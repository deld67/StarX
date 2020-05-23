package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;


import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class GameOver extends Sprite {
    private static final String GAME_OVER_NAME = "message_game_over";

    public GameOver(TextureAtlas atlas) {
        super(atlas.findRegion(GAME_OVER_NAME));
    }

    @Override
    public void resize(Rect wordBounds) {
        setHeightProportion(0.08f);
        setTop(0.1f);
    }
}
