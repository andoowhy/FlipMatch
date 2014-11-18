package com.andoowhy.flipmatch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class ControlsScreen implements Screen
{
    final FlipMatch game;

    private OrthographicCamera camera;

    private FlipCard[] flipCards = new FlipCard[16];

    public ControlsScreen( final FlipMatch game )
    {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho( false, game.screenWidth, game.screenHeight );

        //Set up Cards
        for( int i = 0; i < 4; i++ )
        {
            for( int j = 0; j < 4; j++ )
            {
                flipCards[4 * i + j] = new FlipCard(
                        game
                        ,i * FlipCard.width + FlipCard.margin + FlipCard.xOffset
                        ,j * FlipCard.height + FlipCard.margin + FlipCard.yOffset
                );
            }
        }
    }
    @Override
    public void render( float delta )
    {
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
                    , game.fontReg32
                    , "Tap on two cards\nto flip them"
                    , 0.5f
                    , 0.2f
                    , BitmapFont.HAlignment.CENTER
            );

            //Time

            //Buttons
        }
        game.batch.end();

//        if ( Gdx.input.isTouched() )
//        {
//            game.setScreen( game.startScreen );
//            dispose();
//        }
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
