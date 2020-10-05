package main.controllers;

import io.swagger.annotations.*;
import main.entity.EntityBugReport;
import main.entity.EntityReportCorrections;
import main.interfaces.BugReportDao;
import main.interfaces.ReportCorrectionsDao;
import main.models.DeleteReport;
import main.models.ResponseAddCorrections;
import main.models.ResponseSelectCorrections;
import main.models.Status;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Api(value = "/reportCorrections", description = "Контроллер для отчета об исправлениях")
@RestController
@RequestMapping("/reportCorrections")
public class ReportCorrectionsController {

    private final ApplicationContext ctx;

    @Autowired
    public ReportCorrectionsController(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    @ApiOperation(value = "Сохранение отчета об исправлениях")
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseAddCorrections saveReportCorrections(

            @ApiParam(name = "request",
                    value = "bug_id - id ошибки, " +
                            "comment - комментарий о исправлении, " +
                            "id - опционально нужен для перезаписи",
                    defaultValue = "{comment:comment,bug_id:1}")
            @RequestParam(name = "request") JSONObject requestJ) {

        ResponseAddCorrections responseAddCorrections = new ResponseAddCorrections();

        Status status = new Status();

        if (!requestJ.has("bug_id")) {
            status.setError("Отсутствует bug_id");
            responseAddCorrections.setStatus(status);
            return responseAddCorrections;
        }

        long bugId = requestJ.getLong("bug_id");

        BugReportDao bugReportDao = ctx.getBean("jpaBugReport", BugReportDao.class);

        if (!bugReportDao.existsById(bugId)) {
            status.setError("Bug не существует");
            responseAddCorrections.setStatus(status);
            return responseAddCorrections;
        }

        ReportCorrectionsDao reportCorrectionsDao = ctx.getBean("jpaReportCorrections", ReportCorrectionsDao.class);

        EntityReportCorrections reportCorrections;

        if (requestJ.has("id")) {

            reportCorrections = reportCorrectionsDao.findById(requestJ.getLong("id"));

            if (reportCorrections == null) {
                status.setError("Запись не найдена");
                responseAddCorrections.setStatus(status);
                return responseAddCorrections;
            }

        } else {
            reportCorrections = new EntityReportCorrections();
        }

        if (!requestJ.has("comment")) {
            status.setError("Отсутствует комментарий об исправлении");
            responseAddCorrections.setStatus(status);
            return responseAddCorrections;
        }

        reportCorrections.setBugId(bugId);
        reportCorrections.setComment(requestJ.getString("comment"));

        Date date = new Date();

        reportCorrections.setDate(new Timestamp(date.getTime()));
        reportCorrectionsDao.save(reportCorrections);

        status.setError("ok");

        responseAddCorrections.setId(reportCorrections.getId());
        responseAddCorrections.setStatus(status);

        return responseAddCorrections;

    }

    @ApiOperation(value = "Получение отчета об исправлениях")
    @RequestMapping(value = "/select", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseSelectCorrections selectReportCorrections(

            @ApiParam(name = "request",
                    value = "bug_id - опционально нужен для получения всех исправлениий на одну ошибку, " +
                            "tocken  - токен, ",
                    defaultValue = "{tocken:5cf73532f63c1a56f44d0a9de5c7e33c}")
            @RequestParam(name = "request") JSONObject requestJ) {

        ResponseSelectCorrections responseSelectCorrections = new ResponseSelectCorrections();

        Status status = new Status();

        ReportCorrectionsDao reportCorrectionsDao = ctx.getBean("jpaReportCorrections", ReportCorrectionsDao.class);

        if (requestJ.has("bug_id")) {

            long bugId = requestJ.getLong("bug_id");

            BugReportDao bugReportDao = ctx.getBean("jpaBugReport", BugReportDao.class);

            if (!bugReportDao.existsById(bugId)) {
                status.setError("Bug не существует");
                responseSelectCorrections.setStatus(status);
                return responseSelectCorrections;
            }

            EntityBugReport bugReport = bugReportDao.findById(bugId);

            List<EntityReportCorrections> reportCorrectionsList = reportCorrectionsDao.findAllByBugId(bugId);

            responseSelectCorrections.setBugReport(bugReport);
            responseSelectCorrections.setAllReportCorrections(reportCorrectionsList);

            status.setError("ok");

            responseSelectCorrections.setStatus(status);

            return responseSelectCorrections;
        }

        List<EntityReportCorrections> listReportCorrections = reportCorrectionsDao.findAllByIdIsNotNull();

        if (listReportCorrections != null) {
            responseSelectCorrections.setAllReportCorrections(listReportCorrections);
        }

        status.setError("ok");
        responseSelectCorrections.setStatus(status);

        return responseSelectCorrections;
    }

    @ApiOperation(value = "Удаление отчета об исправлении")
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    DeleteReport deleteReportCorrections(

            @ApiParam(name = "request",
                    value = "id - id исправления, ",
                    defaultValue = "{id:1}")
            @RequestParam(name = "request") JSONObject requestJ) {

        DeleteReport deleteReport = new DeleteReport();

        Status status = new Status();

        ReportCorrectionsDao reportCorrectionsDao = ctx.getBean("jpaReportCorrections", ReportCorrectionsDao.class);

        if (!requestJ.has("id")) {
            status.setError("Отсутствует id");
            deleteReport.setStatus(status);
            return deleteReport;
        }

        long id = requestJ.getLong("id");

        if (!reportCorrectionsDao.existsById(id)) {
            status.setError("Correction не существует");
            deleteReport.setStatus(status);
            return deleteReport;
        }

        reportCorrectionsDao.deleteById(id);

        status.setError("ok");
        deleteReport.setStatus(status);

        return deleteReport;
    }
}
