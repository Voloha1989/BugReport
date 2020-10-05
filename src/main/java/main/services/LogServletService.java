package main.services;

import main.models.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.LinkedList;
import java.util.List;

public class LogServletService {

    public JSONArray getNewLogArrayJ(JSONArray logArrayJ, long requestDate, long currentDate) {

        if (logArrayJ == null || logArrayJ.length() == 0) {
            return null;
        }

        JSONArray newLogArrayJ = new JSONArray();

        for (int i = 0; i < logArrayJ.length(); i++) {

            JSONObject logJ = logArrayJ.getJSONObject(i);
            long date = logJ.getLong("date");

            if (requestDate <= date && date <= currentDate) {
                newLogArrayJ.put(logJ);
            }
        }

        return newLogArrayJ;
    }

    public JSONArray getReverseLogArrayJ(JSONArray newLogArrayJ) {

        if (newLogArrayJ == null || newLogArrayJ.length() == 0) {
            return null;
        }

        JSONArray reverseLogArrayJ = new JSONArray();

        for (int i = newLogArrayJ.length()-1; i >= 0; i--) {
            reverseLogArrayJ.put(newLogArrayJ.getJSONObject(i));
        }

        return reverseLogArrayJ;
    }

    public List<Log> getLogArrayModules(JSONArray reverseLogArrayJ) {

        if (reverseLogArrayJ == null || reverseLogArrayJ.length() == 0) {
            return null;
        }

        List<Log> logList = new LinkedList<>();

        for (int i = 0; i < reverseLogArrayJ.length(); i++) {

            JSONObject logJ = reverseLogArrayJ.getJSONObject(i);

            Log log = new Log();

            log.setDate(logJ.getLong("date"));
            log.setShopId(logJ.getLong("shop_id"));
            log.setUrl(logJ.getString("url"));
            log.setPing(logJ.getLong("ping"));
            log.setResponse(logJ.getString("response"));

            logList.add(log);
        }

        return logList;
    }
}
