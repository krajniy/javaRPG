package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class MyGDXGame extends ApplicationAdapter {
    /*
    === Идеи ===
    1. + Движение по пикселям
    2. Преграды
    3. Анимация
    4. Стрельба
    5. + Хаотичное движение для монстра
    6. + Преследование монстром героя
    7. Аптечки, Монеты, Зелья, Дроп
    8. +- Параметры героям/мострам (ХП, крит, скорость)
    9. Система уровней игры
    10. Опыт
    11. + Оружие
    12. Босс
    13. + Битва
    14. + Полоска здоровья
    15. + Перенос на векторы
    16. Инвентарь
    17. + Отображение текста
    18. Хот бар
    19. Камера
     */
    private SpriteBatch batch;
    private GameScreen gameScreen;



    @Override
    public void create() {
        this.batch = new SpriteBatch();
        this.gameScreen = new GameScreen(batch);
        this.gameScreen.create();


    }

    @Override
    public void render() {
        gameScreen.render();
    }



    @Override
    public void dispose() {
        batch.dispose();

    }
}