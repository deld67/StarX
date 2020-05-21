package ru.geekbrains.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Ship;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.ExplosionPool;

public class MainShip extends Ship {
    private static final String SHIP_NAME = "main_ship";
    private static final String SHIP_BULLET_NAME = "bulletMainShip";
    private static final String SHIP_GUN_SOOT_SOUND = "sounds/soot.mp3";
    private static final float SHIP_HEIGHT = 0.15f;
    private static final float MARGIN = 0.05f;
    private static final int INVALID_POINTER = -1;
    private static final int HP = 100;


    private int leftPointer;
    private int rightPointer;

    private boolean pressedLeft;
    private boolean pressedRight;



    public MainShip(TextureAtlas atlas, BulletPool bulletPool, ExplosionPool explosionPool) {
        super(atlas.findRegion(SHIP_NAME), 1, 2, 2);
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        bulletRegion = atlas.findRegion(SHIP_BULLET_NAME);
        bulletV = new Vector2(0, 0.5f);
        v0.set(0.5f, 0);
        leftPointer = INVALID_POINTER;
        rightPointer = INVALID_POINTER;
        sound = Gdx.audio.newSound(Gdx.files.internal(SHIP_GUN_SOOT_SOUND));
        startAutoShoot = false;
        reloadInterval = 0.1f ;
        bulletHight = 0.01f;
        damage = 1;
        hp = HP;
    }

    @Override
    public void resize(Rect wordBounds) {
        super.resize(wordBounds);
        setHeightProportion(SHIP_HEIGHT);
        setBottom(wordBounds.getBottom() + MARGIN);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (getLeft() < worldBounds.getLeft()) {
            stop();
            setLeft(worldBounds.getLeft());
        }
        if (getRight() > worldBounds.getRight()) {
            stop();
            setRight(worldBounds.getRight());
        }

    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (touch.x < worldBounds.pos.x) {
            if (leftPointer != INVALID_POINTER) {
                return false;
            }
            leftPointer = pointer;
            moveLeft();
        } else {
            if (rightPointer != INVALID_POINTER) {
                return false;
            }
            rightPointer = pointer;
            moveRight();
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (pointer == leftPointer) {
            leftPointer = INVALID_POINTER;
            if (rightPointer != INVALID_POINTER) {
                moveRight();
            } else {
                stop();
            }
        } else if (pointer == rightPointer) {
            rightPointer = INVALID_POINTER;
            if (leftPointer != INVALID_POINTER) {
                moveLeft();
            } else {
                stop();
            }
        }
        return false;
    }

    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                moveRight();
                break;
            case Input.Keys.UP:
                startAutoShoot = true;
                break;
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if (pressedRight) {
                    moveRight();
                } else {
                    stop();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if (pressedLeft) {
                    moveLeft();
                } else {
                    stop();
                }
                break;
            case Input.Keys.UP:
                startAutoShoot = false;
                break;
        }
        return false;
    }
    public void dispose(){
        sound.dispose();
    }
    private void moveRight() {
        v.set(v0);
    }

    private void moveLeft() {
        v.set(v0).rotate(180);
    }

    private void stop() {
        v.setZero();
    }

}
