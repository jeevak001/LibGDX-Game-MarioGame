package com.mygdxmario.game.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdxmario.game.MarioBros;
import com.mygdxmario.game.U;

/**
 * Created by Jeevs on 23-10-2015.
 */
public class Coin extends InteractiveTileObject{

    public TiledMapTileSet tiledMapTileSet;
    public static int BLANK_COIN = 28;

    public Coin(World world, TiledMap map, Rectangle bounds) {

        super(world, map, bounds);
        setFilterBits(fixtureDef);
        tiledMapTileSet = map.getTileSets().getTileSet("tileset_gutter");
    }

    @Override
    public void setFilterBits(FixtureDef fixtureDef) {
        fixtureDef.filter.categoryBits = MarioBros.COIN_BIT;
    }


    @Override
    public void log() {
        U.log("Coin");
        getCell().setTile(tiledMapTileSet.getTile(BLANK_COIN));
    }



}
