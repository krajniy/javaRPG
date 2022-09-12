package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.characters.GameCharacter;
import com.mygdx.game.characters.Hero;
import com.mygdx.game.characters.Monster;

import java.util.*;

public class GameScreen {
    private SpriteBatch batch;
    private Stage stage;
    private BitmapFont font24;
    private Map map;
    private ItemEmitter itemEmitter;
    private TextEmitter textEmitter;
    private Hero hero;

    private Music music;
    private Sound sound;
    private boolean paused;
    private float spawnTimer;
    private List<GameCharacter> allCharacters;
    private List<Monster> allMonsters;

    private Comparator<GameCharacter> drawOrderComparator;

    public GameScreen(SpriteBatch batch) {
        this.batch = batch;
    }

    public Map getMap() {
        return map;
    }

    public void create() {
        map = new Map();
        allCharacters = new ArrayList<>();
        allMonsters = new ArrayList<>();
        hero = new Hero(this);
        itemEmitter = new ItemEmitter();
        textEmitter = new TextEmitter();
        allCharacters.addAll(Arrays.asList(
                hero,
                new Monster(this),
                new Monster(this),
                new Monster(this),
                new Monster(this),
                new Monster(this),
                new Monster(this)
        ));
        for (int i = 0; i < allCharacters.size(); i++) {
            if (allCharacters.get(i) instanceof Monster) {
                allMonsters.add((Monster) allCharacters.get(i));
            }
        }
        font24 = new BitmapFont(Gdx.files.internal("font24.fnt"));
        stage = new Stage();

        Skin skin = new Skin();
        skin.add("simpleButton", new Texture("simpleButton.png"));

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("simpleButton");
        textButtonStyle.font = font24;

        TextButton pauseButton = new TextButton("Pause", textButtonStyle);
        TextButton exitButton = new TextButton("Exit", textButtonStyle);

        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                paused = !paused;
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        Group menuGroup = new Group();
        menuGroup.addActor(pauseButton);
        menuGroup.addActor(exitButton);
        exitButton.setPosition(150,0);
        menuGroup.setPosition(1000, 680);
        menuGroup.setVisible(true);

        stage.addActor(menuGroup);
        Gdx.input.setInputProcessor(stage);


        drawOrderComparator = new Comparator<GameCharacter>() {
            @Override
            public int compare(GameCharacter o1, GameCharacter o2) {
                return (int) (o2.getPosition().y - o1.getPosition().y);
            }
        };

        music = Gdx.audio.newMusic(Gdx.files.internal("smooth-waters-115977.mp3"));
        music.setLooping(true);
        music.setVolume(0.1f);
        music.play();

        sound = Gdx.audio.newSound(Gdx.files.internal("stingers-001-6294.mp3"));




    }

    public void render() {

        float dt = Gdx.graphics.getDeltaTime();
        update(dt);
        Gdx.gl.glClearColor(0.0f, 0.25f, 0.0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        map.render(batch);


        Collections.sort(allCharacters, drawOrderComparator);
        for (int i = 0; i < allCharacters.size(); i++) {
            allCharacters.get(i).render(batch, font24);
        }
        itemEmitter.render(batch);
        textEmitter.render(batch, font24);
        hero.renderHUD(batch, font24);
        batch.end();
        stage.draw();

    }

    public void update(float dt) {
        if (!paused) {
            spawnTimer += dt;
            if (spawnTimer > 10.0f){
                Monster monster = new Monster(this);
                allCharacters.add(monster);
                allMonsters.add(monster);
                spawnTimer = 0.0f;
            }
            for (int i = 0; i < allCharacters.size(); i++) {
                allCharacters.get(i).update(dt);
            }
            for (int i = 0; i < allMonsters.size(); i++) {
                Monster currentMonster = allMonsters.get(i);
                if (!currentMonster.isAlive()) {
                    allMonsters.remove(currentMonster);
                    allCharacters.remove(currentMonster);
                    itemEmitter.generateRandomItem(currentMonster.getPosition().x, currentMonster.getPosition().y, 5, 0.6f);
                    sound.play();
                    hero.killMonster(currentMonster);
                }
            }

            for (int i = 0; i < itemEmitter.getItems().length; i++) {
                Item it = itemEmitter.getItems()[i];
                if (it.isActive()) {
                    float dst = hero.getPosition().dst(it.getPosition());
                    if (dst < 24.0f) {
                        hero.useItem(it);
                    }
                }
            }
            textEmitter.update(dt);
            itemEmitter.update(dt);
        }
        stage.act(dt);
    }

    public Hero getHero() {
        return hero;
    }

    public List<Monster> getAllMonsters() {
        return allMonsters;
    }

    public TextEmitter getTextEmitter() {
        return textEmitter;
    }

    public ItemEmitter getItemEmitter() {
        return itemEmitter;
    }
}