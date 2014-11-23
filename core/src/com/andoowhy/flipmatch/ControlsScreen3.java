package com.andoowhy.flipmatch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;

public class ControlsScreen3 implements Screen
{
    final FlipMatch game;

    private OrthographicCamera camera;

    private FlipCard[] flipCards = new FlipCard[16];
    private int flippedCount = 0;

    public ControlsScreen3( final FlipMatch game )
    {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho( false, game.screenWidth, game.screenHeight );

        //Set Up Random Card Colors
        Color[] colors = new Color[16];
        for( int i = 0; i < colors.length; i+= 2 )
        {//Initialize colors
            Color randomColor = HSL.toRGB( MathUtils.random( 0f, 16f ) / 16f
                    ,game.cardSaturation
                    ,game.cardLightness
                    ,1f
            );

            colors[i] = randomColor;
            colors[i+1] = randomColor;

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
                flipCards[4 * i + j].flipped = true;
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
            game.setScreen( game.gameScreen );
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
            game.drawMultiLineFontFromCenterRelative(
                    game.batch
                    ,game.fontReg32
                    ,"Flip all matching cards\nto win the game!"
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

    }
}