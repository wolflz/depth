package no.agens.agtween.interpolators;

import android.animation.TimeInterpolator;

public class ElasticOut
  implements TimeInterpolator
{
  protected float param_a;
  protected float param_p;
  protected boolean setA = false;
  protected boolean setP = false;
  
  public ElasticOut a(float paramFloat)
  {
    this.param_a = paramFloat;
    this.setA = true;
    return this;
  }
  
  public float getInterpolation(float paramFloat)
  {
    float f1 = this.param_a;
    float f2 = this.param_p;
    if (paramFloat == 0.0F) {
      return 0.0F;
    }
    if (paramFloat == 1.0F) {
      return 1.0F;
    }
    if (!this.setP) {
      f2 = 0.3F;
    }
    if ((!this.setA) || (f1 < 1.0F)) {
      f1 = 1.0F;
    }
    for (float f3 = f2 / 4.0F;; f3 = f2 / 6.2831855F * (float)Math.asin(1.0F / f1)) {
      return 1.0F + f1 * (float)Math.pow(2.0D, -10.0F * paramFloat) * (float)Math.sin(6.283185307179586D * (paramFloat - f3) / f2);
    }
  }
  
  public ElasticOut p(float paramFloat)
  {
    this.param_p = paramFloat;
    this.setP = true;
    return this;
  }
}


/* Location:              /home/andrew/works/KotSharedPreferences/a/classes_dex2jar.jar!/no/agens/agtween/interpolators/ElasticOut.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */