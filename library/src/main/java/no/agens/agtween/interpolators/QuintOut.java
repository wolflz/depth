package no.agens.agtween.interpolators;

import android.animation.TimeInterpolator;

public class QuintOut
  implements TimeInterpolator
{
  public float getInterpolation(float paramFloat)
  {
    float f = paramFloat - 1.0F;
    return 1.0F + f * (f * (f * (f * f)));
  }
}


/* Location:              /home/andrew/works/KotSharedPreferences/a/classes_dex2jar.jar!/no/agens/agtween/interpolators/QuintOut.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */