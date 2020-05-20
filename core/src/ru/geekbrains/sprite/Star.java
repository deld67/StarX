package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;

public class Star extends Sprite {
    private static final String STAR_NAME = "star";
    private static final float STAR_HEIGHT = 0.01f;
    private static final float  DELTA_SCALE_STAR = 0.009f;

    private Vector2 v;
    private Rect wordBounds;
    private float animateTimer;
    private float animateInterval;



    public Star(TextureAtlas atlas) {
        super(atlas.findRegion(STAR_NAME));
        v = new Vector2();
        float vX = Rnd.nextFloat(-0.005f, 0.005f);
        float vY = Rnd.nextFloat(-0.2f, -0.05f);
        v.set(vX, vY);
        wordBounds = new Rect();

    }

    @Override
    public void resize(Rect wordBounds) {
        this.wordBounds = wordBounds;
        setHeightProportion(STAR_HEIGHT);
        float posX = Rnd.nextFloat(wordBounds.getLeft(), wordBounds.getRight());
        float posY = Rnd.nextFloat(wordBounds.getBottom(),wordBounds.getTop());
        pos.set(posX,posY);
        setScale(Rnd.nextFloat(0.1f,1f));
        animateInterval = Rnd.nextFloat(0.7f,1.5f);
    }

    @Override
    public void update(float delta) {
        setScale(getScale() - DELTA_SCALE_STAR);
        animateTimer += delta;
        if (animateTimer >= animateInterval){
            setScale(1f);
            animateTimer = 0f;
        }
        pos.mulAdd(v, delta);
        checkBounds();
    }

    private void checkBounds(){
        if (getRight() < wordBounds.getLeft()){setLeft(wordBounds.getRight());}
        if (getLeft() > wordBounds.getRight()){setRight(wordBounds.getLeft());}
        if (getTop() < wordBounds.getBottom()){setBottom(wordBounds.getTop());}
    }
}
