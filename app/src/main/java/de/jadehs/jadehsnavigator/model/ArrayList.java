package de.jadehs.jadehsnavigator.model;

/**
 * Created by Nico on 11.08.2015.
 */
public class ArrayList {

    private String modulName;
    private String profName;
    private String room;
    private String startTime;
    private String endTime;

    private int fb;
    private String vPlanIdentifier;

    public ArrayList() {}

    public ArrayList(String modulName,
                     String profName,
                     String room,
                     String startTime,
                     String endTime,
                     int fb,
                     String vPlanIdentifier) {

        this.modulName = modulName;
        this.profName = profName;
        this.room = room;
        this.startTime = startTime;
        this.endTime = endTime;
        this.fb = fb;
        this.vPlanIdentifier = vPlanIdentifier;
    }

    public ArrayList(String modulName,
                     String profName,
                     String room,
                     String startTime,
                     String endTime,
                     int fb) {

        this.modulName = modulName;
        this.profName = profName;
        this.room = room;
        this.startTime = startTime;
        this.endTime = endTime;
        this.fb = fb;
    }

    public String getModulName() {
        return modulName;
    }

    public void setModulName(String modulName) {
        this.modulName = modulName;
    }

    public String getProfName() {
        return profName;
    }

    public void setProfName(String profName) {
        this.profName = profName;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getFb() {
        return fb;
    }

    public void setFb(int fb) {
        this.fb = fb;
    }

    public String getvPlanIdentifier() {
        return vPlanIdentifier;
    }

    public void setvPlanIdentifier(String vPlanIdentifier) {
        this.vPlanIdentifier = vPlanIdentifier;
    }
}
