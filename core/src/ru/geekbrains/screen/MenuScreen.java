package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;

public class MenuScreen extends BaseScreen {
    private static final String ImgFileName = "badlogic.jpg";
    private Texture img;
    private Vector2 pos;
    private Vector2 v;
    private Vector2 touch;

    @Override
    public void show() {
        super.show();
        img = new Texture(ImgFileName);
        pos = new Vector2();
        v = new Vector2(1, 1);
        touch = new Vector2(100, 100);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (Math.round(pos.x) == Math.round(touch.x) && Math.round(pos.y) == Math.round(touch.y)){
            v.set(0,0);
        }

        pos.add(v);
        batch.begin();
        batch.draw(img, pos.x, pos.y);
        batch.end();
    }

    @Override
    public void dispose() {
        img.dispose();
        super.dispose();
    }
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, Gdx.graphics.getHeight() - screenY);
        System.out.println("touchDown touch.x = " + touch.x + " touch.y = " + touch.y);
        //pos.set(touch);
        v = touch.cpy().sub(pos).nor();

        return super.touchDown(screenX, screenY, pointer, button);
    }
}
