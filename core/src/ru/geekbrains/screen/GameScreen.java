package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.MainShip;
import ru.geekbrains.sprite.Star;

public class GameScreen extends BaseScreen {
    private static final String BACKGROUND_FILE_NAME = "textures/bg.png";
    private static final String MAIN_ATLAS_FILE_PATH = "textures/mainAtlas.tpack";

    private static final int STARS_COUNT = 64;

    private Texture backgroundImg;
    private Background background;
    private TextureAtlas mainAtlas;

    private Star[] stars;
    private MainShip mainShip;
    private BulletPool bulletPool;

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
        mainShip = new MainShip(mainAtlas, bulletPool);
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
        mainShip.update(delta);
    }

    private void free() {
        bulletPool.freeAllDestroyed();
    }

    private void draw(){
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < stars.length ; i++) {
            stars[i].draw(batch);
        }
        bulletPool.drawActiveSprites(batch);
        mainShip.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        backgroundImg.dispose();
        mainAtlas.dispose();
        bulletPool.dispose();
        super.dispose();
    }
}
