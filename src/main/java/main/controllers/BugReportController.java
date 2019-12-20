package main.controllers;

import io.swagger.annotations.*;
import main.entity.EntityBugReport;
import main.interfaces.BugReportDao;
import main.interfaces.ReportCorrectionsDao;
import main.models.DeleteReport;
import main.models.ResponseAddBug;
import main.models.ResponseSelectBug;
import main.models.Status;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Api(value = "/bugReport",description = "отчет об ошибке")
@RestController
@RequestMapping("/bugReport")
public class BugReportController {

    private final ApplicationContext ctx;

    @Autowired
    public BugReportController(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    @ApiOperation(value = "Сохранение отчета об ошибке")
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseAddBug saveBugReport(

            @ApiParam(name = "request",
                    value = "name - имя, " +
                            "error_message - сообщение об ошибке, " +
                            "id - опционально нужен для перезаписи",
                    defaultValue = "{name:name,error_message:error_message}")
            @RequestParam(name = "request") JSONObject requestJ) {

        ResponseAddBug responseAddBug = new ResponseAddBug();

        Status status = new Status();

        BugReportDao bugReportDao = ctx.getBean("jpaBugReport", BugReportDao.class);

        EntityBugReport bugReport;

        if (requestJ.has("id")) {

            bugReport = bugReportDao.findById(requestJ.getLong("id"));

            if (bugReport == null) {

                status.setError("Запись не найдена");
                responseAddBug.setStatus(status);
                return responseAddBug;
            }

        } else {
            bugReport = new EntityBugReport();
        }

        if (!requestJ.has("error_message")) {

            status.setError("Отсутствует error_message");
            responseAddBug.setStatus(status);
            return responseAddBug;
        }

        bugReport.setErrorMessage(requestJ.getString("error_message"));

        if (requestJ.has("name")) {

            bugReport.setName(requestJ.getString("name"));
        }

        bugReport.setDate(new Timestamp(new Date().getTime()));

        bugReportDao.save(bugReport);

        status.setError("ok");

        responseAddBug.setId(bugReport.getId());

        responseAddBug.setStatus(status);

        return responseAddBug;
    }

    @ApiOperation(value = "Получение отчет об ошибках")
    @RequestMapping(value = "/select", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseSelectBug selectBugReport(

            @ApiParam(name = "request",
                    value = "tocken  - токен, ",
                    defaultValue = "{tocken:5cf73532f63c1a56f44d0a9de5c7e33c}")
            @RequestParam(name = "request") JSONObject requestJ) {

        BugReportDao bugReportDao = ctx.getBean("jpaBugReport", BugReportDao.class);

        ResponseSelectBug responseSelectBug = new ResponseSelectBug();

        Status status = new Status();

        List<EntityBugReport> listBugReport = bugReportDao.findAllByIdIsNotNull();

        if (listBugReport != null && listBugReport.size() != 0) {

            responseSelectBug.setAllBugReports(listBugReport);
        }

        status.setError("ok");

        responseSelectBug.setStatus(status);

        return responseSelectBug;
    }

    @ApiOperation(value = "Удаление отчета об ошибке")
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    DeleteReport deleteBugReport(

            @ApiParam(name = "request",
                    value = "id - id ошибки, ",
                    defaultValue = "{id:1}")
            @RequestParam(name = "request") JSONObject requestJ) {

        DeleteReport deleteReport = new DeleteReport();

        Status status = new Status();

        BugReportDao bugReportDao = ctx.getBean("jpaBugReport", BugReportDao.class);

        if (!requestJ.has("id")) {

            status.setError("Отсутствует id");
            deleteReport.setStatus(status);
            return deleteReport;
        }

        long id = requestJ.getLong("id");

        if (!bugReportDao.existsById(id)) {
            status.setError("Bug не существует");
            deleteReport.setStatus(status);
            return deleteReport;
        }

        ReportCorrectionsDao reportCorrectionsDao = ctx.getBean("jpaReportCorrections", ReportCorrectionsDao.class);

        reportCorrectionsDao.deleteAllByBugId(id);

        bugReportDao.deleteById(id);

        status.setError("ok");

        deleteReport.setStatus(status);

        return deleteReport;
    }
}
