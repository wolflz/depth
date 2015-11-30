package no.agens.agtween.interpolators;

import android.animation.TimeInterpolator;

public class ExpoInOut
  implements TimeInterpolator
{
  public float getInterpolation(float paramFloat)
  {
    if (paramFloat == 0.0F) {
      return 0.0F;
    }
    if (paramFloat == 1.0F) {
      return 1.0F;
    }
    float f = paramFloat * 2.0F;
    if (f < 1.0F) {
      return 0.5F * (float)Math.pow(2.0D, 10.0F * (f - 1.0F));
    }
    return 0.5F * (2.0F + -(float)Math.pow(2.0D, -10.0F * (f - 1.0F)));
  }
}


/* Location:              /home/andrew/works/KotSharedPreferences/a/classes_dex2jar.jar!/no/agens/agtween/interpolators/ExpoInOut.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */