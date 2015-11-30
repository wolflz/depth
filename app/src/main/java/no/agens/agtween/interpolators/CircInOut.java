package no.agens.agtween.interpolators;

import android.animation.TimeInterpolator;

public class CircInOut
  implements TimeInterpolator
{
  public float getInterpolation(float paramFloat)
  {
    float f1 = paramFloat * 2.0F;
    if (f1 < 1.0F) {
      return -0.5F * ((float)Math.sqrt(1.0F - f1 * f1) - 1.0F);
    }
    float f2 = f1 - 2.0F;
    return 0.5F * (1.0F + (float)Math.sqrt(1.0F - f2 * f2));
  }
}


/* Location:              /home/andrew/works/KotSharedPreferences/a/classes_dex2jar.jar!/no/agens/agtween/interpolators/CircInOut.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */