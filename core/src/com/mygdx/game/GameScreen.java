package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;


public class GameScreen implements Screen {
    private static final int FRAME_COLS = 3;
    private static final int FRAME_ROWS = 4;
    private static final float BALL_MOVEMENT = 200f;
    float moveX = 0;
    float moveY = 0;
    boolean directionY = true;
    boolean directionX = true;
    float randomAngle = 20;
    boolean pause = false;
    boolean gameOverState = false;
    boolean playing = false;
    boolean won = false;


    MyGdxGame game;
    Texture breakOut;
    TextureRegion[] gamePieces;
    Texture gameBall;

    Sprite playerSprite;
    Sprite ball;


    ArrayList<Sprite> bricks1 = new ArrayList<Sprite>();
    ArrayList<Sprite> bricks2 = new ArrayList<Sprite>();
    ArrayList<Sprite> bricks3 = new ArrayList<Sprite>();
    ArrayList<Sprite> bricks4 = new ArrayList<Sprite>();
    ArrayList<Sprite> destroyed = new ArrayList<Sprite>();
    ArrayList<Integer> bricks1Destroyed = new ArrayList<Integer>();
    ArrayList<Integer> bricks2Destroyed = new ArrayList<Integer>();
    ArrayList<Integer> bricks3Destroyed = new ArrayList<Integer>();
    ArrayList<Integer> bricks4Destroyed = new ArrayList<Integer>();


    TextButton retryButton;;
    TextButton exitButton;
    TextButton gameOver;
    TextButton paused;
    TextButton pauseButton;
    TextButton resumeButton;
    TextButton gameWin;
    BitmapFont mainFont;
    TextButton.TextButtonStyle textButtonStyle;

    Sound bing = Gdx.audio.newSound(Gdx.files.internal("bing.wav"));
    Sound song = Gdx.audio.newSound(Gdx.files.internal("loopSong.mp3"));
    Sound wall = Gdx.audio.newSound(Gdx.files.internal("wall.mp3"));
    Sound waterDrop = Gdx.audio.newSound(Gdx.files.internal("waterDrop.wav"));
    Sound win = Gdx.audio.newSound(Gdx.files.internal("win.wav"));
    Sound lose = Gdx.audio.newSound(Gdx.files.internal("lose.wav"));







    public GameScreen(MyGdxGame game){
        this.game = game;
        song.loop();


        //loss State
        mainFont = new BitmapFont(Gdx.files.internal("good_neighbors.fnt"),
                Gdx.files.internal("good_neighbors.png"), false);
        //sets button style with font
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = (mainFont);

        retryButton = new TextButton("Retry?", textButtonStyle);
        //sets btn location
        retryButton.setPosition(50f, Gdx.graphics.getHeight()/2 - 150f);
        //sets btn size
        retryButton.setTransform(true);
        retryButton.setScale(5f, 5f);



        exitButton = new TextButton("Exit", textButtonStyle);
        //sets btn location
        exitButton.setPosition((Gdx.graphics.getWidth() /2 + 100f), Gdx.graphics.getHeight()/2 - 150f);
        //sets btn size
        exitButton.setTransform(true);
        exitButton.setScale(5f, 5f);



        gameOver = new TextButton("GAMEOVER", textButtonStyle);
        //sets btn location
        gameOver.setPosition((Gdx.graphics.getWidth() /2 - 225f), Gdx.graphics.getHeight()/2 + 50f);
        //sets btn size
        gameOver.setTransform(true);

        gameOver.setScale(7f, 7f);
        //end of loss state


        //pause state
        paused = new TextButton("Paused", textButtonStyle);
        //sets btn location
        paused.setPosition((Gdx.graphics.getWidth() /2 - 150f), Gdx.graphics.getHeight()/2 );
        //sets btn size
        paused.setTransform(true);

        paused.setScale(7f, 7f);

        pauseButton = new TextButton("Pause", textButtonStyle);
        //sets btn location
        pauseButton.setPosition((10), 375);
        //sets btn size
        pauseButton.setTransform(true);

        pauseButton.setScale(1f, 1f);

        resumeButton = new TextButton("Resume", textButtonStyle);
        //sets btn location
        resumeButton.setPosition(50f, Gdx.graphics.getHeight()/2 - 150f);
        //sets btn size
        resumeButton.setTransform(true);
        resumeButton.setScale(5f, 5f);
        //end pause state

        //win state
        gameWin = new TextButton("YOU WIN", textButtonStyle);
        //sets btn location
        gameWin.setPosition((Gdx.graphics.getWidth() /2 - 225f), Gdx.graphics.getHeight()/2 + 50f);
        //sets btn size
        gameWin.setTransform(true);

        gameWin.setScale(7f, 7f);
        //end of win state






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
        //adds bricks to array
        for(int i = 0; i < 20; i++){
            bricks1.add(new Sprite(gamePieces[0]));
            bricks1.get(i).setSize(32,16);
        }
        for(int i = 0; i < 20; i++){
            bricks2.add(new Sprite(gamePieces[1]));
            bricks2.get(i).setSize(32,16);
        }
        for(int i = 0; i < 20; i++){
            bricks3.add(new Sprite(gamePieces[2]));
            bricks3.get(i).setSize(32,16);
        }
        for(int i = 0; i < 20; i++){
            bricks4.add(new Sprite(gamePieces[3]));
            bricks4.get(i).setSize(32,16);
        }


        //gets player sprite as its bigger than the others
        gamePieces[4] = new TextureRegion(breakOut, 40, 0, 80, 23);

        gameBall = new Texture((Gdx.files.internal("SoccerBall.png")));

        playerSprite = new Sprite(gamePieces[4]);
        ball = new Sprite(gameBall);


    }

    @Override
    public void show(){

    }

    public int randomDirection(){

        int random = (int)(Math.random() * 101);
        if(random > 50){
            directionX = true;
        } else{
            directionX = false;
        }
        //otherwise only small angles to the left
        int randomReturn = (int)(Math.random() * 101);
        return randomReturn;
    }



    public void update(){
        float dt = Gdx.graphics.getDeltaTime();

        //makes sure player stays in the window
        if(Gdx.input.getX() < Gdx.graphics.getWidth() - (playerSprite.getRegionWidth())){
            //sets player location to the x value
            playerSprite.setPosition(Gdx.input.getX(), 48);
        }


        //sets ball
        //ball location
        //checks if at top of screen
        if(moveY > Gdx.graphics.getHeight() - ball.getHeight()){
            directionY = false;
            randomDirection();
            randomAngle = randomDirection();
            wall.play();
        }
        //checks if at bottom of screen
        if(moveY < 0){
            gameOverState = true;
            lose.play();
        }

        if (directionY == true) {
            moveY = moveY + BALL_MOVEMENT *dt;
        } else{
            moveY = moveY - BALL_MOVEMENT *dt;
        }


        if(ball.getBoundingRectangle().overlaps(playerSprite.getBoundingRectangle())){
            directionY = true;
            randomDirection();
            randomAngle = randomDirection();
            bing.play();
        }


        //checks collision for bricks
        for(int i = 0; i < 20; i++){
            //checks if it has been hit before, if so don't check collision
            if(bricks1Destroyed.contains(i)){

            }else{
                if(ball.getBoundingRectangle().overlaps(bricks1.get(i).getBoundingRectangle())){
                    bing.play();
                    directionY = false;
                    destroyed.add(bricks1.get(i));
                    bricks1Destroyed.add(i);

                }
            }

        }

        for(int i = 0; i < 20; i++){
            if(bricks2Destroyed.contains(i)){

            }else{
                if(ball.getBoundingRectangle().overlaps(bricks2.get(i).getBoundingRectangle())){
                    bing.play();
                    directionY = false;
                    destroyed.add(bricks2.get(i));
                    bricks2Destroyed.add(i);
                }
            }
        }

        for(int i = 0; i < 20; i++){
            if(bricks3Destroyed.contains(i)){

            }else{
                if(ball.getBoundingRectangle().overlaps(bricks3.get(i).getBoundingRectangle())){
                    bing.play();
                    directionY = false;
                    destroyed.add(bricks3.get(i));
                    bricks3Destroyed.add(i);
                }
            }
        }

        for(int i = 0; i < 20; i++){
            if(bricks4Destroyed.contains(i)){

            }else{
                if(ball.getBoundingRectangle().overlaps(bricks4.get(i).getBoundingRectangle())){
                    bing.play();
                    directionY = false;
                    destroyed.add(bricks4.get(i));
                    bricks4Destroyed.add(i);
                }
            }
        }



        //checks if ball goes past window width/walls
        if(moveX > Gdx.graphics.getWidth() - ball.getWidth()){
            directionX = false;
            wall.play();
        }
        if (moveX < 0){
            directionX = true;
            wall.play();
        }

        if(directionX == true){
            moveX = moveX + randomAngle * dt;
        } else{
            moveX = moveX - randomAngle * dt;
        }
        ball.setPosition(moveX, moveY);
        //ends ball



        if(Gdx.input.getX() < 50 && Gdx.input.getY() < 100 && Gdx.input.getY() > 75){
            if(Gdx.input.isTouched()){
                waterDrop.play();
                pause = true;
            }
        }

    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();


        //sets player size
        playerSprite.setSize(128, 16);

        //sets ball size
        ball.setSize(16,16);


        //checks if paused, gameover, success or not started or pretty much anything that needs the
        // update stopped as none of them can be accessed at the same time
        //for quick...er testing of win state remove all but brick4Destroyed the lowest row
        //why did i make the x direction after collision random :(
        if(pause || gameOverState || playing == false
                || bricks4Destroyed.size() > 19 && bricks3Destroyed.size() >
                19 && bricks2Destroyed.size() > 19 && bricks1Destroyed.size() > 19 ){
            if(pause){
                float exitX = Gdx.graphics.getWidth() /2 + 100f;
                float playX = 50;

                paused.draw(game.batch,10);
                exitButton.draw(game.batch,10);
                resumeButton.draw(game.batch, 10);

                if(Gdx.input.getX() > exitX && Gdx.input.getX() < exitX + 130f
                        && Gdx.input.getY() > 275 && Gdx.input.getY() < 330){
                    if(Gdx.input.isTouched()){
                        Gdx.app.exit();
                        waterDrop.play();
                    }
                }
                if(Gdx.input.getX() > playX && Gdx.input.getX() < playX + 215f
                        && Gdx.input.getY() > 275 && Gdx.input.getY() < 330){
                    if(Gdx.input.isTouched()){
                        pause = false;
                        waterDrop.play();
                    }
                }
            }else if(gameOverState) {
                gameOver.draw(game.batch,10);
                retryButton.draw(game.batch,10);
                exitButton.draw(game.batch,10);

                float playX = 50;
                //y value was not corrosponding to actual location?

                float exitX = Gdx.graphics.getWidth() /2 + 100f;

                //gets button location
                if(Gdx.input.getX() > playX && Gdx.input.getX() < playX + 215f
                        && Gdx.input.getY() > 275 && Gdx.input.getY() < 330){
                    if(Gdx.input.isTouched()){
                        waterDrop.play();
                        //starts game again
                        game.setScreen(new GameScreen(game));
                    }
                }

                //gets button location
                if(Gdx.input.getX() > exitX && Gdx.input.getX() < exitX + 130f
                && Gdx.input.getY() > 275 && Gdx.input.getY() < 330){
                    if(Gdx.input.isTouched()){
                        waterDrop.play();
                        Gdx.app.exit();
                    }
                }

            } else if(playing == false){
                //if game not started
                moveX = Gdx.input.getX() + playerSprite.getWidth()/2;
                moveY = 75;
                //sets initial position
                ball.setPosition(moveX, moveY);
                //makes sure player stays in the window
                if(Gdx.input.getX() < Gdx.graphics.getWidth() - (playerSprite.getRegionWidth())){
                    //sets player location to the x value
                    playerSprite.setPosition(Gdx.input.getX(), 48);
                }
                //checks if screen is currently being touched
                if(Gdx.input.isTouched()){
                    //adds delay so it wont immediatly begin in desktop mode
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            playing = true;
                        }
                    }, (float)0.1);
                }
            } else{
                //mostly same as gameover
                if(won == false){
                    //stops it from constanly playing
                    win.play();
                    won = true;
                }


                gameWin.draw(game.batch,10);
                retryButton.draw(game.batch,10);
                exitButton.draw(game.batch,10);

                float playX = 50;
                //y value was not corrosponding to actual location?

                float exitX = Gdx.graphics.getWidth() /2 + 100f;

                //gets button location
                if(Gdx.input.getX() > playX && Gdx.input.getX() < playX + 215f
                        && Gdx.input.getY() > 275 && Gdx.input.getY() < 330){
                    if(Gdx.input.isTouched()){
                        waterDrop.play();
                        game.setScreen(new GameScreen(game));

                    }
                }

                //gets button location
                if(Gdx.input.getX() > exitX && Gdx.input.getX() < exitX + 130f
                        && Gdx.input.getY() > 275 && Gdx.input.getY() < 330){
                    if(Gdx.input.isTouched()){
                        waterDrop.play();
                        Gdx.app.exit();
                    }
                }
            }

        } else{
            update();
        }


        int brickXLocation1 = 0;
        for(int i = 0; i < 20; i++ ){
            //checks if brick has been hit before
            if(destroyed.contains(bricks1.get(i))){
                //updates location for other bricks but does not draw
                brickXLocation1 += 32;
            } else{
                //draws like normal
                bricks1.get(i).setPosition(brickXLocation1, 464);
                bricks1.get(i).draw(game.batch);
                brickXLocation1 += 32;
            }

        }

        int brickXLocation2 = 0;
        for(int i = 0; i < 20; i++ ){
            if(destroyed.contains(bricks2.get(i))){
                brickXLocation2 += 32;
            }else{
                bricks2.get(i).setPosition(brickXLocation2, 448);
                bricks2.get(i).draw(game.batch);
                brickXLocation2 += 32;
            }

        }

        int brickXLocation3 = 0;
        for(int i = 0; i < 20; i++ ){
            if(destroyed.contains(bricks3.get(i))){
                brickXLocation3 += 32;
            }else{
                bricks3.get(i).setPosition(brickXLocation3, 432);
                bricks3.get(i).draw(game.batch);
                brickXLocation3 += 32;
            }

        }

        int brickXLocation4 = 0;
        for(int i = 0; i < 20; i++ ){
            if(destroyed.contains(bricks4.get(i))){
                brickXLocation4 += 32;
            }else{
                bricks4.get(i).setPosition(brickXLocation4, 416);
                bricks4.get(i).draw(game.batch);
                brickXLocation4 += 32;
            }

        }

        pauseButton.draw(game.batch, 10);
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
