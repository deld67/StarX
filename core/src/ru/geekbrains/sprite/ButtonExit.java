package ru.geekbrains.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.base.ScaledButton;
import ru.geekbrains.math.Rect;

public class ButtonExit extends ScaledButton {
    private static final String BUTTONNAME = "btExit";
    private static final float BUTTONHEIGHT = 0.2f;
    private static final float BUTTONMARGIN = 0.01f;

    public ButtonExit(TextureAtlas atlas) {
        super(atlas.findRegion(BUTTONNAME));
    }

    @Override
    public void resize(Rect wordBounds) {
        setHeightProportion(BUTTONHEIGHT);
        setBottom(wordBounds.getBottom()+BUTTONMARGIN);
        setRight(wordBounds.getRight()-BUTTONMARGIN);
    }

    @Override
    public void action() {
        Gdx.app.exit();
    }
}
