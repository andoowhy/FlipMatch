package com.andoowhy.flipmatch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;

public class GameScreen implements Screen
{
    final FlipMatch game;

    private OrthographicCamera camera;

    private FlipCard[] flipCards = new FlipCard[16];

    private Timer flippedTimer;
    private Timer.Task flippedTimerTask;
    private ArrayList<FlipCard> lastFlipped = new ArrayList< FlipCard >();

    private Button questionMarkButton;
    private Button pauseButton;

    private Label timeLabel;
    private Label ellapsedLabel;

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
            }
        };

        //Set up Buttons
        questionMarkButton = new Button( game, game.fontReg32, "?", 350, 100 );
        pauseButton = new Button( game, game.fontReg32, "ll", 360f + questionMarkButton.width, 100 );

        //Set up Labels
        timeLabel = new Label( game, game.fontReg32, "Time: ", 100f, 100f );
        ellapsedLabel = new Label( game, game.fontReg32, "0:00", 100f + timeLabel.getWidth(), 100f );

    }
    @Override
    public void render( float delta )
    {
        //
        // Update
        //
        if( flippedTimerTask.isScheduled() ) return;

        if ( Gdx.input.justTouched() )
        {
            //Get touch coords and convert them to game space
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            //Check if any card has been touched
            for( FlipCard flipCard : flipCards )
            {
                if( flipCard.isTouched( touchPos.x, touchPos.y ) && !flipCard.flipped )
                {
                    flipCard.flipped = true;
                    lastFlipped.add( flipCard );

                    //Check if an even number of cards has been flipped
                    if( lastFlipped.size() % 2 == 0 )
                    {
                        //Check if the last two flipped cards are not the same color
                        int end = lastFlipped.size() - 1;
                        if( lastFlipped.get( end ).flippedColor != lastFlipped.get( end - 1 ).flippedColor )
                        {
                            Timer.schedule( flippedTimerTask, 0.8f );
                        }
                    }

                    break;
                }
            }

            //Check if any button has been touched
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
            ellapsedLabel.draw( game.batch );

            //Buttons
            questionMarkButton.draw( game.batch );
            pauseButton.draw( game.batch );
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

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void dispose()
    {

    }
}