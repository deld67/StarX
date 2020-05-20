package ru.geekbrains.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.base.ScaledButton;
import ru.geekbrains.math.Rect;

public class ButtonExit extends ScaledButton {
    private static final String BUTTON_NAME = "btExit";
    private static final float BUTTON_HEIGHT = 0.2f;
    private static final float BUTTON_MARGIN = 0.01f;

    public ButtonExit(TextureAtlas atlas) {
        super(atlas.findRegion(BUTTON_NAME));
    }

    @Override
    public void resize(Rect wordBounds) {
        setHeightProportion(BUTTON_HEIGHT);
        setBottom(wordBounds.getBottom()+BUTTON_MARGIN);
        setRight(wordBounds.getRight()-BUTTON_MARGIN);
    }

    @Override
    public void action() {
        Gdx.app.exit();
    }
}
