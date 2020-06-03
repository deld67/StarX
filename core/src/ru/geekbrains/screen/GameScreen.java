package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.base.Font;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.EnemyPool;
import ru.geekbrains.pool.ExplosionPool;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Bullet;
import ru.geekbrains.sprite.ButtonNewGame;
import ru.geekbrains.sprite.Enemy;
import ru.geekbrains.sprite.GameOver;
import ru.geekbrains.sprite.MainShip;
import ru.geekbrains.sprite.Star;
import ru.geekbrains.utils.EnemyEmitter;

public class GameScreen extends BaseScreen {
    private enum State{PLAYING, GAME_OVER}

    private static final String BACKGROUND_FILE_NAME = "textures/bg.png";
    private static final String MAIN_ATLAS_FILE_PATH = "textures/mainAtlas.tpack";
    private static final String BACKGROUND_MUSIC = "music/videoplayback.mp3";
    private static final String FONT_FILENAME ="font/font.fnt";
    private static final String FONT_IMAGE_FILENAME = "font/font.png";
    private static final String FRAGS = "Frags: ";
    private static final String HP = "HP: ";
    private static final String LEVEL = "Level: ";

    private static final float FONT_SIZE = 0.02f;
    private static final float TEXT_MERGIH = 0.01f;

    private static final int STARS_COUNT = 64;

    private Texture backgroundImg;
    private Background background;
    private TextureAtlas mainAtlas;

    private Star[] stars;
    private MainShip mainShip;
    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private ExplosionPool explosionPool;
    private EnemyEmitter enemyEmitter;
    private Music music;

    private State state;

    private GameOver gameOver;
    private ButtonNewGame buttonNewGame;
    private int frags;
    private Font font;
    private StringBuilder sbFrags;
    private StringBuilder sbHp;
    private StringBuilder sbLevel;

    @Override
    public void show() {
        super.show();
        backgroundImg = new Texture(BACKGROUND_FILE_NAME);
        background = new Background(backgroundImg);
        mainAtlas = new TextureAtlas(Gdx.files.internal(MAIN_ATLAS_FILE_PATH));
        stars = new Star[STARS_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(mainAtlas);
        }
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(mainAtlas);
        enemyPool = new EnemyPool(bulletPool,explosionPool, wordBounds);
        enemyEmitter = new EnemyEmitter(mainAtlas, enemyPool);
        mainShip = new MainShip(mainAtlas, bulletPool, explosionPool);
        gameOver = new GameOver(mainAtlas);
        buttonNewGame = new ButtonNewGame(mainAtlas, this);
        font = new Font(FONT_FILENAME, FONT_IMAGE_FILENAME);
        sbFrags = new StringBuilder();
        sbHp = new StringBuilder();
        sbLevel = new StringBuilder();
        music = Gdx.audio.newMusic(Gdx.files.internal(BACKGROUND_MUSIC));
        music.play();
        music.setLooping(true);
        state = State.PLAYING;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollision();
        free();
        draw();
    }

    @Override
    public void resize(Rect wordBounds) {
        background.resize(wordBounds);
        for (int i = 0; i < stars.length ; i++) {
            stars[i].resize(wordBounds);
        }
        mainShip.resize(wordBounds);
        enemyEmitter.resize(wordBounds);
        gameOver.resize(wordBounds);
        buttonNewGame.resize(wordBounds);
        font.setSize(FONT_SIZE);
    }



    @Override
    public boolean keyDown(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyUp(keycode);
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            mainShip.touchDown(touch, pointer, button);
        }else if (state == State.GAME_OVER){
            buttonNewGame.touchDown(touch,pointer,button);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            mainShip.touchUp(touch, pointer, button);
        }else if (state == State.GAME_OVER){
            buttonNewGame.touchUp(touch,pointer,button);
        }
        return false;
    }

    public void startNewGame(){
        mainShip.reStartSheep();
        bulletPool.freeAllActiveObject();
        explosionPool.freeAllActiveObject();
        enemyPool.freeAllActiveObject();
        state = State.PLAYING;
        music.play();
        music.setLooping(true);
        frags = 0;
    }
    @Override
    public void dispose() {
        music.stop();
        music.dispose();
        backgroundImg.dispose();
        mainAtlas.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        explosionPool.dispose();
        mainShip.dispose();
        font.dispose();
        super.dispose();
    }
    private void update(float delta){
        for (Star star: stars) {
            star.update(delta);
        }
        explosionPool.updateActiveSprites(delta);
        if (state == State.PLAYING){
            mainShip.update(delta);
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            enemyEmitter.generate(delta, frags);
        }else if (state == State.GAME_OVER){
            buttonNewGame.update(delta);
        }
    }

    private void checkCollision(){
        if (state != State.PLAYING){
            return;
        }
        List<Enemy> enemyList = enemyPool.getActiveObjects();
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Enemy enemy: enemyList) {
            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
            if(mainShip.pos.dst(enemy.pos) < minDist){
                enemy.destroy();
                mainShip.damage(enemy.getDamage());
                continue;
            }
            for (Bullet bullet: bulletList ) {
                if (bullet.getOwner() != mainShip || bullet.isDestroyed()){
                    continue;
                }
                if (enemy.isBulletCollision(bullet)){
                    enemy.damage(bullet.getDamage());
                    bullet.destroy();
                    if (enemy.isDestroyed()){
                        frags++;
                    }
                }
            }
        }
        for (Bullet bullet: bulletList ) {
            if (bullet.getOwner() == mainShip||bullet.isDestroyed()){
                continue;
            }
            if (mainShip.isBulletCollision(bullet)){
                mainShip.damage(bullet.getDamage());
                bullet.destroy();
            }
        }
        if (mainShip.isDestroyed()){
            state = State.GAME_OVER;
        }
    }

    private void free() {
        bulletPool.freeAllDestroyed();
        enemyPool.freeAllDestroyed();
        explosionPool.freeAllDestroyed();
    }

    private void draw(){
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < stars.length ; i++) {
            stars[i].draw(batch);
        }
        if (state == State.PLAYING) {
            bulletPool.drawActiveSprites(batch);
            mainShip.draw(batch);
            enemyPool.drawActiveSprites(batch);
        }else if(state == State.GAME_OVER){
            gameOver.draw(batch);
            buttonNewGame.draw(batch);
            music.stop();
        }
        explosionPool.drawActiveSprites(batch);
        printInfo();
        batch.end();
    }

    private void printInfo(){
        sbFrags.setLength(0);
        sbHp.setLength(0);
        sbLevel.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), wordBounds.getLeft()+TEXT_MERGIH, wordBounds.getTop() - TEXT_MERGIH);
        font.draw(batch, sbHp.append(HP).append(mainShip.getHp()), wordBounds.pos.x, wordBounds.getTop()-TEXT_MERGIH, Align.center);
        font.draw(batch,sbLevel.append(LEVEL).append(enemyEmitter.getLevel()),wordBounds.getRight()-TEXT_MERGIH,wordBounds.getTop() - TEXT_MERGIH, Align.right);
    }
}
