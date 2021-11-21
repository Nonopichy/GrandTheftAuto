package me.matt.grandtheftauto.database.table.impl;

import me.matt.grandtheftauto.GrandTheftAuto;
import me.matt.grandtheftauto.database.table.TableService;
import me.matt.grandtheftauto.jobs.model.Job;

import java.util.List;

// JobAdapter
public class Jobs implements TableService<Job, String> {

    private final GrandTheftAuto plugin;

    public Jobs(GrandTheftAuto plugin) {
        this.plugin = plugin;
    }

    @Override
    public void createTable() {
        // this!!!!!!!!!!

        // id short not null
        // name varchar(?)
        // necessaryLevel int not null
        // workers varchar(30*17)
    }

    @Override
    public boolean has(Job job) {
        return false;
    }

    @Override
    public boolean hasBy(String jobName) {
        return false;
    }

    @Override
    public boolean hasById(short id) {
        return false;
    }

    @Override
    public void add(Job job) {

    }

    @Override
    public void insert(Job job) {

    }

    @Override
    public void update(Job job) {

    }

    @Override
    public void delete(String jobName) {

    }

    @Override
    public Job get(String jobName) {
        return null;
    }

    @Override
    public Job getById(short id) {
        return null;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public List<Job> getAll() {
        return null;
    }

}
