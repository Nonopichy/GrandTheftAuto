package me.matt.grandtheftauto.organizations.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OrganizationRole {

    RECRUIT(1), // 1
    MEMBER(2), // 2
    SUB_LEADER(3), // 3
    LEADER(4); // 4

    // Sorry, i don't know how to use enum.

    private int number;

    OrganizationRole(int number) {
        this.number = number;
    }

    // And i don't know if using this is the best option.
    public static OrganizationRole getOrganizationRoleByNumber(int number) {

        switch (number) {
            case 1:
                return RECRUIT;
            case 2:
                return MEMBER;
            case 3:
                return SUB_LEADER;
            case 4:
                return LEADER;
            default:
                return null;
        }

    }

    public static int getNumberByOrganizationRole(OrganizationRole organizationRole) {

        switch (organizationRole) {
            case RECRUIT:
                return 1;
            case MEMBER:
                return 2;
            case SUB_LEADER:
                return 3;
            case LEADER:
                return 4;
            default:
                return -1;
        }

    }

}
