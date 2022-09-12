package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.List;

public class ItemEmitter {
    private Item[] items;
    private Texture texture;
    private TextureRegion[] regions;

    public Item[] getItems() {
        return items;
    }

    public ItemEmitter() {
        this.items = new Item[50];
        for (int i = 0; i < items.length; i++) {
            items[i] = new Item();
        }
        texture = new Texture("items.png");
        regions = new TextureRegion(texture).split(32, 32)[0];
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < items.length; i++) {
            if (items[i].isActive()) {
                batch.draw(regions[items[i].getType().index], items[i].getPosition().x - 16, items[i].getPosition().y - 16);
            }
        }
    }

    public void generateRandomItem(float x, float y, int count, float probability) {

        for (int q = 0; q < count; q++) {
            float n = MathUtils.random(0, 1);
            if (n <= probability) {
                int type = MathUtils.random(0, Item.Type.values().length - 1);
                for (int i = 0; i < items.length; i++) {
                    if (!items[i].isActive()) {
                        items[i].setup(x, y, Item.Type.values()[type]);
                        break;
                    }
                }
            }

        }


    }

    public void update(float dt) {
        for (int i = 0; i < items.length; i++) {
            if (items[i].isActive()) {
                items[i].update(dt);
            }
        }

    }
}
