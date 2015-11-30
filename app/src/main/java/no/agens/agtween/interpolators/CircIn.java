package no.agens.agtween.interpolators;

import android.animation.TimeInterpolator;

public class CircIn
  implements TimeInterpolator
{
  public float getInterpolation(float paramFloat)
  {
    return (float)Math.sqrt(1.0F - paramFloat * paramFloat);
  }
}


/* Location:              /home/andrew/works/KotSharedPreferences/a/classes_dex2jar.jar!/no/agens/agtween/interpolators/CircIn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */