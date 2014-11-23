package com.andoowhy.flipmatch;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Button extends GUIElement
{
    public final String text;
    public float width, height;
    public final int border = 5;
    public final int margin = 10;

    private BitmapFont font;
    private BitmapFont.TextBounds bounds;
    private Texture buttonTexture;

    public Button( FlipMatch game, BitmapFont font, String text, float x, float y )
    {
        super( game, x, y );

        this.font = font;
        this.text = text;

        bounds = font.getBounds( text );
        int textWidth = MathUtils.ceil( bounds.width );
        int textHeight = MathUtils.ceil( bounds.height );

        width = (float)( textWidth + 2 * border + 2 * margin );
        height = (float)(textHeight + 2 * border + 2 * margin );

        Pixmap pixmap = new Pixmap( MathUtils.ceil( width ), MathUtils.ceil( height ), Pixmap.Format.RGB888 );
        //Border
        pixmap.setColor( new Color( 0.2f, 0.2f, 0.2f, 1f ) ); //red
        pixmap.fillRectangle( 0, 0, MathUtils.ceil( width ), MathUtils.ceil( height ) );
        //Fill
        pixmap.setColor( new Color( 0.9f, 0.9f, 0.9f, 1f ) ); //blue
        pixmap.fillRectangle( border, border, MathUtils.ceil( width ) - 2 * border,  MathUtils.ceil( height ) - 2 * border );

        buttonTexture = new Texture( pixmap );
    }

    @Override
    public void draw( SpriteBatch batch )
    {
        batch.draw( buttonTexture, x, y );
        font.draw( batch, text, x + border + margin, y + bounds.height + border + margin );
    }

    private boolean isTouched( float touchX, float touchY )
    {
        if( touchX >= x && touchX < x + width )
        {
            if( touchY >= y && touchY < y + height )
            {
                return true;
            }
        }
        return false;
    }
}
