package com.javal.cute;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Player player;
    private Forest forest;
    private OrthographicCamera camera;

    @Override
    public void create() {
        batch = new SpriteBatch();
        player = new Player();
        forest = new Forest();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600); // world size
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        player.update(delta);

        // make camera follow player
        camera.position.set(player.getX(), player.getY(), 0);
        camera.update();

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1);
        forest.render(camera);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        player.render(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        player.dispose();
        forest.dispose();
    }
}
