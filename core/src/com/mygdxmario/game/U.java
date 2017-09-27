package com.mygdxmario.game;

import com.badlogic.gdx.Gdx;

/**
 * Created by Jeevs on 23-10-2015.
 */
public class U {

    public static void log(String s){
        Gdx.app.log("Jeeva",s);
    }

    public static void log(int s){
        Gdx.app.log("Jeeva",String.valueOf(s));
    }

    public static void log(float s){
        Gdx.app.log("Jeeva",String.valueOf(s));
    }

    public static void log(long s){
        Gdx.app.log("Jeeva",String.valueOf(s));
    }

    public static void log(double s){
        Gdx.app.log("Jeeva",String.valueOf(s));
    }
}
