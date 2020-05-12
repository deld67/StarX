package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Logo;

public class MenuScreen extends BaseScreen {
    private static final String ImgFileName = "badlogic.jpg";
    private static final String BackGroundFileName = "starsbg1080.jpg";


    private Texture img;
    private Texture backgroundImg;
    private Background background;
    private Logo logo;



    @Override
    public void show() {
        super.show();
        img = new Texture(ImgFileName);
        backgroundImg = new Texture(BackGroundFileName);
        background = new Background(backgroundImg);
        logo = new Logo(img);

    }

    @Override
    public void resize(Rect wordBounds) {
        background.resize(wordBounds);
        logo.resize(wordBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        logo.update(delta);
        batch.begin();
        background.drow(batch);
        logo.drow(batch);
        batch.end();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        super.touchDown(touch, pointer, button);
        logo.touchDown(touch,pointer,button);
        return false;
    }

    @Override
    public void dispose() {
        img.dispose();
        backgroundImg.dispose();
        super.dispose();
    }

}
