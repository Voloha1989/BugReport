package main.models;

import java.util.List;

public class ResponseLogs {

    private List<Log> log;
    private Status status;

    public List<Log> getLog() {
        return log;
    }

    public void setLog(List<Log> log) {
        this.log = log;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
