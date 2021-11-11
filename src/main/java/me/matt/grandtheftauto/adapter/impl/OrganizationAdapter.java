package me.matt.grandtheftauto.adapter.impl;

import me.matt.grandtheftauto.GrandTheftAuto;
import me.matt.grandtheftauto.adapter.Adapter;
import me.matt.grandtheftauto.organizations.model.Organization;

import java.sql.ResultSet;

public class OrganizationAdapter implements Adapter<Organization, ResultSet> {

    private GrandTheftAuto plugin;

    public OrganizationAdapter(GrandTheftAuto plugin) {
        this.plugin = plugin;
    }

    @Override
    public Organization adapt(ResultSet resultSet) {

        //
        return null;
    }

}
