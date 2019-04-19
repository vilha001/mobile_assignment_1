package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;


public class GameScreen implements Screen {
    private static final int FRAME_COLS = 3;
    private static final int FRAME_ROWS = 4;
    private static final float BALL_MOVEMENT = 200f;
    float moveX = 0;
    float moveY = 0;


    MyGdxGame game;
    Texture breakOut;
    TextureRegion[] gamePieces;
    Stage stage;
    Texture gameBall;

    Sprite playerSprite;
    Sprite ball;
    Sprite[] bricks;





    public GameScreen(MyGdxGame game){
        this.game = game;
        stage = new Stage();

        breakOut = new Texture(Gdx.files.internal("breakout_pieces.png"));

        TextureRegion[][] temp = TextureRegion.split(breakOut,
                breakOut.getWidth() / FRAME_COLS,
                breakOut.getHeight() / FRAME_ROWS);


        gamePieces = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for(int i = 0; i < FRAME_ROWS; i++){
            for(int j = 0; j < FRAME_COLS; j++){
                //checks if they are bricks
                if(i == 0 && j > 0 ||i == 1 && j > 0 || i == 2 && j > 0 || i == 3 && j >0 ){

                } else{
                    gamePieces[index++] = temp [i] [j];
                }

            }
        }
        //gets player sprite as its bigger than the others
        gamePieces[4] = new TextureRegion(breakOut, 40, 0, 120, 23);

        gameBall = new Texture((Gdx.files.internal("SoccerBall.png")));

        playerSprite = new Sprite(gamePieces[4]);
        ball = new Sprite(gameBall);

        //adds bricks to sprite array, make easier to work with
        bricks = new Sprite[]{new Sprite(gamePieces[0]),
                new Sprite(gamePieces[1]),
                new Sprite(gamePieces[2]),
                new Sprite(gamePieces[3])};







    }

    @Override
    public void show(){

    }

    @Override
    public void render(float delta){
        float dt = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        //sets bricks position
        int posY = 0;
        for(int y = 0; y < 4; y++){
            int pos = 0;
            for(int x = 0; x < 20; x++){
                bricks[y].setPosition(pos, 464 - posY);
                //sets to 32 so it will cover screen of x resolution 640
                bricks[y].setSize(32,16);
                pos += bricks[y].getWidth();
                bricks[y].draw(game.batch);
            }
            //drops the y height down after one loop is done
            posY += bricks[0].getRegionHeight();
        }



        //sets player
        playerSprite.setSize(128, 16);
        //makes sure player stays in the window
        if(Gdx.input.getX() < Gdx.graphics.getWidth() - (playerSprite.getRegionWidth()/2)){
            //sets player location to the x value
            playerSprite.setPosition(Gdx.input.getX(), 48);
        }


        ball.setSize(16,16);
        //ball location
        moveY = moveY + BALL_MOVEMENT *dt;
        moveX = 100;
        if(ball.getBoundingRectangle().overlaps(bricks[0].getBoundingRectangle()) ){
            System.out.println("i got here");

        }

        ball.setPosition(moveX, moveY);


        playerSprite.draw(game.batch);
        ball.draw(game.batch);


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
