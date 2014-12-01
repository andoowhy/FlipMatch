package com.andoowhy.flipmatch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class HighScoreScreen implements Screen
{
    final FlipMatch game;

    private OrthographicCamera camera;

    private Label highLabel;
    private Label scoresLabel;


    private Button backButton;

    public HighScoreScreen( final FlipMatch game )
    {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho( false, game.screenWidth, game.screenHeight );

        {
            String highText = "High";
            BitmapFont.TextBounds bounds = game.fontBlk100.getBounds( highText );
            highLabel = new Label(
                    game
                    , game.fontBlk100
                    , highText
                    , game.screenWidth * 0.5f - bounds.width / 2f
                    , game.screenHeight * 0.9f - bounds.height / 2f
            );
        }
        {
            String scoresText = "Scores:";
            BitmapFont.TextBounds bounds = game.fontBlk100.getBounds( scoresText );
            scoresLabel = new Label(
                    game
                    , game.fontBlk100
                    , scoresText
                    , game.screenWidth * 0.5f - bounds.width / 2f
                    , game.screenHeight * 0.8f - bounds.height / 2f
            );
        }
        {
            String backText = "Back";
            BitmapFont.TextBounds bounds = game.fontReg32.getBounds( backText );
            backButton = new Button(
                    game
                    ,game.fontReg32
                    ,backText
                    ,game.screenWidth * 0.5f - bounds.width / 2f
                    ,game.screenHeight * 0.1f - bounds.height / 2f
            );
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

            if( backButton.isTouched( touchPos.x, touchPos.y ) )
            {
                game.setScreen( game.gameScreen );
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
            highLabel.draw( game.batch );
            scoresLabel.draw( game.batch );
            backButton.draw( game.batch );
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
