package com.andoowhy.flipmatch;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Label extends GUIElement
{
    private String text;
    private BitmapFont font;
    private BitmapFont.TextBounds bounds;

    public Label( FlipMatch game, BitmapFont font, String text, float x, float y )
    {
        super( game, x, y );
        this.text = text;
        this.font = font;

        this.bounds = font.getBounds( this.text );
    }

    public String getText()
    {
        return text;
    }

    public void setText( String str )
    {
        this.text = str;
        this.bounds = font.getBounds( this.text );
    }

    public float getWidth()
    {
        return bounds.width;
    }

    public float getHeight()
    {
        return bounds.height;
    }

    @Override
    public void draw( SpriteBatch batch )
    {
        font.draw( batch, text, x, y + bounds.height );
    }
}
