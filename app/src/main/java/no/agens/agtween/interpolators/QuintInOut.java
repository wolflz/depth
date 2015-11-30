package no.agens.agtween.interpolators;

import android.animation.TimeInterpolator;

public class QuintInOut
  implements TimeInterpolator
{
  public float getInterpolation(float paramFloat)
  {
    float f1 = paramFloat * 2.0F;
    if (f1 < 1.0F) {
      return f1 * (f1 * (f1 * (f1 * (0.5F * f1))));
    }
    float f2 = f1 - 2.0F;
    return 0.5F * (2.0F + f2 * (f2 * (f2 * (f2 * f2))));
  }
}


/* Location:              /home/andrew/works/KotSharedPreferences/a/classes_dex2jar.jar!/no/agens/agtween/interpolators/QuintInOut.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */