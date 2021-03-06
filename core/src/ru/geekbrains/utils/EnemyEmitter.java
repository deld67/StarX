package ru.geekbrains.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;
import ru.geekbrains.pool.EnemyPool;
import ru.geekbrains.sprite.Enemy;

public class EnemyEmitter {
    private static final String ENEMY_SMALL_NAME = "enemy0";
    private static final String ENEMY_MEDIUM_NAME = "enemy1";
    private static final String ENEMY_BIG_NAME = "enemy2";
    private static final String BULLET_ENEMY_NAME = "bulletEnemy";
    private static final float GENERATE_INTERVAL = 4f;

    private static final float ENEMY_SMALL_HEIGHT = 0.1f;
    private static final int ENEMY_SMALL_HP = 1;
    private static final float ENEMY_SMALL_BULLET_HEIGHT = 0.01f;
    private static final float ENEMY_SMALL_BULLET_VY = -0.3f;
    private static final int ENEMY_SMALL_BULLET_DAMAGE = 1;
    private static final float ENEMY_SMALL_RELOAD_INTERVAL = 3f;

    private static final float ENEMY_MEDIUM_HEIGHT = 0.15f;
    private static final int ENEMY_MEDIUM_HP = 5;
    private static final float ENEMY_MEDIUM_BULLET_HEIGHT = 0.02f;
    private static final float ENEMY_MEDIUM_BULLET_VY = -0.25f;
    private static final int ENEMY_MEDIUM_BULLET_DAMAGE = 5;
    private static final float ENEMY_MEDIUM_RELOAD_INTERVAL = 4f;

    private static final float ENEMY_BIG_HEIGHT = 0.2f;
    private static final int ENEMY_BIG_HP = 10;
    private static final float ENEMY_BIG_BULLET_HEIGHT = 0.04f;
    private static final float ENEMY_BIG_BULLET_VY = -0.3f;
    private static final int ENEMY_BIG_BULLET_DAMAGE = 10;
    private static final float ENEMY_BIG_RELOAD_INTERVAL = 1f;

    private Rect worldBounds;
    private  float generateTimer;

    private final TextureRegion[] enemySmallRegions;
    private final TextureRegion[] enemyMegiumRegions;
    private final TextureRegion[] enemyBigRegions;

    private final Vector2 enemySmallV;
    private final Vector2 enemyMediumV;
    private final Vector2 enemyBigV;

    private TextureRegion bulletRegion;

    private final EnemyPool enemyPool;

    public EnemyEmitter(TextureAtlas atlas,  EnemyPool enemyPool) {
        TextureRegion enemy0 = atlas.findRegion(ENEMY_SMALL_NAME);
        this.enemySmallRegions = Regions.split(enemy0, 1,2,2);
        TextureRegion enemy1 = atlas.findRegion(ENEMY_MEDIUM_NAME);
        this.enemyMegiumRegions = Regions.split(enemy1, 1,2,2);
        TextureRegion enemy2 = atlas.findRegion(ENEMY_BIG_NAME);
        this.enemyBigRegions = Regions.split(enemy2, 1,2,2);

        this.enemySmallV = new Vector2(0, -0.1f);
        this.enemyMediumV = new Vector2(0, -0.03f);
        this.enemyBigV  = new Vector2(0,-0.005f);

        this.bulletRegion = atlas.findRegion(BULLET_ENEMY_NAME);
        this.enemyPool = enemyPool;
    }

    public void resize(Rect worldBounds){
        this.worldBounds = worldBounds;
    }
    public void generate(float delta){
        generateTimer += delta;
        if (generateTimer > GENERATE_INTERVAL){
            generateTimer = 0;
            Enemy enemy = enemyPool.obtain();
            float type = (float) Math.random();
            if (type < 0.5f) {
                enemy.set(
                        enemySmallRegions,
                        enemySmallV,
                        bulletRegion,
                        ENEMY_SMALL_BULLET_HEIGHT,
                        ENEMY_SMALL_BULLET_VY,
                        ENEMY_SMALL_BULLET_DAMAGE,
                        ENEMY_SMALL_RELOAD_INTERVAL,
                        ENEMY_SMALL_HP,
                        ENEMY_SMALL_HEIGHT
                );
            }else  if (type < 0.8f ){
                enemy.set(
                        enemyMegiumRegions,
                        enemyMediumV,
                        bulletRegion,
                        ENEMY_MEDIUM_BULLET_HEIGHT,
                        ENEMY_MEDIUM_BULLET_VY,
                        ENEMY_MEDIUM_BULLET_DAMAGE,
                        ENEMY_MEDIUM_RELOAD_INTERVAL,
                        ENEMY_MEDIUM_HP,
                        ENEMY_MEDIUM_HEIGHT
                );
            }else{
                enemy.set(
                        enemyBigRegions,
                        enemyBigV,
                        bulletRegion,
                        ENEMY_BIG_BULLET_HEIGHT,
                        ENEMY_BIG_BULLET_VY,
                        ENEMY_BIG_BULLET_DAMAGE,
                        ENEMY_BIG_RELOAD_INTERVAL,
                        ENEMY_BIG_HP,
                        ENEMY_BIG_HEIGHT
                );
            }
            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft()+enemy.getHalfWidth(), worldBounds.getRight()-enemy.getHalfWidth());
            enemy.setBottom(worldBounds.getTop());
        }
    }
}
