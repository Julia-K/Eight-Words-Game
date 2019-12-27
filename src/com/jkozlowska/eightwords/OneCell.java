package com.jkozlowska.eightwords;

import java.util.LinkedList;

public class OneCell implements CellFunctions {
    private char value;
    private boolean filled;
    private LinkedList<Character> used;

    public OneCell() {
        value = ' ';
        filled = false;
        used = new LinkedList<>();
    }

    @Override
    public boolean isFilled() {
        return filled;
    }

    public char getValue() {
        return value;
    }

    public void setValue(final char value) {
        this.value = value;
        filled = true;
        used.add(value);
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
