package no.agens.agtween.interpolators;

import android.animation.TimeInterpolator;

public class SineInOut
  implements TimeInterpolator
{
  public float getInterpolation(float paramFloat)
  {
    return -0.5F * ((float)Math.cos(3.141592653589793D * paramFloat) - 1.0F);
  }
}


/* Location:              /home/andrew/works/KotSharedPreferences/a/classes_dex2jar.jar!/no/agens/agtween/interpolators/SineInOut.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */