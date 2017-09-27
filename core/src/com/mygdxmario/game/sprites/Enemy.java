package com.mygdxmario.game.sprites;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdxmario.game.GameScreen;

/**
 * Created by Jeevs on 25-10-2015.
 */
public abstract class Enemy extends Sprite{

    protected World world;
    protected TiledMap tiledMap;
    protected GameScreen screen;
    protected Rectangle bounds;
    protected Body body;
    protected Vector2 velocity;
    protected Fixture fixture;
    protected FixtureDef fixtureDef;

    public Enemy(World world,GameScreen screen){
        this.world = world;
        this.screen = screen;
        velocity = new Vector2(0.5f,0);

    }

    public void reverseVelocity(boolean x,boolean y){
        if(x)
            velocity.x = -velocity.x;

        if(y)
            velocity.y = -velocity.y;
    }
}
