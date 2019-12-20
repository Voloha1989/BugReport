package main.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jcabi.manifests.Manifests;

public class Status {

    private String error;
    private Integer code;
    private static String version ;
    static {
        try{
            version = Manifests.read("Foo-Version");
        }catch (Exception e){
            version = "testing";
        }
    }

    public static final int AUTHENTICATION_ERROR = 3;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        Status.version = version;
    }
}
