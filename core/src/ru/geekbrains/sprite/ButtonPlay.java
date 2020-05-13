package ru.geekbrains.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.ScaledButton;
import ru.geekbrains.math.Rect;
import ru.geekbrains.screen.GameScreen;

public class ButtonPlay extends ScaledButton {
    private static final String BUTTONNAME = "btPlay";
    private static final float BUTTONHEIGHT = 0.25f;
    private static final float BUTTONMARGIN = 0.01f;

    private final  Game game;

    public ButtonPlay(TextureAtlas atlas, Game game) {
        super(atlas.findRegion(BUTTONNAME));
        this.game = game;
    }

    @Override
    public void resize(Rect wordBounds) {
        setHeightProportion(BUTTONHEIGHT);
        setBottom(wordBounds.getBottom()+BUTTONMARGIN);
        setLeft(wordBounds.getLeft()+BUTTONMARGIN);

    }

    @Override
    public void action() {
        game.setScreen(new GameScreen());
    }
}
