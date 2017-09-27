package com.mygdxmario.game.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdxmario.game.MarioBros;
import com.mygdxmario.game.U;
import com.mygdxmario.game.sprites.Brick;
import com.mygdxmario.game.sprites.Coin;
import com.mygdxmario.game.sprites.Goomba;
import com.mygdxmario.game.sprites.InteractiveTileObject;

/**
 * Created by Jeevs on 24-10-2015.
 */
public class WorldContactListener implements ContactListener{

    @Override
    public void beginContact(Contact contact) {


        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        Fixture object;
        Fixture head;

        int collision = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        switch (collision){

            case (3):
                U.log("Mario and Ground");
                break;
            case (34):
                U.log("Mario and Object");
                break;
            case (66):
                U.log("Mario and Enemy");
                break;
            case (132):
                U.log("Mario's Head and Brick");
                if(fixA.getFilterData().categoryBits == MarioBros.BRICK_BIT)
                    ((Brick)fixA.getUserData()).log();
                else if(fixB.getFilterData().categoryBits == MarioBros.BRICK_BIT)
                    ((Brick)fixB.getUserData()).log();
                break;
            case (136): {

                U.log("Mario's Head and Coin");
                if(fixA.getFilterData().categoryBits == MarioBros.COIN_BIT)
                    ((Coin)fixA.getUserData()).log();
                else if(fixB.getFilterData().categoryBits == MarioBros.COIN_BIT)
                    ((Coin)fixB.getUserData()).log();
                break;
            }
            case (258):
                U.log("Mario and Goomba Head");
                if(fixA.getFilterData().categoryBits == MarioBros.GOOMBA_HEAD_BIT)
                    ((Goomba)fixA.getUserData()).log();
                else if(fixB.getFilterData().categoryBits == MarioBros.GOOMBA_HEAD_BIT)
                    ((Goomba)fixB.getUserData()).log();
                break;
            case (96):
                U.log("Goomba and Object");
                if(fixA.getFilterData().categoryBits == MarioBros.GOOMBA_BIT)
                    ((Goomba)fixA.getUserData()).reverseVelocity(true,false);
                else if(fixB.getFilterData().categoryBits == MarioBros.GOOMBA_BIT)
                    ((Goomba)fixB.getUserData()).reverseVelocity(true,false);
                break;
            case (64):
                U.log("Goomba and Goomba");
                    ((Goomba)fixA.getUserData()).reverseVelocity(true,false);
                    ((Goomba)fixB.getUserData()).reverseVelocity(true,false);
                break;
            default:
                break;

        }


    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
