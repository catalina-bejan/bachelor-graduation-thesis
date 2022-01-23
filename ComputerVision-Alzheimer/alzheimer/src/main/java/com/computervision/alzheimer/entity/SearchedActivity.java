package com.computervision.alzheimer.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "searched_activity")
public class SearchedActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "activity_name")
    private String activityName;

    @ManyToOne
    @JoinColumn(name = "general_action_id")
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
