package com.mygdxmario.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MarioBros extends Game {

    public static final float PPM = 100;
    public static int V_WIDTH = 400;
    public static int V_HEIGHT = 208;

    public static short GROUND_BIT = 1;
    public static short MARIO_BIT = 2;
    public static short BRICK_BIT = 4;
    public static short COIN_BIT = 8;
    public static short DESTROYED_BIT = 16;
    public static short OBJECT_BIT = 32;
    public static short GOOMBA_BIT = 64;
    public static short MARIO_HEAD_BIT = 128;
    public static short GOOMBA_HEAD_BIT = 256;
    public static short BREAD_BIT = 512;

	SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
        setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
        super.render();

	}
}
