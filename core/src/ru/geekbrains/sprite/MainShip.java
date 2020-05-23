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
    private static final float[] BULLET_HEIGHT = new float[]{0.01f, 0.015f, 0.02f, 0.025f, 0.03f};

    private static final int[] BULLET_DAMAGE = new int[]{1, 2, 3, 5, 10};
    private static final int INVALID_POINTER = -1;
    private static final int HP = 10;


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
        bulletHight = BULLET_HEIGHT[0];
        damage = BULLET_DAMAGE[0];
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
        bulletPos.set(pos.x, pos.y+getHalfHeight());
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
            case Input.Keys.NUM_1:
                bulletHight = BULLET_HEIGHT[0];
                damage = BULLET_DAMAGE[0];
                break;
            case Input.Keys.NUM_2:
                bulletHight = BULLET_HEIGHT[1];
                damage = BULLET_DAMAGE[1];
                break;
            case Input.Keys.NUM_3:
                bulletHight = BULLET_HEIGHT[2];
                damage = BULLET_DAMAGE[2];
                break;
            case Input.Keys.NUM_4:
                bulletHight = BULLET_HEIGHT[3];
                damage = BULLET_DAMAGE[3];
                break;
            case Input.Keys.NUM_5:
                bulletHight = BULLET_HEIGHT[4];
                damage = BULLET_DAMAGE[4];
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

    public boolean isBulletCollision(Bullet bullet){
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > pos.y
                || bullet.getTop() < getBottom()
        );
    }
}
