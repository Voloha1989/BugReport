package main.models;

public class DeleteLogFile {

    private JsonBody jsonBody;
    private Status status;

    public JsonBody getJsonBody() {
        return jsonBody;
    }

    public void setJsonBody(JsonBody jsonBody) {
        this.jsonBody = jsonBody;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
