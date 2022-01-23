package com.rest.client.surveillance.model;

public class NotificationMessage {
    String eatingTooOftenMessage;
    String forgotToEatMessage;
    String forgotToDrinkMessage;
    String eatingTooOftenMessageForCaretaker;
    String forgotToEatMessageForCaretaker;
    String forgotToDrinkMessageForCaretaker;

    public NotificationMessage() {
    }

    public NotificationMessage(String eatingTooOftenMessage, String forgotToEatMessage, String forgotToDrinkMessage, String eatingTooOftenMessageForCaretaker, String forgotToEatMessageForCaretaker, String forgotToDrinkMessageForCaretaker) {
        this.eatingTooOftenMessage = eatingTooOftenMessage;
        this.forgotToEatMessage = forgotToEatMessage;
        this.forgotToDrinkMessage = forgotToDrinkMessage;
        this.eatingTooOftenMessageForCaretaker = eatingTooOftenMessageForCaretaker;
        this.forgotToEatMessageForCaretaker = forgotToEatMessageForCaretaker;
        this.forgotToDrinkMessageForCaretaker = forgotToDrinkMessageForCaretaker;
    }

    public String getEatingTooOftenMessage() {
        return eatingTooOftenMessage;
    }

    public void setEatingTooOftenMessage(String eatingTooOftenMessage) {
        this.eatingTooOftenMessage = eatingTooOftenMessage;
    }

    public String getForgotToEatMessage() {
        return forgotToEatMessage;
    }

    public void setForgotToEatMessage(String forgotToEatMessage) {
        this.forgotToEatMessage = forgotToEatMessage;
    }

    public String getForgotToDrinkMessage() {
        return forgotToDrinkMessage;
    }

    public void setForgotToDrinkMessage(String forgotToDrinkMessage) {
        this.forgotToDrinkMessage = forgotToDrinkMessage;
    }

    public String getEatingTooOftenMessageForCaretaker() {
        return eatingTooOftenMessageForCaretaker;
    }

    public void setEatingTooOftenMessageForCaretaker(String eatingTooOftenMessageForCaretaker) {
        this.eatingTooOftenMessageForCaretaker = eatingTooOftenMessageForCaretaker;
    }

    public String getForgotToEatMessageForCaretaker() {
        return forgotToEatMessageForCaretaker;
    }

    public void setForgotToEatMessageForCaretaker(String forgotToEatMessageForCaretaker) {
        this.forgotToEatMessageForCaretaker = forgotToEatMessageForCaretaker;
    }

    public String getForgotToDrinkMessageForCaretaker() {
        return forgotToDrinkMessageForCaretaker;
    }

    public void setForgotToDrinkMessageForCaretaker(String forgotToDrinkMessageForCaretaker) {
        this.forgotToDrinkMessageForCaretaker = forgotToDrinkMessageForCaretaker;
    }

    @Override
    public String toString() {
        return "NotificationMessage{" +
                "eatingTooOftenMessage='" + eatingTooOftenMessage + '\'' +
                ", forgotToEatMessage='" + forgotToEatMessage + '\'' +
                ", forgotToDrinkMessage='" + forgotToDrinkMessage + '\'' +
                ", eatingTooOftenMessageForCaretaker='" + eatingTooOftenMessageForCaretaker + '\'' +
                ", forgotToEatMessageForCaretaker='" + forgotToEatMessageForCaretaker + '\'' +
                ", forgotToDrinkMessageForCaretaker='" + forgotToDrinkMessageForCaretaker + '\'' +
                '}';
    }
}
