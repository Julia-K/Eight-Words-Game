package com.jkozlowska.eightwords;

import java.util.LinkedList;

public class OneCell implements CellFunctions {
    private char value;
    private boolean filled;
    private boolean changePossibility;
    private boolean passwordNeeded;
    private LinkedList<Character> used;

    public OneCell() {
        value = ' ';
        filled = false;
        changePossibility = true;
        passwordNeeded = false;
        used = new LinkedList<>();
    }

    public void setPasswordNeeded(boolean passwordNeeded) {
        this.passwordNeeded = passwordNeeded;
    }

    public boolean getPasswordNeeded() {
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

    public char getValue() {
        return value;
    }

    public void setValue(final char value) {
        if(changePossibility) {
            if(value != ' ') {
                filled = true;
            }
            this.value = value;
            used.add(value);
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
        used.clear();
    }

    public boolean isUsed(final char value) {
        if(used.contains(value)) {
            return true;
        }
        return false;
    }

    public void useValue(final char value) {
        used.add(value);
    }

    public int countedValues() {
        return used.size();
    }
}
