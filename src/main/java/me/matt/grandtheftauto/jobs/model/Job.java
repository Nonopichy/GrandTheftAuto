package me.matt.grandtheftauto.jobs.model;

import lombok.Data;
import me.matt.grandtheftauto.GrandTheftAuto;

import java.util.ArrayList;
import java.util.List;

@Data
// recheck this
public class Job {

    private short id;
    private String name;
    private int necessaryLevel;
    private List<String> workers; // TODO: 20/09/2021 only if the member is online! 

    public Job(String name, int necessaryLevel) {
        this.id = (short) (GrandTheftAuto.getInstance().getDatabaseManager().getJobs().getSize() + 1);
        this.name = name;
        this.necessaryLevel = necessaryLevel;
        this.workers = new ArrayList<>();
    }

}
