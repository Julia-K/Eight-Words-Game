package com.jkozlowska.eightwords.model;

import java.io.Serializable;

public class OneCell implements CellFunctions, Serializable {
    private char value;
    private boolean filled;
    private boolean changePossibility;
    private boolean passwordNeeded;

    public OneCell() {
        value = ' ';
        filled = false;
        changePossibility = true;
        passwordNeeded = false;
    }

    public void setPasswordNeeded(boolean passwordNeeded) {
        this.passwordNeeded = passwordNeeded;
    }

    public boolean isPasswordNeeded() {
        return passwordNeeded;
    }

    public void setChangePossibility(boolean changePossibility) {
        this.changePossibility = changePossibility;
    }

    public boolean getChangePossibility() {
        return changePossibility;
    }

    @Override
    public boolean isFilled() {
        return filled;
    }

    public void setFilled(Boolean bool) {
        this.filled = bool;
    }

    public char getValue() {
        return value;
    }

    public void setValue(final char value) {
        if(changePossibility) {
            if(value != ' ') {
                filled = true;
            }
            this.value = value;
        } else {
            System.out.println("Nie da sie zmienic wartosci poczatkowej");
        }
    }

    @Override
    public void clear() {
        value = ' ';
        filled = false;
    }

    @Override
    public void restart() {
        clear();
    }
}
