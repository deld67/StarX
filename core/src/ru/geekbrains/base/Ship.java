package ru.geekbrains.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.ExplosionPool;
import ru.geekbrains.sprite.Bullet;
import ru.geekbrains.sprite.Explosion;

public class Ship extends Sprite {
    private static final float DAMAGE_ANIMATE_INTERVAL = 0.1f;

    protected Vector2 v0;
    protected final  Vector2 v;

    protected Rect worldBounds;

    protected ExplosionPool explosionPool;
    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletV;
    protected Vector2 bulletPos;
    protected float reloadTimer;
    protected float reloadInterval;
    protected float bulletHight;
    protected boolean startAutoShoot;

    protected int damage;
    protected Sound sound;
    protected int hp;

    private float damageAnimateTimer;

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
        v0 = new Vector2();
        v = new Vector2();
        startAutoShoot = false;
        bulletPos = new Vector2();
        damageAnimateTimer = DAMAGE_ANIMATE_INTERVAL;
    }

    public Ship(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, Sound sound ){
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.worldBounds = worldBounds;
        this.sound = sound;

        v0 = new Vector2();
        v = new Vector2();
        bulletV = new Vector2();
        bulletPos = new Vector2();
        startAutoShoot = false;
        damageAnimateTimer = DAMAGE_ANIMATE_INTERVAL;
    }

    @Override
    public void resize(Rect wordBounds) {
        super.resize(wordBounds);
        this.worldBounds = wordBounds;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
        damageAnimateTimer += delta;
        if (damageAnimateTimer >= DAMAGE_ANIMATE_INTERVAL){
            frame = 0;
        }
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval && startAutoShoot) {
            reloadTimer = 0f;
            shoot();
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        boom();
    }

    private void boom(){
        Explosion explosion = explosionPool.obtain();
        explosion.set(getHeight(), pos);
    }

    public void damage(int damage){
        damageAnimateTimer = 0;
        frame = 1;
        hp -= damage;
        if (hp <= 0){
            hp = 0;
            destroy();
        }
    }

    protected void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, bulletPos, bulletV, bulletHight, worldBounds, damage);
        sound.play(1.0f);
    }

    public int getDamage() {
        return damage;
    }
}
