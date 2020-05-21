package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.EnemyPool;
import ru.geekbrains.pool.ExplosionPool;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.MainShip;
import ru.geekbrains.sprite.Star;
import ru.geekbrains.utils.EnemyEmitter;

public class GameScreen extends BaseScreen {
    private static final String BACKGROUND_FILE_NAME = "textures/bg.png";
    private static final String MAIN_ATLAS_FILE_PATH = "textures/mainAtlas.tpack";
    private static final String BACKGROUND_MUSIC = "music/videoplayback.mp3";

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
        music = Gdx.audio.newMusic(Gdx.files.internal(BACKGROUND_MUSIC));
        music.play();
        music.setLooping(true);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
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
    }



    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        mainShip.touchDown(touch,pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        return false;
    }

    private void update(float delta){
        for (Star star: stars) {
            star.update(delta);
        }
        bulletPool.updateActiveSprites(delta);
        enemyPool.updateActiveSprites(delta);
        explosionPool.updateActiveSprites(delta);
        mainShip.update(delta);
        enemyEmitter.generate(delta);
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
        bulletPool.drawActiveSprites(batch);
        mainShip.draw(batch);
        enemyPool.drawActiveSprites(batch);
        explosionPool.drawActiveSprites(batch);
        batch.end();
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
        super.dispose();
    }
}
