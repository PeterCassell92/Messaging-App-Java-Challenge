package com.optionbehaviour;

public class ActionOption implements IOption {
    final private String description;
    private Runnable action;

    public ActionOption(String description, Runnable action) {
        this.description = description;
        this.action = action;
    }

    public Runnable getAction() {
        return action;
    }

    public void setAction(Runnable action) {
        this.action = action;
    }

    public void performAction() {
        if (action != null) {
            action.run();
        }
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String getDisplayString() {
        return this.getDescription();
    }
}