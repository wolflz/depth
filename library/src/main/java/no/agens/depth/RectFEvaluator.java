package no.agens.depth;

import android.animation.TypeEvaluator;
import android.graphics.RectF;

public class RectFEvaluator
  implements TypeEvaluator<RectF>
{
  public RectF evaluate(float paramFloat, RectF paramRectF1, RectF paramRectF2)
  {
    return new RectF(paramRectF1.left + (int)(paramFloat * (paramRectF2.left - paramRectF1.left)), paramRectF1.top + (int)(paramFloat * (paramRectF2.top - paramRectF1.top)), paramRectF1.right + (int)(paramFloat * (paramRectF2.right - paramRectF1.right)), paramRectF1.bottom + (int)(paramFloat * (paramRectF2.bottom - paramRectF1.bottom)));
  }
}


/* Location:              /home/andrew/works/KotSharedPreferences/a/classes_dex2jar.jar!/no/agens/depth/RectFEvaluator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */