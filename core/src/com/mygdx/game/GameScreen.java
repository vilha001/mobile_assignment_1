package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;


public class GameScreen implements Screen {
    private static final int FRAME_COLS = 3;
    private static final int FRAME_ROWS = 4;
    private static final float BALL_MOVEMENT = 200f;
    float moveX = 0;
    float moveY = 0;
    boolean directionY = true;
    boolean directionX = true;


    MyGdxGame game;
    Texture breakOut;
    TextureRegion[] gamePieces;
    Stage stage;
    Texture gameBall;

    Sprite playerSprite;
    Sprite ball;
    Sprite brick1;
    Sprite brick2;
    Sprite brick3;
    Sprite brick4;







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
        gamePieces[4] = new TextureRegion(breakOut, 40, 0, 80, 23);

        gameBall = new Texture((Gdx.files.internal("SoccerBall.png")));

        playerSprite = new Sprite(gamePieces[4]);
        ball = new Sprite(gameBall);

        //adds bricks to sprite array, make easier to work with
        brick1 = new Sprite(gamePieces[0]);
        brick2 = new Sprite(gamePieces[1]);
        brick3 = new Sprite(gamePieces[2]);
        brick4 = new Sprite(gamePieces[3]);








    }

    @Override
    public void show(){

    }

    public int randomDirection(){

        int random = (int)(Math.random() * 101);
        System.out.println(random);
        if(random > 50){
            directionX = true;
        } else{
            directionX = false;
        }
        return random;
    }

    @Override
    public void render(float delta){
        float dt = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        //sets bricks position
        brick1.setPosition(100, 440);
        brick1.draw(game.batch);




        //sets player
        playerSprite.setSize(128, 16);
        //makes sure player stays in the window
        if(Gdx.input.getX() < Gdx.graphics.getWidth() - (playerSprite.getRegionWidth())){
            //sets player location to the x value
            playerSprite.setPosition(Gdx.input.getX(), 48);
        }


        ball.setSize(16,16);
        //ball location
        if(moveY > Gdx.graphics.getHeight() - ball.getHeight()){
            directionY = false;
            randomDirection();
        }
        if(moveY < 0){
            directionY = true;
            randomDirection();

        }
        if (directionY == true) {
            moveY = moveY + BALL_MOVEMENT *dt;
        } else{
            moveY = moveY - BALL_MOVEMENT *dt;
        }
        if(ball.getBoundingRectangle().overlaps(brick1.getBoundingRectangle()) ){
            System.out.println("i got here");

        }

        if(ball.getBoundingRectangle().overlaps(playerSprite.getBoundingRectangle())){
            directionY = true;
            randomDirection();
        }

        if(directionX == true){
            moveX = moveX + 50 * dt;
        } else{
            moveX = moveX - 50 * dt;
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
