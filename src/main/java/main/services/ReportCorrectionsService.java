package main.services;

import main.entity.EntityBugReport;
import main.entity.EntityReportCorrections;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.List;

public class ReportCorrectionsService {

    public JSONObject getReportCorrectionsJ(EntityBugReport bugReport, List<EntityReportCorrections> listReportCorrections) {

        if (bugReport == null) {
            return null;
        }

        JSONObject bugReportJ = new JSONObject(bugReport);

        JSONArray reportCorrectionsArrayJ = new JSONArray();

        if (listReportCorrections != null && listReportCorrections.size() != 0) {

            for (EntityReportCorrections reportCorrections : listReportCorrections) {

                JSONObject reportCorrectionsJ = new JSONObject(reportCorrections);
                reportCorrectionsJ.remove("bugId");
                reportCorrectionsArrayJ.put(reportCorrectionsJ);

            }
        }

        if (reportCorrectionsArrayJ.length() != 0) {
            bugReportJ.put("report_corrections", reportCorrectionsArrayJ);
        }

        return bugReportJ;
    }
}
