package main.models;

import main.entity.EntityBugReport;
import main.entity.EntityReportCorrections;
import java.util.List;

public class ResponseSelectCorrections {

    private EntityBugReport bugReport;
    private List<EntityReportCorrections> allReportCorrections;
    private Status status;

    public List<EntityReportCorrections> getAllReportCorrections() {
        return allReportCorrections;
    }

    public void setAllReportCorrections(List<EntityReportCorrections> allReportCorrections) {
        this.allReportCorrections = allReportCorrections;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public EntityBugReport getBugReport() {
        return bugReport;
    }

    public void setBugReport(EntityBugReport bugReport) {
        this.bugReport = bugReport;
    }
}
