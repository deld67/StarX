package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import javax.crypto.spec.PSource;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class MainShip extends Sprite {
    private static final String SHIP_NAME = "main_ship";
    private static final float SHIP_HEIGHT = 0.2f;
    private static final int SHIP_KEY_UP_CODE = 19;
    private static final int SHIP_KEY_DOWN_CODE = 20;
    private static final int SHIP_KEY_LEFT_CODE = 21;
    private static final int SHIP_KEY_RIGHT_CODE = 22;
    private static final float V_LEN = 0.01f;

    private Vector2 v;
    private Vector2 touch;
    private Vector2 common;
    private Rect wordBounds;

    public MainShip(TextureAtlas atlas) {
        super(atlas.findRegion(SHIP_NAME));
        atlas.findRegion(SHIP_NAME).setRegionWidth(atlas.findRegion(SHIP_NAME).originalWidth/2);
        v = new Vector2();
        touch = new Vector2();
        common = new Vector2();
        wordBounds = new Rect();
    }



    @Override
    public void update(float delta) {
        super.update(delta);
        common.set(touch);
        if (common.sub(pos).len() > V_LEN){
            pos.add(v);
        }else{
            pos.set(touch);
            v.setZero();
        }
    }

    @Override
    public void resize(Rect wordBounds) {
        this.wordBounds = wordBounds;
        setHeightProportion(SHIP_HEIGHT);
        setBottom(wordBounds.getBottom());
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        this.touch.set(touch);
        v.set(touch.sub(pos)).setLength(V_LEN);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        return super.touchUp(touch, pointer, button);
    }

    public boolean keyDown(int keycode) {

        switch (keycode){
            case SHIP_KEY_DOWN_CODE:
                v.set(0f, - V_LEN );
                break;
            case SHIP_KEY_UP_CODE:
                v.set(0f, V_LEN);
                break;
            case SHIP_KEY_LEFT_CODE:
                v.set(- V_LEN, 0f);
                break;
            case SHIP_KEY_RIGHT_CODE:
                v.set(V_LEN, 0f);
                break;
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        switch (keycode){
            case SHIP_KEY_DOWN_CODE:
            case SHIP_KEY_UP_CODE:
            case SHIP_KEY_LEFT_CODE:
            case SHIP_KEY_RIGHT_CODE:
                                    v.setZero();
                                    break;
        }

        return false;
    }

}
