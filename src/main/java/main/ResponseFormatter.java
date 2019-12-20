package main;

import org.json.JSONObject;

public class ResponseFormatter {

    public static final int AUTHENTICATION_ERROR = 1;

    private JSONObject response = new JSONObject();
    private JSONObject status = new JSONObject();
    public ResponseFormatter(){
        status.put("error","ok");
    }

    public void addParameterJsonObject(String key, JSONObject value){
        response.put(key,value);
    }
    public void addParameter(String key,Object value){
        response.put(key,value);
    }
    public void addCodeError(int errorCode){
        status.put("code",errorCode);
    }
    public void addError(String error){
        status.put("error",error);
    }

    public JSONObject getResponse(){
        response.put("status",status);
        return response;
    }
}
