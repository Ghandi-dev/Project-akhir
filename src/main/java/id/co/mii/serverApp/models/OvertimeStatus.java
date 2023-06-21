package id.co.mii.serverApp.models;

public enum OvertimeStatus {
    PROCESS("Process"),
    APPROVED_MANAGER("Approved Manager"),
    APPROVED_HR("Approved HR"),
    REJECTED("Rejected");

    private String label;

    OvertimeStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
