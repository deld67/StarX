package ru.geekbrains.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.base.ScaledButton;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.ButtonExit;
import ru.geekbrains.sprite.ButtonPlay;
import ru.geekbrains.sprite.Logo;
import ru.geekbrains.sprite.Star;

public class MenuScreen extends BaseScreen {
    private static final String ImgFileName = "badlogic.jpg";
    private static final String BackGroundFileName = "textures/bg.png";
    private static final String AtlasFilePath = "textures/menuAtlas.tpack";

    private static final int StarsCount = 256;

    private final Game game;
    private Texture img;
    private Texture backgroundImg;
    private Background background;
    private TextureAtlas atlas;
    private Logo logo;
    private ButtonExit buttonExit;
    private ButtonPlay buttonPlay;

    private Star[] stars;

    public MenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        img = new Texture(ImgFileName);
        backgroundImg = new Texture(BackGroundFileName);
        background = new Background(backgroundImg);
        atlas = new TextureAtlas(Gdx.files.internal(AtlasFilePath));
        buttonExit = new ButtonExit(atlas);
        buttonPlay = new ButtonPlay(atlas, game);
        logo = new Logo(img);
        stars = new Star[StarsCount];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }
    }

    @Override
    public void resize(Rect wordBounds) {
        background.resize(wordBounds);
        buttonExit.resize(wordBounds);
        buttonPlay.resize(wordBounds);
        logo.resize(wordBounds);

        for (int i = 0; i < stars.length ; i++) {
            stars[i].resize(wordBounds);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        drow();


    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        buttonExit.touchDown(touch,pointer,button);
        buttonPlay.touchDown(touch,pointer,button);
        //logo.touchDown(touch,pointer,button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        buttonExit.touchUp(touch,pointer,button);
        buttonPlay.touchUp(touch,pointer,button);
        return false;
    }

    private void update(float delta){
        logo.update(delta);
        for (Star star: stars) {
            star.update(delta);
        }
    }

    private void drow(){
        batch.begin();
        background.drow(batch);

        for (int i = 0; i < stars.length ; i++) {
            stars[i].drow(batch);
        }
        logo.drow(batch);
        buttonExit.drow(batch);
        buttonPlay.drow(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        img.dispose();
        backgroundImg.dispose();
        atlas.dispose();
        super.dispose();
    }

}
