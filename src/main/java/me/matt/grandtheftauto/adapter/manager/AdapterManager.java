package me.matt.grandtheftauto.adapter.manager;

import lombok.Getter;
import me.matt.grandtheftauto.GrandTheftAuto;
import me.matt.grandtheftauto.adapter.impl.OrganizationAdapter;
import me.matt.grandtheftauto.adapter.impl.UserAdapter;

@Getter
public class AdapterManager {

    private final UserAdapter userAdapter;
    private final OrganizationAdapter organizationAdapter;

    public AdapterManager(GrandTheftAuto plugin) {
        this.userAdapter = new UserAdapter(plugin);
        this.organizationAdapter = new OrganizationAdapter(plugin);
    }

}
