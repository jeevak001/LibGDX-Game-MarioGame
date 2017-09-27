package com.mygdxmario.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdxmario.game.GameScreen;
import com.mygdxmario.game.MarioBros;
import com.mygdxmario.game.U;
import com.mygdxmario.game.sprites.Brick;
import com.mygdxmario.game.sprites.Coin;
import com.mygdxmario.game.sprites.Goomba;

/**
 * Created by Jeevs on 23-10-2015.
 */
public class B2WorldCreator {

    private Array<Goomba> goombas;

    public B2WorldCreator(World world,GameScreen screen,TiledMap map){

        goombas = new Array<Goomba>();

        //Box2D Body and Fixtures
        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;

        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rectangle.getX()+rectangle.getWidth()/2)/ MarioBros.PPM,(rectangle.getY()+rectangle.getHeight()/2)/MarioBros.PPM);

            body = world.createBody(bodyDef);

            shape.setAsBox((rectangle.getWidth()/2)/MarioBros.PPM,(rectangle.getHeight()/2)/MarioBros.PPM);
            fixtureDef.shape = shape;
            fixtureDef.filter.categoryBits = MarioBros.GROUND_BIT;
            body.createFixture(fixtureDef);
        }

        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rectangle.getX()+rectangle.getWidth()/2)/MarioBros.PPM,(rectangle.getY()+rectangle.getHeight()/2)/MarioBros.PPM);

            body = world.createBody(bodyDef);

            shape.setAsBox((rectangle.getWidth()/2)/MarioBros.PPM,(rectangle.getHeight()/2)/MarioBros.PPM);
            fixtureDef.shape = shape;
            fixtureDef.filter.categoryBits = MarioBros.OBJECT_BIT;
            body.createFixture(fixtureDef).setUserData("pipe");
        }

        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

            new Brick(world,map,rectangle);
        }

        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

            new Coin(world,map,rectangle);
        }


        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
                Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                goombas.add(new Goomba(world, screen, rectangle));
        }

        for(MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)){
            U.log("Got Data");
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            bodyDef = new BodyDef();
            bodyDef.position.set((rectangle.getX() + 7)/MarioBros.PPM,(rectangle.getY() + 7)/MarioBros.PPM);
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            body = world.createBody(bodyDef);

            fixtureDef = new FixtureDef();
            CircleShape circle = new CircleShape();
            circle.setRadius(7 / MarioBros.PPM);
            fixtureDef.shape = circle;
            fixtureDef.filter.categoryBits = MarioBros.BREAD_BIT;
            body.createFixture(fixtureDef);
        }
    }

    public Array<Goomba> getGoombas() {
        return goombas;
    }
}
