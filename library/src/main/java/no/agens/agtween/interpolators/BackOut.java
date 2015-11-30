package no.agens.agtween.interpolators;

import android.animation.TimeInterpolator;

public class BackOut
  implements TimeInterpolator
{
  protected float param_s = 1.70158F;
  
  public BackOut amount(float paramFloat)
  {
    this.param_s = paramFloat;
    return this;
  }
  
  public float getInterpolation(float paramFloat)
  {
    float f1 = this.param_s;
    float f2 = paramFloat - 1.0F;
    return 1.0F + f2 * f2 * (f1 + f2 * (f1 + 1.0F));
  }
}


/* Location:              /home/andrew/works/KotSharedPreferences/a/classes_dex2jar.jar!/no/agens/agtween/interpolators/BackOut.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */