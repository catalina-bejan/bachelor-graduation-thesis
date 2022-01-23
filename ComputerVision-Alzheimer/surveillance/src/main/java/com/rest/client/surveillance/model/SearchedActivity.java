package com.rest.client.surveillance.model;

public class SearchedActivity {
    private Integer id;
    private String activityName;
    private GeneralAction generalAction;

    public SearchedActivity() {
    }

    public SearchedActivity(String activityName, GeneralAction generalAction) {
        this.activityName = activityName;
        this.generalAction = generalAction;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public GeneralAction getGeneralAction() {
        return generalAction;
    }

    public void setGeneralAction(GeneralAction generalAction) {
        this.generalAction = generalAction;
    }

    @Override
    public String toString() {
        return "SearchedActivity{" +
                "id=" + id +
                ", activityName='" + activityName + '\'' +
                ", generalAction=" + generalAction +
                '}';
    }
}
