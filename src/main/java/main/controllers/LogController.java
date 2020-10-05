package main.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import main.models.*;
import main.services.FileLog;
import main.services.LogServletService;
import org.json.JSONArray;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.File;
import java.util.Date;
import java.util.List;

@Api(value = "/log", description = "Контроллер для логов")
@RestController
@RequestMapping("/log")
public class LogController {

    @ApiOperation(value = "Для получения логов - /ice/s/select.log")
    @RequestMapping(value = "/select", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseLogs selectLog(

            @ApiParam(name = "date",
                    value = "date  - прошлая дата",
                    defaultValue = "1538894655000")
            @RequestParam(name = "date") String requestDate) {

        FileLog fileLog = new FileLog();

        Date currentDate = new Date();

        ResponseLogs responseLogs = new ResponseLogs();

        Status status = new Status();

        JSONArray logArrayJ = fileLog.getLogArrayJ();

        if (logArrayJ == null || logArrayJ.length() == 0) {
            status.setError("ок");
            responseLogs.setStatus(status);
            return responseLogs;
        }

        requestDate = requestDate.replace("\r", "");
        requestDate = requestDate.replace("\n", "");

        LogServletService lSS = new LogServletService();

        JSONArray newLogArrayJ = lSS.getNewLogArrayJ(logArrayJ, Long.parseLong(requestDate), currentDate.getTime());
        JSONArray reverseLogArrayJ = lSS.getReverseLogArrayJ(newLogArrayJ);

        List<Log> logList = lSS.getLogArrayModules(reverseLogArrayJ);
        responseLogs.setLog(logList);

        status.setError("ok");
        responseLogs.setStatus(status);

        return responseLogs;
    }

    @ApiOperation(value = "Сохранение логов")
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    void saveLog(

            @ApiParam(name = "url",
                    value = "url  - запрос",
                    defaultValue = "/ice/s/select.info.shop{\"tocken\":\"5cf73532f63c1a56f44d0a9de5c7e33c\",\"employee_id\":1414}")
            @RequestParam(name = "url") String url,

            @ApiParam(name = "shopId",
                    value = "shopId  - id магазина",
                    defaultValue = "1414")
            @RequestParam(name = "shopId") String shopId,

            @ApiParam(name = "ping",
                    value = "ping  - ping",
                    defaultValue = "153")
            @RequestParam(name = "ping") String ping,

            @ApiParam(name = "response",
                    value = "response  - ответ",
                    defaultValue = "response")
            @RequestParam(name = "response") String response) {

        FileLog fileLog = new FileLog();

        fileLog.saveFileLog(url, Long.parseLong(shopId), Long.parseLong(ping), response);
    }

    @ApiOperation(value = "Удаление логов по дате")
    @RequestMapping(value = "/delete/by/date", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseDelete deleteByDateLog(

            @ApiParam(name = "date",
                    value = "date - дата от которой делать удаление",
                    defaultValue = "1539602173000")
            @RequestParam(name = "date") String requestDate) {

        long date = Long.parseLong(requestDate);

        ResponseDelete responseDelete = new ResponseDelete();
        Status status = new Status();

        FileLog fileLog = new FileLog();
        fileLog.deleteByDateLogFile(date);

        status.setError("ok");
        responseDelete.setStatus(status);

        return responseDelete;
    }

    @ApiOperation(value = "Удаление файла с логами")
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    DeleteLogFile deleteFileLog(

            @ApiParam(name = "request",
                    value = "request - запрос",
                    defaultValue = "request")
            @RequestParam(name = "request") String request) {

        String path = "C:\\Users\\User\\IdeaProjects/Logs.txt";

        //String path = System.getProperty("jboss.home.dir") + "/Log.txt";
        //String path = "Logs.txt";

        File logFile = new File(path);
        DeleteLogFile deleteLogFile = new DeleteLogFile();

        JsonBody jsonBody = new JsonBody();
        jsonBody.setStatusLog("logFile не существует");

        if (logFile.exists()) {

            boolean checkDelete = logFile.delete();

            if (checkDelete) {
                jsonBody.setStatusLog("logFile удалён");
            } else {
                jsonBody.setStatusLog("logFile не удалён");
            }
        }

        Status status = new Status();
        status.setError("ok");

        deleteLogFile.setJsonBody(jsonBody);
        deleteLogFile.setStatus(status);

        return deleteLogFile;
    }
}
