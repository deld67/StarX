package ru.geekbrains.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.ScaledButton;
import ru.geekbrains.math.Rect;
import ru.geekbrains.screen.GameScreen;

public class ButtonPlay extends ScaledButton {
    private static final String BUTTON_NAME = "btPlay";
    private static final float BUTTON_HEIGHT = 0.25f;
    private static final float BUTTON_MARGIN = 0.01f;

    private final  Game game;

    public ButtonPlay(TextureAtlas atlas, Game game) {
        super(atlas.findRegion(BUTTON_NAME));
        this.game = game;
    }

    @Override
    public void resize(Rect wordBounds) {
        setHeightProportion(BUTTON_HEIGHT);
        setBottom(wordBounds.getBottom()+BUTTON_MARGIN);
        setLeft(wordBounds.getLeft()+BUTTON_MARGIN);

    }

    @Override
    public void action() {
        game.setScreen(new GameScreen());
    }
}
