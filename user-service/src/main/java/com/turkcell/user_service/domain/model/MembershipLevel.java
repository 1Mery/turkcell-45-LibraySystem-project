package com.turkcell.user_service.domain.model;

public enum MembershipLevel {
    STANDARD,
    GOLD;

    public static MembershipLevel getDefault(){
        return STANDARD;
    }
}
