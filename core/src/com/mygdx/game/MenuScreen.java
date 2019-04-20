package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MenuScreen implements Screen {

    MyGdxGame game;

    TextButton playButton;;
    TextButton exitButton;
    TextButton title;
    BitmapFont mainFont;
    TextButton.TextButtonStyle textButtonStyle;
    Stage stage;
    Sound waterDrop = Gdx.audio.newSound(Gdx.files.internal("waterDrop.wav"));


    public MenuScreen(MyGdxGame game){
        this.game = game;
        stage = new Stage();

        //apply font to mainFont
        mainFont = new BitmapFont(Gdx.files.internal("good_neighbors.fnt"),
                Gdx.files.internal("good_neighbors.png"), false);
        //sets button style with font
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = (mainFont);


        playButton = new TextButton("Play", textButtonStyle);
        //sets btn location
        playButton.setPosition((Gdx.graphics.getWidth() /2 - 100f), Gdx.graphics.getHeight()/2 - 50f);
        //sets btn size
        playButton.setTransform(true);
        playButton.setScale(5f, 5f);



        exitButton = new TextButton("Exit", textButtonStyle);
        //sets btn location
        exitButton.setPosition((Gdx.graphics.getWidth() /2 - 0f), Gdx.graphics.getHeight()/2 - 150f);
        //sets btn size
        exitButton.setTransform(true);
        exitButton.setScale(5f, 5f);



        title = new TextButton("Breakout?", textButtonStyle);
        //sets btn location
        title.setPosition((Gdx.graphics.getWidth() /2 - 225f), Gdx.graphics.getHeight()/2 + 50f);
        //sets btn size
        title.setTransform(true);

        title.setScale(7f, 7f);

        //adds buttons to stage
        stage.addActor(playButton);
        stage.addActor(exitButton);
        stage.addActor(title);


    }


    @Override
    public void show(){

    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float playX = Gdx.graphics.getWidth() /2 - 100f;
        float playY = Gdx.graphics.getHeight()/2 - 50f;

        float exitX = Gdx.graphics.getWidth() /2 - 0f;
        float exitY = Gdx.graphics.getHeight()/2 + 50f;

        game.batch.begin();


        //button width is 125f and height is 40f
        //gets button locations and runs when touched/clicked
        if(Gdx.input.getX() > playX && Gdx.input.getX() < playX + 125f
                && Gdx.input.getY() > playY && Gdx.input.getY() < playY + 40f ){
            if(Gdx.input.isTouched()){
                waterDrop.play();
                game.setScreen(new GameScreen(game));

            }
        }

        if(Gdx.input.getX() > exitX && Gdx.input.getX() < exitX + 125f
                && Gdx.input.getY() > exitY && Gdx.input.getY() < exitY + 40f ){
            if(Gdx.input.isTouched()){
                waterDrop.play();
                Gdx.app.exit();

            }
        }

        stage.draw();


        game.batch.end();

    }
    @Override
    public void resize(int width, int height){

    }
    @Override
    public void pause(){

    }
    @Override
    public void resume(){

    }
    @Override
    public void hide(){

    }
    @Override
    public void dispose(){

    }
}
