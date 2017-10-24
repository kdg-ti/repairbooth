package be.kdg.repaircafe.web.resources.error;

public class ErrorResource {
    private final String errorMessage;

    public ErrorResource(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
