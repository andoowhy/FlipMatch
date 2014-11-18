package com.andoowhy.flipmatch;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FlipCard
{
    public static final float margin = 10f;
    public static final int width = 100;
    public static final int height = 100;
    public static final int xOffset = 25;
    public static final int yOffset = 250;

    public boolean flipped = false;

    private final FlipMatch game;
    public float x;
    public float y;

    public FlipCard( final FlipMatch game, float x, float y )
    {
        this.game = game;
        this.x = x;
        this.y = y;
    }

    public void draw( SpriteBatch batch )
    {
        if( flipped )
        {
            batch.draw( game.unflippedFace, x, y );
        }
        else
        {
            batch.draw( game.unflippedFace, x, y );
        }
    }
}
