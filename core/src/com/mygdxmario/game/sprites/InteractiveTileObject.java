package com.mygdxmario.game.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdxmario.game.MarioBros;
import com.mygdxmario.game.U;

/**
 * Created by Jeevs on 23-10-2015.
 */
public abstract class InteractiveTileObject {

    protected World world;
    protected TiledMap tiledMap;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;
    protected FixtureDef fixtureDef;

    public InteractiveTileObject(World world,TiledMap map,Rectangle bounds){
        this.world = world;
        this.tiledMap = map;
        this.bounds = bounds;


        BodyDef bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((bounds.getX()+bounds.getWidth()/2)/ MarioBros.PPM,(bounds.getY()+bounds.getHeight()/2)/MarioBros.PPM);

        body = world.createBody(bodyDef);

        shape.setAsBox((bounds.getWidth()/2)/MarioBros.PPM,(bounds.getHeight()/2)/MarioBros.PPM);
        fixtureDef.shape = shape;
        setFilterBits(fixtureDef);
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
    }

    public abstract void setFilterBits(FixtureDef fixtureDef);

    public abstract void log();


    public TiledMapTileLayer.Cell getCell() {
        TiledMapTileLayer tiledMapTileLayer = (TiledMapTileLayer) tiledMap.getLayers().get(1);
        return tiledMapTileLayer.getCell((int)(body.getPosition().x * MarioBros.PPM /16) ,(int)(body.getPosition().y * MarioBros.PPM /16));
    }
}
