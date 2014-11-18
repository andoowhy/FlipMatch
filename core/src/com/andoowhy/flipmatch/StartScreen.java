package com.andoowhy.flipmatch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class StartScreen implements Screen
{
    final FlipMatch game;

    private OrthographicCamera camera;

    public StartScreen( final FlipMatch game )
    {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho( false, game.screenWidth, game.screenHeight );
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
            game.drawFontFromCenterRelative( game.batch
                                            ,game.fontBlk100
                                            ,"FlipMatch"
                                            ,0.5f
                                            ,0.5f );

            game.drawFontFromCenterRelative( game.batch
                                            ,game.fontReg32
                                            ,"Tap Anywhere to Start"
                                            ,0.5f
                                            ,0.2f );


        }
        game.batch.end();

        if ( Gdx.input.isTouched() )
        {
            game.setScreen( game.controlsScreen );
            dispose();
        }
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
