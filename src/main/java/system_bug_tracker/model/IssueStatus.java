package system_bug_tracker.model;

public enum IssueStatus {
    OPEN ("Open"),
    CLOSED ("Closed");

    private String status;

    IssueStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}