package com.babbangona.commons.library.enums;

public enum Status {
    // user profile
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    MUST_CHANGE_PIN("Must Change Pin"),
    PENDING_REGISTRATION("Pending Registration"),
    PENDING_ACTIVATION("Pending Activation"),
    DELETED("Deleted"),

    // transactions
    NEW("New"),
    PENDING("Pending"),
    FAILED("Failed"),
    SUCCESS("Successful"),
    REVERSED("Reversed"),
    REVERSE_FAILED("Reverse Failed"),
    UNDER_PROCESSING("Under Processing"),
    PARTIALLY_APPROVED("Partially Approved"),
    PENDING_APPROVAL("Pending Approval"),
    REVOKED("Revoked"),
    REJECTED("Rejected"),

    // support
    OPEN("Open"),
    CLOSED("Closed"),

    //scheduled payment
    COMPLETED("Completed"),
    CREATED("Created");

    private final String label;

    Status(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
