package com.mygdxmario.game.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdxmario.game.GameScreen;
import com.mygdxmario.game.MarioBros;
import com.mygdxmario.game.U;

/**
 * Created by Jeevs on 25-10-2015.
 */
public class Goomba extends Enemy{

    private Rectangle rectangle;
    public static boolean right = true;
    private Animation goombaRun;
    private float stateTime;
    private boolean goombaDead;
    private boolean setDead;

    public Goomba(World world,GameScreen screen,Rectangle rectangle) {
        super(world, screen);
        this.rectangle = rectangle;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i=0;i<2;i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("goomba"),i*16,0,16,16));
        goombaRun = new Animation(0.4f,frames);
        goombaDead = false;
        setDead = false;
        stateTime = 0;
        setBounds(0,0,16/MarioBros.PPM,16/MarioBros.PPM);
        defineEnemy();
    }

    private void defineEnemy() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(rectangle.getX()/MarioBros.PPM,rectangle.getY()/MarioBros.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(7 / MarioBros.PPM);
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = MarioBros.GOOMBA_BIT;
        body.createFixture(fixtureDef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-4f/MarioBros.PPM,8/MarioBros.PPM),new Vector2(4f/MarioBros.PPM,8/MarioBros.PPM));
        fixtureDef.shape = head;
        fixtureDef.filter.maskBits = (short) (MarioBros.MARIO_BIT);
        fixtureDef.filter.categoryBits = MarioBros.GOOMBA_HEAD_BIT;
        body.createFixture(fixtureDef).setUserData(this);

    }

    public void update(float dt){

        stateTime += dt;


        if(!goombaDead && setDead) {
            world.destroyBody(body);
            setRegion(new TextureRegion(screen.getAtlas().findRegion("goomba"),32,0,16,16));
            goombaDead = true;
            stateTime = 0;

        }
        else if(!goombaDead){

            body.setLinearVelocity(velocity);
            setPosition(body.getPosition().x - getWidth()/2,body.getPosition().y - getHeight()/2);

            setRegion(goombaRun.getKeyFrame(stateTime,true));
        }

    }

    public void draw(Batch batch){
        if(stateTime < 1 || !goombaDead){
            super.draw(batch);
        }
    }



    public void log() {
        U.log("Goomba Died");
        setDead = true;
    }
}
