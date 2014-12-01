package com.andoowhy.flipmatch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;

public class GameScreen implements Screen
{
    final FlipMatch game;

    private OrthographicCamera camera;

    private FlipCard[] flipCards = new FlipCard[16];

    //Flipped Timer
    private Timer flippedTimer;
    private Timer.Task flippedTimerTask;
    private ArrayList<FlipCard> lastFlipped = new ArrayList< FlipCard >();

    //Buttons
    private Button questionMarkButton;
    private Button pauseButton;
    private Button resumeButton;
    private Button restartButton;
    private Button highScoresButton;

    //Labels
    private Label timeLabel;
    private Label elapsedLabel;
    private float elapsedTime;
    private Label pausedLabel;
    private Label youWonLabel;

    //Textures
    private Texture pauseMenuBG;

    private boolean paused = false;

    public GameScreen( final FlipMatch game )
    {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho( false, game.screenWidth, game.screenHeight );

        //Set Up Random Card Colors
        Color[] colors = new Color[16];

        for( int i = 0; i < colors.length; i+= 2 )
        {//Initialize colors
            Color color = HSL.toRGB( (float)i / colors.length
                ,game.cardSaturation
                ,game.cardLightness
                ,1f
            );

            colors[i] = color;
            colors[i+1] = color;

        }

        for ( int i = 0; i < colors.length; i++ )
        {//Fisher–Yates Shuffle colors
            int r = i + (int)( Math.random() * ( colors.length - i ) );
            Color tmp = colors[r];
            colors[r] = colors[i];
            colors[i] = tmp;
        }

        //Set up Cards
        for( int i = 0; i < 4; i++ )
        {
            for( int j = 0; j < 4; j++ )
            {
                flipCards[4 * i + j] = new FlipCard(
                        game
                        ,i * FlipCard.width + i * FlipCard.margin + FlipCard.xOffset
                        ,j * FlipCard.height + j* FlipCard.margin + FlipCard.yOffset
                        ,colors[4 * i + j]
                );
            }
        }

        //Set up Timer task
        flippedTimerTask = new Timer.Task()
        {
            @Override
            public void run()
            {
                //Hide the last two cards
                for( int i = lastFlipped.size() - 2; i < lastFlipped.size(); i++ )
                {
                    lastFlipped.get( i ).flipped = false;
                }
                lastFlipped.remove( lastFlipped.size() - 1 );
                lastFlipped.remove( lastFlipped.size() - 1 );
            }
        };

        //Set up Buttons
        questionMarkButton = new Button(
                game
                ,game.fontReg32
                ,"?"
                ,350
                ,100
        );
        pauseButton = new Button(
                game
                ,game.fontReg32
                ,"ll"
                ,360f + questionMarkButton.width
                ,100
        );


        //Set up Labels
        timeLabel = new Label(
                game
                ,game.fontReg32
                ,"Time: "
                ,100f
                ,100f + pauseButton.margin + pauseButton.border
        );
        elapsedLabel = new Label(
                game
                ,game.fontReg32
                ,"0:00"
                ,100f + timeLabel.getWidth()
                ,100f + pauseButton.margin + pauseButton.border
        );

        //
        // Pause Menu
        //
        {
            Pixmap pixmap = new Pixmap( MathUtils.ceil( game.screenWidth ), MathUtils.ceil( game.screenHeight ), Pixmap.Format.RGB888 );
            pixmap.setColor( new Color( 1f, 1f, 1f, 1f ) );
            pixmap.fillRectangle( 0, 0, pixmap.getWidth(), pixmap.getHeight() );
            pauseMenuBG = new Texture( pixmap );
        }
        {
            String resumeText = "Resume";
            BitmapFont.TextBounds bounds = game.fontReg32.getBounds( resumeText );
            resumeButton = new Button(
                    game
                    ,game.fontReg32
                    ,resumeText
                    ,game.screenWidth * 0.5f - bounds.width / 2f
                    ,game.screenHeight * 0.5f - bounds.height / 2f
            );
        }
        {
            String restartText = "Restart";
            BitmapFont.TextBounds bounds = game.fontReg32.getBounds( restartText );
            restartButton = new Button(
                    game
                    ,game.fontReg32
                    ,restartText
                    ,game.screenWidth * 0.5f - bounds.width / 2f
                    ,game.screenHeight * 0.4f - bounds.height / 2f
            );
        }
        {
            String highScoresText = "High Scores";
            BitmapFont.TextBounds bounds = game.fontReg32.getBounds( highScoresText );
            highScoresButton = new Button(
                    game
                    ,game.fontReg32
                    ,highScoresText
                    ,game.screenWidth * 0.5f - bounds.width / 2f
                    ,game.screenHeight * 0.3f - bounds.height / 2f
            );
        }
        {
            String pausedText = "Paused";
            BitmapFont.TextBounds bounds = game.fontBlk100.getBounds( pausedText );
            pausedLabel = new Label(
                    game
                    ,game.fontBlk100
                    ,pausedText
                    ,game.screenWidth * 0.5f - bounds.width / 2f
                    ,game.screenHeight * 0.7f - bounds.height / 2f
            );
        }

        //
        // You Won Menu
        //
        {
            String youWonText = "You Won!";
            BitmapFont.TextBounds bounds = game.fontBlk100.getBounds( youWonText );
            youWonLabel = new Label(
                    game
                    ,game.fontBlk100
                    ,youWonText
                    ,game.screenWidth * 0.5f - bounds.width / 2f
                    ,game.screenHeight * 0.7f - bounds.height / 2f
            );
        }

    }
    @Override
    public void render( float delta )
    {
        //
        // Update
        //
        if ( !paused )
        {
            elapsedTime += delta;
        }

        if( !flippedTimerTask.isScheduled() && Gdx.input.justTouched() )
        {
            //Get touch coords and convert them to game space
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            if( lastFlipped.size() != flipCards.length )
            {
                if( !paused )
                {   //Check if any card has been touched
                    for( FlipCard flipCard : flipCards )
                    {
                        if( flipCard.isTouched( touchPos.x, touchPos.y ) && !flipCard.flipped )
                        {
                            flipCard.flipped = true;
                            lastFlipped.add( flipCard );

                            //Check if an even number of cards has been flipped
                            if( lastFlipped.size() > 0 && lastFlipped.size() % 2 == 0 )
                            {
                                //Check if the last two flipped cards are not the same color
                                int end = lastFlipped.size() - 1;
                                if( lastFlipped.get( end ).flippedColor != lastFlipped.get( end - 1 ).flippedColor )
                                {
                                    //remove the last two cards
                                    Timer.schedule( flippedTimerTask, 0.8f );
                                }
                            }

                            break;
                        }
                    }

                    //Check if any button has been touched
                    if( questionMarkButton.isTouched( touchPos.x, touchPos.y ) )
                    {
                        game.setScreen( game.controlsScreen );
                    }

                    if( pauseButton.isTouched( touchPos.x, touchPos.y ) )
                    {
                        paused = true;
                    }
                }
                else
                {//Pause Menu
                    if( resumeButton.isTouched( touchPos.x, touchPos.y ) )
                    {
                        paused = false;
                    }

                    if( highScoresButton.isTouched( touchPos.x, touchPos.y ) )
                    {
                        game.setScreen( game.highScoreScreen );
                    }

                    if( restartButton.isTouched( touchPos.x, touchPos.y ) )
                    {
                        dispose();
                    }
                }
            }
            else if( lastFlipped.size() == flipCards.length )
            {
                if( restartButton.isTouched( touchPos.x, touchPos.y ) )
                {
                    dispose();
                }
            }
        }

        //
        // Drawing
        //
        Gdx.gl.glClearColor( 0.8f, 0.8f, 0.8f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

        camera.update();
        game.batch.setProjectionMatrix( camera.combined );

        game.batch.begin();
        {
            //Cards
            for( FlipCard flipCard : flipCards )
            {
                flipCard.draw( game.batch );
            }

            //Time
            timeLabel.draw( game.batch );
            String elapsedLabeled = Integer.toString( (int)( elapsedTime / 60f ) );
            elapsedLabeled += ":";
            elapsedLabeled += String.format( "%02d", (int)( elapsedTime % 60 ));
            elapsedLabel.setText( elapsedLabeled );
            elapsedLabel.draw( game.batch );

            //Buttons
            questionMarkButton.draw( game.batch );
            pauseButton.draw( game.batch );

            if( lastFlipped.size() == flipCards.length )
            {
                {
                    Color oldColor = game.batch.getColor();
                    game.batch.setColor( oldColor.r, oldColor.g, oldColor.b, 0.9f );
                    game.batch.draw( pauseMenuBG, 0f, 0f );
                    game.batch.setColor( oldColor );
                }
                youWonLabel.draw( game.batch );
                restartButton.draw( game.batch );
            }
            else if( paused )
            {
                {
                    Color oldColor = game.batch.getColor();
                    game.batch.setColor( oldColor.r, oldColor.g, oldColor.b, 0.9f );
                    game.batch.draw( pauseMenuBG, 0f, 0f );
                    game.batch.setColor( oldColor );
                }
                resumeButton.draw( game.batch );
                restartButton.draw( game.batch );
                highScoresButton.draw( game.batch );
                pausedLabel.draw( game.batch );
            }
        }
        game.batch.end();
    }

    @Override
    public void resize( int width, int height )
    {

    }

    @Override
    public void show()
    {

    }

    @Override
    public void hide()
    {

    }

    @Override
    public void pause()
    {
        paused = true;
    }

    @Override
    public void resume()
    {

    }

    @Override
    public void dispose()
    {
        elapsedTime = 0f;
        for( FlipCard flipCard : flipCards )
        {
            flipCard.flipped = false;
        }
        lastFlipped.clear();
        paused = false;
    }
}