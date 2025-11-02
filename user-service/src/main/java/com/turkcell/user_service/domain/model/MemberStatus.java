package com.turkcell.user_service.domain.model;

public enum MemberStatus {
    /**
     * Member is active and can loan books
     */
    ACTIVE,
    /**
     * Member was banned and can't loan books
     */
    BANNED;

    /**
     * Returns the default status for new members
     * @return The default MemberStatus (ACTIVE)
     */
    public static MemberStatus getDefault(){
        return ACTIVE;
    }

    /**
     * Checks if the status is active
     * @return true if status is ACTIVE
     */
    public boolean isActive(){
        return this == ACTIVE;
    }

    /**
     * Checks if the status is banned
     * @return true if status is BANNED
     */
    public boolean isBanned(){
        return this == BANNED;
    }
}
