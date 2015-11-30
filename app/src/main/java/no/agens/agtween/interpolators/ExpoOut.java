package no.agens.agtween.interpolators;

import android.animation.TimeInterpolator;

public class ExpoOut
  implements TimeInterpolator
{
  public float getInterpolation(float paramFloat)
  {
    if (paramFloat == 1.0F) {
      return 1.0F;
    }
    return 1.0F + -(float)Math.pow(2.0D, -10.0F * paramFloat);
  }
}


/* Location:              /home/andrew/works/KotSharedPreferences/a/classes_dex2jar.jar!/no/agens/agtween/interpolators/ExpoOut.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */