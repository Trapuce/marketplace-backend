package com.trapuce.marketplace.models;

public enum ConditionState {
    NEW("Neuf"),
    VERY_GOOD("Très bon état"),
    GOOD("Bon état"),
    SATISFACTORY("État satisfaisant"),
    USED("Occasion"),
    DAMAGED("Endommagé");

    private final String displayName;

    ConditionState(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
