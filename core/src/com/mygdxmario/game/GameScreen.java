package com.mygdxmario.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdxmario.game.Tools.B2WorldCreator;
import com.mygdxmario.game.Tools.WorldContactListener;
import com.mygdxmario.game.scenes.Hud;
import com.mygdxmario.game.sprites.Goomba;
import com.mygdxmario.game.sprites.Mario;


/**
 * Created by Jeevs on 23-10-2015.
 */
public class GameScreen implements Screen {

    public SpriteBatch batch;
    private Hud hud;
    private MarioBros game;
    private B2WorldCreator creator;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Mario player;
    private Array<Goomba> goombas;
    private TextureAtlas atlas;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer debugRenderer;


    public GameScreen(MarioBros game) {

        atlas = new TextureAtlas("Mario_and_Enemies.pack");

        this.game = game;
        batch = this.game.batch;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(MarioBros.V_WIDTH/MarioBros.PPM,MarioBros.V_HEIGHT/MarioBros.PPM,gameCam);
        gameCam.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()/2,0);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map,1/MarioBros.PPM);

        world = new World(new Vector2(0,-10),true);
        world.setContactListener(new WorldContactListener());
        debugRenderer = new Box2DDebugRenderer();

        creator = new B2WorldCreator(world,this,map);
        hud = new Hud(batch);

        player = new Mario(world,this);
        goombas = creator.getGoombas();



    }



    private void handleInput(float deltaTime) {

        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            player.body.applyLinearImpulse(new Vector2(0,4),player.body.getWorldCenter(),true);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) && player.body.getLinearVelocity().x <= 2){

            player.body.applyLinearImpulse(new Vector2(0.2f,0),player.body.getWorldCenter(),true);
        }else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.body.getLinearVelocity().x <= 1){

            player.body.applyLinearImpulse(new Vector2(0.1f,0),player.body.getWorldCenter(),true);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) && player.body.getLinearVelocity().x >= -2){

            player.body.applyLinearImpulse(new Vector2(-0.2f,0),player.body.getWorldCenter(),true);
        }else if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.body.getLinearVelocity().x >= -1){

            player.body.applyLinearImpulse(new Vector2(-0.1f,0),player.body.getWorldCenter(),true);
        }
    }


    public void update(float deltaTime){

        handleInput(deltaTime);

        world.step(1 / 60f, 6, 2);
        player.update(deltaTime);

        for(Goomba g:goombas)
            g.update(deltaTime);

        gameCam.position.x = player.body.getPosition().x;
        gameCam.update();
        renderer.setView(gameCam);

    }

    @Override
    public void render(float delta) {

        update(delta);
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();
        //debugRenderer.render(world,gameCam.combined);

        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        player.draw(batch);
        for(Goomba g:goombas)
            g.draw(batch);
        game.batch.end();

        //batch.setProjectionMatrix(hud.stage.getCamera().combined);
        //hud.stage.draw();
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);
    }

    @Override
    public void show() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

        map.dispose();
        renderer.dispose();
        world.dispose();
        debugRenderer.dispose();
        hud.dispose();
    }
}
