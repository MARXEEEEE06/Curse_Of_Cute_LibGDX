package com.javal.cute;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player {
    private float x = 200, y = 250;
    private final float speed = 200f;
    private final float sprintMultiplier = 2f;
    private Texture playerSheet;
    private Animation<TextureRegion> walkDown, walkUp, walkLeft, walkRight;
    private float stateTime = 0f;
    private final float frameDuration = 0.10f;
    private float frameTimer = 0f;
    private TextureRegion currentFrame;

    // constructor (load textures and setup animations)
    public Player() {
        playerSheet = new Texture("felis/felis_walk.png");

        // Split into 32x32 frames
        TextureRegion[][] tmp = TextureRegion.split(playerSheet, 32, 32);

        walkDown  = new Animation<>(0.1f, tmp[0]);
        walkLeft  = new Animation<>(0.1f, tmp[1]);
        walkRight = new Animation<>(0.1f, tmp[2]);
        walkUp    = new Animation<>(0.1f, tmp[3]);

        walkDown.setPlayMode(Animation.PlayMode.LOOP);
        walkLeft.setPlayMode(Animation.PlayMode.LOOP);
        walkRight.setPlayMode(Animation.PlayMode.LOOP);
        walkUp.setPlayMode(Animation.PlayMode.LOOP);

        currentFrame = walkDown.getKeyFrame(0);
    }

    public float getX() { return x; }
    public float getY() { return y; }

    // update movement and animation
    public void update(float delta) {
        float currentSpeed = speed;
        boolean moving = false;

        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            currentSpeed *= sprintMultiplier;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            x -= currentSpeed * delta;
            currentFrame = walkLeft.getKeyFrame(stateTime);
            moving = true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            x += currentSpeed * delta;
            currentFrame = walkRight.getKeyFrame(stateTime);
            moving = true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            y += currentSpeed * delta;
            currentFrame = walkUp.getKeyFrame(stateTime);
            moving = true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            y -= currentSpeed * delta;
            currentFrame = walkDown.getKeyFrame(stateTime);
            moving = true;
        }

        // Frame skipping
        if (moving) {
            frameTimer += delta;
            if (frameTimer >= frameDuration) {
                stateTime += frameDuration;
                frameTimer -= frameDuration;
            }
        } else {
            frameTimer = 0;
            stateTime = 0;
        }
    }

    // draw player
    public void render(SpriteBatch batch) {
        batch.draw(currentFrame, x, y, currentFrame.getRegionWidth() + 32, currentFrame.getRegionHeight() + 32);
    }

    public void dispose() {
        playerSheet.dispose();
    }
}
