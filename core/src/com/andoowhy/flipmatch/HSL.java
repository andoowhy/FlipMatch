package com.andoowhy.flipmatch;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

public class HSL
{
    public static Color toRGB(float h, float s, float l, float alpha)
    {
        if (s <0.0f || s > 100.0f)
        {
            String message = "Color parameter outside of expected range - Saturation";
            throw new IllegalArgumentException( message );
        }

        if (l <0.0f || l > 100.0f)
        {
            String message = "Color parameter outside of expected range - Luminance";
            throw new IllegalArgumentException( message );
        }

        if (alpha <0.0f || alpha > 1.0f)
        {
            String message = "Color parameter outside of expected range - Alpha";
            throw new IllegalArgumentException( message );
        }

        float q = 0;

        if (l < 0.5)
            q = l * (1 + s);
        else
            q = (l + s) - (s * l);

        float p = 2 * l - q;

        float r = Math.max(0, HueToRGB(p, q, h + (1.0f / 3.0f)));
        float g = Math.max(0, HueToRGB(p, q, h));
        float b = Math.max(0, HueToRGB(p, q, h - (1.0f / 3.0f)));

        return new Color(r, g, b, alpha);
    }

    private static float HueToRGB(float p, float q, float h)
    {
        if (h < 0) h += 1;

        if (h > 1 ) h -= 1;

        if (6 * h < 1)
        {
            return p + ((q - p) * 6 * h);
        }

        if (2 * h < 1 )
        {
            return  q;
        }

        if (3 * h < 2)
        {
            return p + ( (q - p) * 6 * ((2.0f / 3.0f) - h) );
        }

        return p;
    }
}
