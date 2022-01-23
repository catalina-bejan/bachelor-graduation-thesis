package com.rest.client.surveillance.model;

public class Notification {
    private Integer id;
    private String type;
    private String patientMessage;
    private String caretakerMessage;

    public Notification() {
    }

    public Notification(String type, String patientMessage, String caretakerMessage) {
        this.type = type;
        this.patientMessage = patientMessage;
        this.caretakerMessage = caretakerMessage;
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

    public String getPatientMessage() {
        return patientMessage;
    }

    public void setPatientMessage(String patientMessage) {
        this.patientMessage = patientMessage;
    }

    public String getCaretakerMessage() {
        return caretakerMessage;
    }

    public void setCaretakerMessage(String caretakerMessage) {
        this.caretakerMessage = caretakerMessage;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", patientMessage='" + patientMessage + '\'' +
                ", caretakerMessage='" + caretakerMessage + '\'' +
                '}';
    }
}
