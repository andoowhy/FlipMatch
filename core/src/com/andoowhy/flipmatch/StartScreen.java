package com.andoowhy.flipmatch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;

public class StartScreen implements Screen
{
    final FlipMatch game;

    private OrthographicCamera camera;

    private Button tutorialButton;
    private Button startButton;

    public StartScreen( final FlipMatch game )
    {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho( false, game.screenWidth, game.screenHeight );

        {
            String tutorialText = "Tutorial";
            BitmapFont.TextBounds bounds = game.fontReg32.getBounds( tutorialText );
            tutorialButton = new Button(
                    game
                    ,game.fontReg32
                    ,tutorialText
                    ,game.screenWidth * 0.5f - bounds.width / 2f
                    ,game.screenHeight * 0.25f - bounds.height / 2f
            );
            tutorialButton.x -= tutorialButton.border;
            tutorialButton.x -= tutorialButton.margin;

        }
        {
            String startText = "Start";
            BitmapFont.TextBounds bounds = game.fontReg32.getBounds( startText );
            startButton = new Button(
                    game
                    ,game.fontReg32
                    ,startText
                    ,game.screenWidth * 0.5f - bounds.width / 2f
                    ,game.screenHeight * 0.15f - bounds.height / 2f
            );
            startButton.x -= startButton.border;
            startButton.x -= startButton.margin;

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
            game.drawFontFromCenterRelative( game.batch
                                            ,game.fontBlk100
                                            ,"FlipMatch"
                                            ,0.5f
                                            ,0.5f );

            startButton.draw( game.batch );
            tutorialButton.draw( game.batch );
        }
        game.batch.end();

        if ( Gdx.input.justTouched() )
        {
            //Get touch coords and convert them to game space
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            if( startButton.isTouched( touchPos.x, touchPos.y ) )
            {
                game.setScreen( game.gameScreen );
                dispose();
            }

            if( tutorialButton.isTouched( touchPos.x, touchPos.y ) )
            {
                game.setScreen( game.controlsScreen );
                dispose();
            }
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
