package com.andoowhy.flipmatch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class ControlsScreen2 implements Screen
{
    final FlipMatch game;

    private OrthographicCamera camera;

    private FlipCard[] flipCards = new FlipCard[16];
    private int flippedCount = 0;

    public ControlsScreen2( final FlipMatch game )
    {
        this.game = game;
        /*
        camera = new OrthographicCamera();
        camera.setToOrtho( false, game.screenWidth, game.screenHeight );

        //Set Up Random Card Colors
        Color[] colors = new Color[16];
        for( int i = 0; i < colors.length; i += 2 )
        {//Initialize colors
            Color randomColor = HSL.toRGB( MathUtils.random( 0f, 1f ), 0f, 0.15f , 1f );
            colors[i] = randomColor;
            colors[i+1] = randomColor;
        }
        for (int i = 0; i < colors.length; i++)
        {//Fisher–Yates Shuffle colors
            int r = i + MathUtils.random(0, colors.length - i );
            Color tmp = colors[i];
            colors[i] = colors[r];
            colors[r] = tmp;
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
        */
    }
    @Override
    public void render( float delta )
    {
        /*

        //
        // Update
        //
        if ( Gdx.input.isTouched() )
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
                    flippedCount ++;
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
                        , game.fontReg32
                        , "When the cards match\nthey stay flipped!"
                        , 0.5f
                        , 0.2f
                        , BitmapFont.HAlignment.CENTER
                );
            }


            //Time

            //Buttons
        }
        game.batch.end();

        */
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
