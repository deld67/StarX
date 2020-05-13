package ru.geekbrains.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Background;

public class GameScreen extends BaseScreen {
    private static final String BackGroundFileName = "textures/bg.png";

    private Texture backgroundImg;
    private Background background;


    @Override
    public void show() {
        super.show();
        backgroundImg = new Texture(BackGroundFileName);
        background = new Background(backgroundImg);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        drow();
    }

    @Override
    public void resize(Rect wordBounds) {
        background.resize(wordBounds);
    }



    @Override
    public boolean keyDown(int keycode) {
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        return super.keyUp(keycode);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        return super.touchDown(touch, pointer, button);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        return super.touchUp(touch, pointer, button);
    }

    private void update(float delta){

    }

    private void drow(){
        batch.begin();
        background.drow(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        backgroundImg.dispose();
        super.dispose();
    }
}
