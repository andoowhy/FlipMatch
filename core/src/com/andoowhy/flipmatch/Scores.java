package com.andoowhy.flipmatch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.Collections;

public class Scores
{
    public final FlipMatch game;
    public final int numScores = 10;

    private ArrayList<Label> labels = new ArrayList<Label>();

    Preferences save = Gdx.app.getPreferences("scores");

    public Scores( FlipMatch game )
    {
        this.game = game;

        ArrayList<Float> scores = new ArrayList<Float>();

        //Read previous scores
        for( int i = 0; i < numScores; i++ )
        {
            float savedScore = save.getFloat( "score" + Integer.toString( i + 1 ), 0f );
            if( savedScore == 0f )
            {
                break;
            }
            else
            {
                scores.add( savedScore );
            }
        }

        //Display Previous Scores
        for( int i = 0; i < scores.size(); i++ )
        {
            String labelText = Integer.toString( i + 1 ) + ": " + save.getFloat( "score" + Integer.toString( i + 1), 0f );
            BitmapFont.TextBounds bounds = game.fontReg32.getBounds( labelText );
            Label label = new Label(
                    game
                    ,game.fontReg32
                    ,labelText
                    ,game.screenWidth * 0.5f - bounds.width / 2f
                    ,game.screenHeight * ( 0.68f - 0.05f * i ) - bounds.height / 2f
            );
            labels.add(label);
        }
    }

    public void addScore( float score )
    {
        ArrayList<Float> scores = new ArrayList<Float>();

        //Read previous scores
        for( int i = 0; i < numScores; i++ )
        {
            float savedScore = save.getFloat( "score" + Integer.toString( i + 1 ), 0f );
            if( savedScore == 0f )
            {
                break;
            }
            else
            {
                scores.add( savedScore );
            }
        }

        //Add new score
        scores.add( score );

        //Sort scores and trim to top 10
        Collections.sort( scores );
        while( scores.size() >= numScores )
        {
            scores.remove( scores.size() - 1 );
        }

        //Save scores;
        for( int i = 0; i < scores.size(); i++ )
        {
            save.putFloat( "score" + Integer.toString( i + 1 ), scores.get( i ) );
        }
        save.flush();

        //Update Labels
        labels.clear();
        for( int i = 0; i < scores.size(); i++ )
        {
            String labelText = Integer.toString( i + 1 ) + ": " + save.getFloat( "score" + Integer.toString( i + 1), 0f );
            BitmapFont.TextBounds bounds = game.fontReg32.getBounds( labelText );
            Label label = new Label(
                    game
                    ,game.fontReg32
                    ,labelText
                    ,game.screenWidth * 0.5f - bounds.width / 2f
                    ,game.screenHeight * ( 0.68f - 0.05f * i ) - bounds.height / 2f
            );
            labels.add(label);
        }
    }

    public void draw( SpriteBatch batch )
    {
        for( Label label : labels )
        {
            label.draw( batch );
        }
    }
}
