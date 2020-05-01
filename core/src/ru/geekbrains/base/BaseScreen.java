package ru.geekbrains.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BaseScreen implements Screen, InputProcessor {
    protected SpriteBatch batch;
    private static final String BackGroundFileName = "starsbg1080.jpg";
    Texture backgroundImg;

    @Override
    public void show() {
        System.out.println("show");
        Gdx.input.setInputProcessor(this);
        batch = new SpriteBatch();
        backgroundImg = new Texture(BackGroundFileName);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(backgroundImg, 0, 0);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        System.out.printf("resize width %s height %s%n", width, height);
    }

    @Override
    public void pause() {
        System.out.println("pause");
    }

    @Override
    public void resume() {
        System.out.println("resume");
    }

    @Override
    public void hide() {
        System.out.println("hide");
        dispose();
    }

    @Override
    public void dispose() {
        backgroundImg.dispose();
        batch.dispose();
        System.out.println("dispose");
    }

    @Override
    public boolean keyDown(int keycode) {
        System.out.printf("keyDown keycode = %s%n", keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        System.out.printf("keyUp keycode = %s%n", keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        System.out.printf("keyTyped character = %s%n", character);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.printf("touchDown screenX = %s screenY = %s%n"  , screenX , screenY);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        System.out.printf("touchUp screenX = %s screenY = %s%n",screenX, screenY);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        System.out.printf("touchDragged screenX = %s screenY = %s%n",screenX, screenY);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        System.out.printf("scrolled amount = %s%n", amount);
        return false;
    }
}
