package no.agens.agtween.interpolators;

import android.animation.TimeInterpolator;

public class SineOut
  implements TimeInterpolator
{
  public float getInterpolation(float paramFloat)
  {
    return (float)Math.sin(1.5707963267948966D * paramFloat);
  }
}


/* Location:              /home/andrew/works/KotSharedPreferences/a/classes_dex2jar.jar!/no/agens/agtween/interpolators/SineOut.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */