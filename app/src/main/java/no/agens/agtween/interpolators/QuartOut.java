package no.agens.agtween.interpolators;

import android.animation.TimeInterpolator;

public class QuartOut
  implements TimeInterpolator
{
  public float getInterpolation(float paramFloat)
  {
    float f = paramFloat - 1.0F;
    return -(f * (f * (f * f)) - 1.0F);
  }
}


/* Location:              /home/andrew/works/KotSharedPreferences/a/classes_dex2jar.jar!/no/agens/agtween/interpolators/QuartOut.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */