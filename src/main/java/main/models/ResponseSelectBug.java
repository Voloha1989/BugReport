package main.models;

import main.entity.EntityBugReport;
import java.util.List;

public class ResponseSelectBug {

    private List<EntityBugReport> allBugReports;
    private Status status;

    public List<EntityBugReport> getAllBugReports() {
        return allBugReports;
    }

    public void setAllBugReports(List<EntityBugReport> allBugReports) {
        this.allBugReports = allBugReports;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
