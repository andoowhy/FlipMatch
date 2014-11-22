package com.andoowhy.flipmatch;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FlipCard
{
    //Statics
    public static final float margin = 10f;
    public static final int width = 100;
    public static final int height = 100;
    public static final int xOffset = 25;
    public static final int yOffset = 250;

    private final FlipMatch game;
    public float x;
    public float y;
    public boolean flipped = false;

    public Color flippedColor;

    public FlipCard( final FlipMatch game, float x, float y, Color color )
    {
        this.game = game;
        this.x = x;
        this.y = y;
        this.flippedColor = color;
    }

    public void draw( SpriteBatch batch )
    {
        if( flipped )
        {
            Color oldColor = batch.getColor();
            batch.setColor( flippedColor );
            batch.draw( game.flippedFace, x, y );
            batch.setColor( oldColor );
        }
        else
        {
            batch.draw( game.unflippedFace, x, y );
        }
    }

    public boolean isTouched(float touchX, float touchY)
    {
        if( touchX >= x && touchX < x + FlipCard.width )
        {
            if( touchY >= y && touchY < y + FlipCard.height )
            {
                return true;
            }
        }
        return false;
    }
}
