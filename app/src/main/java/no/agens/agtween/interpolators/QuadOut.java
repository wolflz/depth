package no.agens.agtween.interpolators;

import android.animation.TimeInterpolator;

public class QuadOut
  implements TimeInterpolator
{
  public float getInterpolation(float paramFloat)
  {
    return -paramFloat * (paramFloat - 2.0F);
  }
}


/* Location:              /home/andrew/works/KotSharedPreferences/a/classes_dex2jar.jar!/no/agens/agtween/interpolators/QuadOut.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */