package com.asgla.avatar;

import com.asgla.util.Stats;

public abstract class AvatarStats extends Stats {

    public void decreaseDamageByPercent(double percent) {
        int amount = (int) (damage * percent);
        decreaseDamage(amount);
    }

    public void increaseDamageByPercent(double percent) {
        int amount = (int) (damage * percent);
        increaseDamage(amount);
    }

    private void decreaseDamage(int amount) {
        damage(damage - amount);
    }

    private void increaseDamage(int amount) {
        damage(damage + amount);
    }

    private void damage(double damage) {
        damage(damage);
    }

    public abstract double reduction();

    public abstract int weaponDps();

    public abstract int maximumAttackDamage();

    public abstract int minimumAttackDamage();

    public abstract int magicDps();

    public abstract int maximumMagicDamage();

    public abstract int minimumMagicDamage();

    public abstract void update();

    private double calculate(String type, double val, double val2, boolean check) {
        switch (type) {
            case "+" -> val += val2;
            case "-" -> {
                if (check && val - val2 < 0) {
                    return 0;
                }
                val -= val2;
            }
            default -> val *= val2;
        }

        return val;
    }

}
