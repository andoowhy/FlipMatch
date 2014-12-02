package com.andoowhy.flipmatch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class ControlsScreen implements Screen
{
    final FlipMatch game;

    private OrthographicCamera camera;

    private FlipCard[] flipCards = new FlipCard[16];
    private int flippedCount = 0;

    public ControlsScreen( final FlipMatch game )
    {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho( false, game.screenWidth, game.screenHeight );

        //Set up Cards
        Color randomColor = HSL.toRGB( MathUtils.random( 0f, 1f )
                                        ,game.cardSaturation
                                        ,game.cardLightness
                                        ,1f
        );
        for( int i = 0; i < 4; i++ )
        {
            for( int j = 0; j < 4; j++ )
            {
                flipCards[4 * i + j] = new FlipCard(
                        game
                        ,i * FlipCard.width + i * FlipCard.margin + FlipCard.xOffset
                        ,j * FlipCard.height + j* FlipCard.margin + FlipCard.yOffset
                        ,randomColor
                );
            }
        }
    }
    @Override
    public void render( float delta )
    {
        //
        // Update
        //
        if ( Gdx.input.justTouched() )
        {
            //Get touch coords and convert them to game space
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            if( flippedCount < 2 )
            {
                //Check if any card has been touched
                for( FlipCard flipCard : flipCards )
                {
                    if( flipCard.isTouched( touchPos.x, touchPos.y ) && !flipCard.flipped )
                    {
                        flipCard.flipped = true;
                        flippedCount ++;
                    }
                }
            }
            else
            {
                game.setScreen( game.controlsScreen2 );
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
            if( flippedCount < 2 )
            {
                game.drawMultiLineFontFromCenterRelative(
                        game.batch
                        , game.fontReg32
                        , "Tap on two cards\nto flip them"
                        , 0.5f
                        , 0.2f
                        , BitmapFont.HAlignment.CENTER
                );
            }
            else
            {
                game.drawMultiLineFontFromCenterRelative(
                        game.batch
                        ,game.fontReg32
                        ,"When the cards match\nthey stay flipped!"
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


            //Time

            //Buttons
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
        flippedCount = 0;
        for( FlipCard flipCard : flipCards )
        {
            flipCard.flipped = false;
        }
    }
}
