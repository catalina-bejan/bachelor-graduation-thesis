package com.computervision.alzheimer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "type")
    private String type;

    @Column(name = "patient_message")
    private String patientMessage;

    @Column(name = "caretaker_message")
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

    @OneToMany(mappedBy="notification")
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
