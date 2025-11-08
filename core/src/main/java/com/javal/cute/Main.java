package com.javal.cute;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.awt.*;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;
    private ShapeRenderer shape;
    private float x = 200, y = 250;   // shape position
    final private float speed = 200;        // move speed
    final private float sprintMultiplier = 2f; // double speed when sprinting
    private Texture playerSheet;
    private Animation<TextureRegion> walkDown, walkUp, walkLeft, walkRight;
    private float stateTime = 0f;
    private float frameDuration = 0.15f; // seconds per frame
    private float frameTimer = 0f;       // accumulates delta

    private TextureRegion currentFrame;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
        image = new Texture("libgdx.png");
        shape = new ShapeRenderer();
        playerSheet = new Texture("felis/felis_walk.png");

        // Split sprite sheet into individual frames
        TextureRegion[][] tmp = TextureRegion.split(playerSheet, 32, 32);

        walkDown  = new Animation<>(0.1f, tmp[0]); // row 0
        walkLeft  = new Animation<>(0.1f, tmp[1]); // row 1
        walkRight = new Animation<>(0.1f, tmp[2]); // row 2
        walkUp    = new Animation<>(0.1f, tmp[3]); // row 3

        walkDown.setPlayMode(Animation.PlayMode.LOOP);
        walkLeft.setPlayMode(Animation.PlayMode.LOOP);
        walkRight.setPlayMode(Animation.PlayMode.LOOP);
        walkUp.setPlayMode(Animation.PlayMode.LOOP);

        stateTime = 0f;
        currentFrame = walkDown.getKeyFrame(0);
    }

    @Override
    public void render() {
        boolean moving = false;
        float delta = Gdx.graphics.getDeltaTime();
        float currentSpeed = speed;
        stateTime += Gdx.graphics.getDeltaTime(); // accumulate elapsed time
        TextureRegion currentFrame = walkDown.getKeyFrame(stateTime);


        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            currentSpeed *= sprintMultiplier;
        }
        System.out.println(speed);


        // 2 Handle key input
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)||Gdx.input.isKeyPressed(Input.Keys.A)) {
            x -= currentSpeed * delta;
            stateTime += delta;
            currentFrame = walkLeft.getKeyFrame(stateTime);
            moving = true;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)||Gdx.input.isKeyPressed(Input.Keys.D)) {
            x += currentSpeed * delta;
            stateTime += delta;
            currentFrame = walkRight.getKeyFrame(stateTime);
            moving = true;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.UP)||Gdx.input.isKeyPressed(Input.Keys.W)) {
            y += currentSpeed * delta;
            stateTime += delta;
            currentFrame = walkUp.getKeyFrame(stateTime);
            moving = true;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)||Gdx.input.isKeyPressed(Input.Keys.S)) {
            y -= currentSpeed * delta;
            stateTime += delta;
            currentFrame = walkDown.getKeyFrame(stateTime);
            moving = true;
        }

        // Frame skipping logic
        if (moving) {
            frameTimer += delta;
            if (frameTimer >= frameDuration) {
                stateTime += frameDuration; // advance one frame
                frameTimer -= frameDuration;
            }
        } else {
            frameTimer = 0;
            stateTime = 0; // reset to first frame when idle
        }

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
//        shape.begin(ShapeRenderer.ShapeType.Filled);
//        shape.setColor(new Color(255, 0, 0, 1));
//        shape.rect(x, y, 50, 50);
//        shape.end();
        batch.begin();
        batch.draw(currentFrame, x ,y, currentFrame.getRegionWidth()*2, currentFrame.getRegionHeight()*2);
        batch.draw(image, 200, 250);
        batch.end();

    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
