package no.agens.depth;

import android.animation.TypeEvaluator;
import android.graphics.Rect;

public class RectEvaluator
  implements TypeEvaluator<Rect>
{
  public Rect evaluate(float paramFloat, Rect paramRect1, Rect paramRect2)
  {
    return new Rect(paramRect1.left + (int)(paramFloat * (paramRect2.left - paramRect1.left)), paramRect1.top + (int)(paramFloat * (paramRect2.top - paramRect1.top)), paramRect1.right + (int)(paramFloat * (paramRect2.right - paramRect1.right)), paramRect1.bottom + (int)(paramFloat * (paramRect2.bottom - paramRect1.bottom)));
  }
}


/* Location:              /home/andrew/works/KotSharedPreferences/a/classes_dex2jar.jar!/no/agens/depth/RectEvaluator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */