package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class Logo  extends Sprite {
    private static final float V_LEN = 0.0008f;

    private Vector2 pos;
    private Vector2 v;
    private Vector2 touch;


    public Logo(Texture texture) {
        super(new TextureRegion(texture));

        pos = new Vector2();
        v = new Vector2();
        touch = new Vector2();
    }

    @Override
    public void drow(SpriteBatch batch) {
        batch.draw(
                regions[frame],
                pos.x, pos.y,
                halfWidth, halfHeight,
                getWidth(), getHeight(),
                scale,scale,
                angle
        );
    }

    @Override
    public void update(float delta) {
        //

        if(isMe(pos)){
            pos.add(v);
        }else{
            pos.set(touch);
            v.setZero();
        }
    }

    @Override
    public void resize(Rect wordBounds) {
        setHeightProportion(0.2f);
        this.pos.set(wordBounds.pos);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        this.touch.set(touch);
        v.set(touch.cpy().sub(pos));
        v.setLength(V_LEN);
        return false;
    }
}
