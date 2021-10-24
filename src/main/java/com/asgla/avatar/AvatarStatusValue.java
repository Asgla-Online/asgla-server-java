package com.asgla.avatar;

public class AvatarStatusValue {

    private volatile int value = 0;
    private volatile int valueMax = 0;

    public AvatarStatusValue(int value, int valueMax) {
        this.value = value;
        this.valueMax = valueMax;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValueMax() {
        return valueMax;
    }

    public void setValueMax(int valueMax) {
        this.valueMax = valueMax;
    }

    public boolean isFullHealth() {
        return getValue() >= getValueMax();
    }

    public double healthPercentage() {
        return (getValue() * 100) / (double) getValueMax();
    }

    public void increase(int amount) {
        setValue(getValue() + amount);
    }

    public void decrease(int amount) {
        setValue(getValue() - amount);
    }

    public void increaseByPercent(int percent) {
        increase(getValueMax() * (percent / 100));
    }

    public void decreaseByPercent(int percent) {
        decrease(getValueMax() * (percent / 100));
    }

}
