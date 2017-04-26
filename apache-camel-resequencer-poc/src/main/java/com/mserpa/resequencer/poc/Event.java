package com.mserpa.resequencer.poc;


public class Event {

    private Integer priority;
    private String message;

    public Event(Integer priority, String message) {
        this.priority = priority;
        this.message = message;
    }

    public Integer getPriority() {
        return priority;
    }

    public String getMessage() {
        return message;
    }
}
