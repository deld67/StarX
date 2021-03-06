package ru.geekbrains.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import ru.geekbrains.base.SpritesPool;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Enemy;

public class EnemyPool extends SpritesPool<Enemy> {
    private static final String ENEMY_GUN_SOOT_SOUND = "sounds/bullet.wav";

    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private Rect worldBounds;
    private Sound sound;

    public EnemyPool(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.worldBounds = worldBounds;
        sound = Gdx.audio.newSound(Gdx.files.internal(ENEMY_GUN_SOOT_SOUND));
    }

    @Override
    protected Enemy newObject() {
        return new Enemy(bulletPool,  explosionPool, worldBounds, sound);
    }

    @Override
    public void dispose() {
        super.dispose();
        sound.dispose();
    }
}
