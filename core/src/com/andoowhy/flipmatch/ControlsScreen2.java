package com.andoowhy.flipmatch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class ControlsScreen2 implements Screen
{
    final FlipMatch game;

    private OrthographicCamera camera;

    private FlipCard[] flipCards = new FlipCard[16];

    private Timer flippedTimer;
    private Timer.Task flippedTimerTask;
    private ArrayList<FlipCard> lastFlipped = new ArrayList< FlipCard >();

    public ControlsScreen2( final FlipMatch game )
    {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho( false, game.screenWidth, game.screenHeight );

        //Set Up Random Card Colors
        Color[] colors = new Color[16];
        for( int i = 0; i < colors.length; i++ )
        {//Initialize colors
            Color randomColor = HSL.toRGB( MathUtils.random( 0f, 16f ) / 16f
                                            ,game.cardSaturation
                                            ,game.cardLightness
                                            ,1f
            );

            colors[i] = randomColor;
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

        flippedTimerTask = new Timer.Task()
        {
            @Override
            public void run()
            {
                for( FlipCard flipCard : lastFlipped )
                {
                    flipCard.flipped = false;
                }
            }
        };
    }
    @Override
    public void render( float delta )
    {
        //
        // Update
        //

        if( !flippedTimerTask.isScheduled() && Gdx.input.justTouched() )
        {
            //Get touch coords and convert them to game space
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            if ( lastFlipped.size() < 2 )
            {
                //Check if any card has been touched
                for( FlipCard flipCard : flipCards )
                {
                    if( flipCard.isTouched( touchPos.x, touchPos.y ) && !flipCard.flipped )
                    {
                        flipCard.flipped = true;
                        lastFlipped.add( flipCard );
                        if( lastFlipped.size() > 0 && lastFlipped.size() % 2 == 0 )
                        {
                            Timer.schedule( flippedTimerTask, 1.5f );
                        }
                        break;
                    }
                }
            }
            else if ( lastFlipped.size() >= 2 )
            {
                game.setScreen( game.controlsScreen3 );
                dispose();
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

            //Text
            if ( lastFlipped.size() < 2 )
            {
                game.drawMultiLineFontFromCenterRelative(
                        game.batch
                        , game.fontReg32
                        , "When two flipped cards\ndon't match..."
                        , 0.5f
                        , 0.2f
                        , BitmapFont.HAlignment.CENTER
                );
            }
            else if ( lastFlipped.size() >= 2 )
            {
                game.drawMultiLineFontFromCenterRelative(
                        game.batch
                        ,game.fontReg32
                        ,"They flip back!"
                        ,0.5f
                        ,0.2f
                        ,BitmapFont.HAlignment.CENTER
                );

                game.drawMultiLineFontFromCenterRelative(
                        game.batch
                        ,game.fontReg14
                        ,"Tap anywhere to continue"
                        ,0.5f
                        ,0.05f
                        ,BitmapFont.HAlignment.CENTER
                );
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

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void dispose()
    {
        for( FlipCard flipCard : flipCards )
        {
            flipCard.flipped = false;
        }
        lastFlipped.clear();
    }
}
