package com.rest.client.surveillance.model;

public class GeneralAction {
    private Integer id;
    private String type;

    public GeneralAction() {
    }

    public GeneralAction(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public GeneralAction(Integer id, String type) {
        this.id = id;
        this.type = type;
    }

    @Override
    public String toString() {
        return "GeneralAction{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
