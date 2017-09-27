package com.mygdxmario.game.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdxmario.game.GameScreen;
import com.mygdxmario.game.MarioBros;

/**
 * Created by Jeevs on 23-10-2015.
 */
public class Mario extends Sprite {

    public World world;
    public Body body;
    private TextureRegion marioStand;



    private enum State { STANDING,JUMPING,RUNNING,FALLING };
    private State currentState;
    private State previousState;
    private boolean runningRight;
    private Animation marioRun;
    private Animation marioJump;
    private float stateTimer;

    public Mario(World world,GameScreen screen){

        super(screen.getAtlas().findRegion("little_mario"));
        this.world = world;
        defineMario();

        marioStand = new TextureRegion(getTexture(),0,0,16,16);
        setBounds(0,0,16/MarioBros.PPM,16/MarioBros.PPM);
        setRegion(marioStand);

        currentState = State.STANDING;
        previousState = State.STANDING;
        runningRight = true;
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i=1;i<4;i++)
            frames.add(new TextureRegion(getTexture(),i*16,0,16,16));
        marioRun = new Animation(0.1f,frames);
        frames.clear();
        for(int i=4;i<6;i++)
            frames.add(new TextureRegion(getTexture(),i*16,0,16,16));
        marioJump = new Animation(0.1f,frames);

    }

    private void defineMario() {

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(32/MarioBros.PPM,32/MarioBros.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(7/MarioBros.PPM);
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = MarioBros.MARIO_BIT;
        fixtureDef.filter.maskBits = (short) (MarioBros.GOOMBA_HEAD_BIT | MarioBros.OBJECT_BIT | MarioBros.GROUND_BIT | MarioBros.GOOMBA_BIT |MarioBros.BRICK_BIT | MarioBros.COIN_BIT);
        body.createFixture(fixtureDef);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-0.1f/MarioBros.PPM,7/MarioBros.PPM),new Vector2(0.1f/MarioBros.PPM,7/MarioBros.PPM));
        fixtureDef.shape = head;
        fixtureDef.filter.categoryBits = MarioBros.MARIO_HEAD_BIT;
        fixtureDef.filter.maskBits = (short) (MarioBros.BRICK_BIT | MarioBros.COIN_BIT);
        body.createFixture(fixtureDef).setUserData("mario_head");


    }

    public void update(float deltaTime){
        setPosition(body.getPosition().x - getWidth()/2,body.getPosition().y - getHeight()/2);

        setRegion(getFrame(deltaTime));
    }

    private TextureRegion getFrame(float deltaTime) {

        currentState = getState();

        TextureRegion region = new TextureRegion();
        switch(currentState){
            case JUMPING:
                region = marioJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = marioRun.getKeyFrame(stateTimer,true);
                break;
            case STANDING:
            case FALLING:
                default:
                    region = marioStand;
                    break;

        }

        stateTimer = currentState == previousState ? stateTimer+deltaTime : 0;
        previousState = currentState;
        return getCorrectRegion(region);


    }

    private TextureRegion getCorrectRegion(TextureRegion r) {
        TextureRegion region = r;
        if(body.getLinearVelocity().x > 0){
            if(region.isFlipX())
                region.flip(true,false);
            runningRight = true;
            return region;
        }else if(body.getLinearVelocity().x < 0){
            if(!region.isFlipX())
                region.flip(true,false);
            runningRight = false;
            return region;
        }else{
            if(runningRight){
                if(region.isFlipX())
                    region.flip(true,false);
                return region;
            }else{
                if(!region.isFlipX())
                    region.flip(true,false);
                return  region;
            }
        }
    }

    public State getState() {

        if(body.getLinearVelocity().y > 0){
            return State.JUMPING;
        }
        else if(body.getLinearVelocity().y < 0){
            return State.JUMPING;
        }
        else if(body.getLinearVelocity().y < 0 && previousState == State.JUMPING){
            return State.FALLING;
        }
        else if(body.getLinearVelocity().x != 0){
            return State.RUNNING;
        }
        else{
            return State.STANDING;
        }


    }
}
