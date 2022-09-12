package com.mygdx.game;

public class Weapon {
    private String name;
    private float attackRadius;
    private float attackPeriod;
    private float damage;

    public Weapon(String name, float attackRadius, float attackPeriod, float damage) {
        this.name = name;
        this.attackRadius = attackRadius;
        this.attackPeriod = attackPeriod;
        this.damage = damage;
    }

    public float getAttackRadius() {
        return attackRadius;
    }

    public float getAttackPeriod() {
        return attackPeriod;
    }

    public String getName() {
        return name;
    }

    public float getDamage() {
        return damage;
    }
}
