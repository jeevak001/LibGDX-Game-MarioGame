package com.mygdxmario.game.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdxmario.game.MarioBros;
import com.mygdxmario.game.U;

/**
 * Created by Jeevs on 23-10-2015.
 */
public class Brick extends InteractiveTileObject{


    public Brick(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);

    }

    @Override
    public void setFilterBits(FixtureDef fixtureDef) {
        fixtureDef.filter.categoryBits = MarioBros.BRICK_BIT;

    }

    @Override
    public void log() {
        U.log("Brick");
        Filter filter = new Filter();
        filter.categoryBits = MarioBros.DESTROYED_BIT;
        fixture.setFilterData(filter);
        getCell().setTile(null);
    }


}
