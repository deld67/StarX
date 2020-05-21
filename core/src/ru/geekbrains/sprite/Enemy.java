package ru.geekbrains.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Ship;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.ExplosionPool;

public class Enemy extends Ship {

    private final Vector2 startV;

    public Enemy(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, Sound sound) {
        super(bulletPool,  explosionPool, worldBounds, sound);
        startV = new Vector2(0, -0.5f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (getBottom() <= worldBounds.getBottom()){
            destroy();
        }
        if(getTop() <= worldBounds.getTop()){
            v.set(v0);
            this.startAutoShoot = true;
        }
    }

    public void set(
            TextureRegion[] regions,
            Vector2 v0,
            TextureRegion bulletRegion,
            float bulletHight,
            float bulletVY,
            int damage,
            float reloadInterval,
            int hp,
            float height
    ){
        this.regions = regions;
        this.v0.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletHight = bulletHight;
        this.bulletV.set(0,bulletVY);
        this.damage = damage;
        this.reloadInterval = reloadInterval;
        this.reloadTimer = reloadInterval;
        this.hp = hp;
        setHeightProportion(height);
        this.v.set(startV);
    }

}
