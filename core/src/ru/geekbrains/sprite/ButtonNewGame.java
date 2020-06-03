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
    private static final float BUTTON_ANIMATE_INTERVAL = 1f;
    private static final float BUTTON_ANIMATED_SCALE = 0.001f;

    private final GameScreen gameScreen;
    private float animateatimer;
    private boolean scaleUp = true;

    public ButtonNewGame(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion(BUTTON_NAME));
        this.gameScreen = gameScreen;
    }

    @Override
    public void update(float delta) {
        animateatimer += delta;
        if (animateatimer >= BUTTON_ANIMATE_INTERVAL){
            animateatimer = 0f;
            scaleUp = !scaleUp;
        }

        if (scaleUp){
            setScale(getScale() + BUTTON_ANIMATED_SCALE );
        } else {
            setScale(getScale() - BUTTON_ANIMATED_SCALE);
        }
    }

    @Override
    public void resize(Rect wordBounds) {
        setHeightProportion(BUTTON_HEIGHT);
        setBottom(wordBounds.getBottom()+BUTTON_MARGIN);
        setRight(wordBounds.getRight()-BUTTON_MARGIN);

    }

    @Override
    public void action() {
        gameScreen.startNewGame();
        return;
    }
}
