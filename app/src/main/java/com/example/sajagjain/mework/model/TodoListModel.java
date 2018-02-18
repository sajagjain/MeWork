package com.example.sajagjain.mework.model;

import java.util.Date;

/**
 * Created by sajag jain on 14-02-2018.
 */

public class TodoListModel {
    String name;
    String description;
    String date;
    String timeRemaining;
    boolean complete;

    public TodoListModel() {
    }

    public TodoListModel(String name, String description, String date, String timeRemaining, boolean complete) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.timeRemaining = timeRemaining;
        this.complete = complete;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(String timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}
