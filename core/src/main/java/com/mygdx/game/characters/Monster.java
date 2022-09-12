package com.mygdx.game.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameScreen;
import com.mygdx.game.Weapon;

public class Monster extends GameCharacter {

    private float moveTimer;
    private float activityRadius;


    public Monster(GameScreen gameScreen) {
        this.texture = new Texture("sceletons.png");
        this.regions = new TextureRegion(texture).split(100, 100)[0];
        this.textureHP = new Texture("bar.png");
        this.position = new Vector2(MathUtils.random(0, 1280), MathUtils.random(0, 720));
        while (!gameScreen.getMap().isCellPassable(position)) {
            this.position.set(MathUtils.random(0, 1280), MathUtils.random(0, 720));
        }
        this.direction = new Vector2(0, 0);
        this.speed = 40.0f;
        this.activityRadius = 200.0f;
        this.gameScreen = gameScreen;
        this.hpMax = 20.0f;
        this.hp = hpMax;
        this.weapon = new Weapon("Rust Sword", 50.0f, 0.8f, 5.0f);
        this.secondsPerFrame = 0.2f;


    }

    @Override
    public void update(float dt) {

        damageEffectTimer -= dt/3.0f;
        animationTimer += dt;
        if (damageEffectTimer < 0.0f) {
            damageEffectTimer = 0.0f;
        }
        float dst = gameScreen.getHero().getPosition().dst(this.position);
        if (dst < activityRadius) {
            direction.set(gameScreen.getHero().getPosition()).sub(this.position).nor();
        } else {

            moveTimer -= dt;
            if (moveTimer < 0) {
                moveTimer = MathUtils.random(1.0f, 4.0f);
                direction.set(MathUtils.random(-1.0f, 1.0f), MathUtils.random(-1.0f, 1.0f));
                direction.nor();
            }
        }
//        temp.set(position).mulAdd(direction, speed * dt);
//        if (gameScreen.getMap().isCellPassable(temp)) {
//            position.set(temp);
//        }

        moveForward(dt);


        if (dst < weapon.getAttackRadius()) {
            attackTimer += dt;
            if (attackTimer > weapon.getAttackPeriod()) {
                attackTimer = 0;
                gameScreen.getHero().takeDamage(weapon.getDamage());
            }
        }
        checkScreenBounds();

    }
}
