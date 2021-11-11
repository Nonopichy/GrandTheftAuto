package me.matt.grandtheftauto.organizations.enums;

public enum OrganizationType {

    GOVERNMENT,
    GANGSTER,
    MAFIA,
    TERRORIST,
    MERCENARY,
    MURDERERS,
    NOT_CRIMINAL;

    public static OrganizationType detect(String organizationType) {
        return OrganizationType.valueOf(organizationType.toUpperCase());
    }

}
