package com.andoowhy.flipmatch;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GUIElement
{
    private final FlipMatch game;
    public float x;
    public float y;

    public GUIElement( final FlipMatch game, float x, float y )
    {
        this.game = game;
        this.x = x;
        this.y = y;
    }

    public abstract void draw( SpriteBatch batch );
}
