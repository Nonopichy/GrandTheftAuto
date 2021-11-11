package me.matt.grandtheftauto.users.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.val;
import me.matt.grandtheftauto.GrandTheftAuto;
import me.matt.grandtheftauto.jobs.model.Job;
import me.matt.grandtheftauto.organizations.enums.OrganizationRole;
import me.matt.grandtheftauto.organizations.model.Organization;
import me.matt.grandtheftauto.users.enums.AccountType;
import me.matt.grandtheftauto.users.enums.Gender;
import me.matt.grandtheftauto.users.enums.LocationType;

import java.util.Date;

@AllArgsConstructor
@Data
@ToString
public class User {

    private String nickName;
    private Date firstLogin;
    private int level;
    private Gender gender;
    private double moneyBalance;
    private double moneyInBank;
    private double cashBalance;
    private int golds;
    private int warns;
    private AccountType accountType;
    private LocationType locationType;
    private long vipTime;
    private int crimes;
    private int stars;
    private int killedTimes;
    private int diedTimes;
    private Job job;
    private Organization organization;
    private OrganizationRole organizationRole;
    private int house;
    private int company;
    private int farm;
    private Date lastLogin;

    /*
    * Constructor to register new users.
    * */
    public User(String nickName) {
        this.nickName = nickName;
        this.firstLogin = new Date();
        this.gender = Gender.MALE; //

        val section = GrandTheftAuto.getInstance().getConfig().getConfigurationSection("Start");

        this.level = section.getInt("Level");
        this.moneyBalance = section.getDouble("Money");
        this.moneyInBank = section.getDouble("Bank");
        this.cashBalance = section.getDouble("Cash");
        this.accountType = AccountType.FREE;
        this.locationType = LocationType.SPAWN;
        // TODO: 19/09/2021 if players join in organization, switch locationtype to hq! 
        this.vipTime = -1;
        this.job = null;
        this.organization = null;
        this.organizationRole = null;
        this.house = -1;
        this.company = -1;
        this.farm = -1;
        this.lastLogin = new Date();
    }

}
