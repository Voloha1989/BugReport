package main.services;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.util.Date;

public class FileLog {

    private String path = "C:\\Users\\User\\IdeaProjects/Logs.txt";
    
    //private String path = System.getProperty("jboss.home.dir") + "/Logs.txt";
    //private String path = "Logs.txt";

    private File logFile = new File(path);

    public void saveFileLog(String url, long shopId, long ping, String response) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {

            String lineSeparator = System.getProperty("line.separator");

            Date currentDate = new Date();

            writer.write(currentDate.getTime() + lineSeparator + url + lineSeparator + shopId + lineSeparator + ping + lineSeparator);

            writer.write(response);
            writer.write(lineSeparator);

            writer.flush();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public void deleteByDateLogFile(long requestDate) {
        JSONArray logArrayJ = getLogArrayJ();
        rewriteLogFile(logArrayJ, requestDate);
    }

    private void rewriteLogFile(JSONArray logArrayJ, long requestDate) {

        if (logArrayJ == null || logArrayJ.length() == 0) {
            return;
        }

        for (int i = 0; i < logArrayJ.length(); i++) {

            JSONObject logJ = logArrayJ.getJSONObject(i);

            long date = logJ.getLong("date");

            if (requestDate >= date) {

                try {
                    FileWriter fileWriter = new FileWriter(path);

                    BufferedWriter writer = new BufferedWriter(fileWriter);

                    writer.write("");
                    writer.flush();
                    writer.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public JSONArray getLogArrayJ() {

        JSONArray logArrayJ = new JSONArray();

        try {
            FileReader fileReader = new FileReader(logFile);
            BufferedReader reader = new BufferedReader(fileReader);

            String date;

            while ((date = reader.readLine()) != null) {

                String url = reader.readLine();
                String shopId = reader.readLine();
                String ping = reader.readLine();
                String response = reader.readLine();

                JSONObject logJ = new JSONObject();
                logJ.put("date", date);
                logJ.put("url", url);
                logJ.put("shop_id", shopId);
                logJ.put("ping", ping);
                logJ.put("response", response);

                logArrayJ.put(logJ);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return logArrayJ;
    }
}
