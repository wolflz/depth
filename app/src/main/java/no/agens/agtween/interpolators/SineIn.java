package no.agens.agtween.interpolators;

import android.animation.TimeInterpolator;

public class SineIn
  implements TimeInterpolator
{
  public float getInterpolation(float paramFloat)
  {
    return 1.0F + (float)-Math.cos(1.5707963267948966D * paramFloat);
  }
}


/* Location:              /home/andrew/works/KotSharedPreferences/a/classes_dex2jar.jar!/no/agens/agtween/interpolators/SineIn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */