package ru.geekbrains.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.ScaledButton;
import ru.geekbrains.math.Rect;
import ru.geekbrains.screen.GameScreen;

public class ButtonNewGame extends ScaledButton {
    private static final String BUTTON_NAME = "button_new_game";
    private static final float BUTTON_HEIGHT = 0.05f;
    private static final float BUTTON_MARGIN = 0.01f;

    private final GameScreen gameScreen;

    public ButtonNewGame(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion(BUTTON_NAME));
        this.gameScreen = gameScreen;
    }

    @Override
    public void resize(Rect wordBounds) {
        setHeightProportion(BUTTON_HEIGHT);
        setBottom(wordBounds.getBottom()+BUTTON_MARGIN);
        setRight(wordBounds.getRight()-BUTTON_MARGIN);

    }

    @Override
    public void action() {
        gameScreen.startGame();
        return;
    }
}
