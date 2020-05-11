package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Background;

public class MenuScreen extends BaseScreen {
    private static final String ImgFileName = "badlogic.jpg";
    private static final String BackGroundFileName = "starsbg1080.jpg";
    private static final float V_LEN = 0.8f;

    private Texture img;
    private Texture backgroundImg;
    private Background background;

    private Vector2 pos;
    private Vector2 v;

    private Vector2 common;

    @Override
    public void show() {
        super.show();
        img = new Texture(ImgFileName);
        backgroundImg = new Texture(BackGroundFileName);
        background = new Background(backgroundImg);

        pos = new Vector2();
        v = new Vector2();

        common = new Vector2();


    }

    @Override
    public void resize(Rect wordBounds) {
        background.resize(wordBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
       // common.set(touch);

        /*if (common.sub(pos).len() > V_LEN){
            pos.add(v);
        }else {
            pos.set(touch);
            v.setZero();
        }*/
        batch.begin();
        background.drow(batch);
        //batch.draw(img, pos.x, pos.y, 0.5f, 0.5f);
        batch.end();
    }

    @Override
    public void dispose() {
        img.dispose();
        backgroundImg.dispose();
        super.dispose();
    }
   /* @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, Gdx.graphics.getHeight() - screenY);
        v.set(touch.cpy().sub(pos));
        v.setLength(V_LEN);
        return super.touchDown(screenX, screenY, pointer, button);
    }*/
}
