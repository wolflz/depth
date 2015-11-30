package no.agens.depth;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

public class ColorAnimator
{
  public static ObjectAnimator ofBackgroundColor(View paramView, int paramInt)
  {
    ViewBackgroundWrapper localViewBackgroundWrapper = new ViewBackgroundWrapper(paramView);
    ColorEvaluator localColorEvaluator = new ColorEvaluator(null);
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(paramInt);
    return ObjectAnimator.ofObject(localViewBackgroundWrapper, "backgroundColor", localColorEvaluator, arrayOfObject);
  }
  
  public static ObjectAnimator ofBackgroundColor(View paramView, int paramInt1, int paramInt2)
  {
    ViewBackgroundWrapper localViewBackgroundWrapper = new ViewBackgroundWrapper(paramView);
    ColorEvaluator localColorEvaluator = new ColorEvaluator(null);
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Integer.valueOf(paramInt1);
    arrayOfObject[1] = Integer.valueOf(paramInt2);
    return ObjectAnimator.ofObject(localViewBackgroundWrapper, "backgroundColor", localColorEvaluator, arrayOfObject);
  }
  
  public static ObjectAnimator ofColor(Object paramObject, String paramString, int paramInt)
  {
    ColorEvaluator localColorEvaluator = new ColorEvaluator(null);
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(paramInt);
    return ObjectAnimator.ofObject(paramObject, paramString, localColorEvaluator, arrayOfObject);
  }
  
  public static ObjectAnimator ofColor(Object paramObject, String paramString, int paramInt1, int paramInt2)
  {
    ColorEvaluator localColorEvaluator = new ColorEvaluator(null);
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Integer.valueOf(paramInt1);
    arrayOfObject[1] = Integer.valueOf(paramInt2);
    return ObjectAnimator.ofObject(paramObject, paramString, localColorEvaluator, arrayOfObject);
  }
  
  private static class ColorEvaluator
    implements TypeEvaluator<Integer>
  {
    public Integer evaluate(float paramFloat, Integer paramInteger1, Integer paramInteger2)
    {
      int i = Color.alpha(paramInteger2.intValue());
      int j = Color.alpha(paramInteger1.intValue());
      int k = (int)(paramFloat * (i - j));
      int m = Color.red(paramInteger2.intValue());
      int n = Color.red(paramInteger1.intValue());
      int i1 = (int)(paramFloat * (m - n));
      int i2 = Color.green(paramInteger2.intValue());
      int i3 = Color.green(paramInteger1.intValue());
      int i4 = (int)(paramFloat * (i2 - i3));
      int i5 = Color.blue(paramInteger2.intValue());
      int i6 = Color.blue(paramInteger1.intValue());
      int i7 = (int)(paramFloat * (i5 - i6));
      return Integer.valueOf(Color.argb(j + k, n + i1, i3 + i4, i6 + i7));
    }
  }
  
  public static class ViewBackgroundWrapper
  {
    private View mView;
    
    public ViewBackgroundWrapper(View paramView)
    {
      this.mView = paramView;
    }
    
    public int getBackgroundColor()
    {
      try
      {
        int i = ((ColorDrawable)this.mView.getBackground()).getColor();
        return i;
      }
      catch (ClassCastException localClassCastException)
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = this.mView.getBackground().getClass().getSimpleName();
        throw new IllegalStateException(String.format("Attempt to read View background color when background isn't a ColorDrawable (is %s instead)", arrayOfObject));
      }
    }
    
    public void setBackgroundColor(int paramInt)
    {
      this.mView.setBackgroundColor(paramInt);
    }
  }
}


/* Location:              /home/andrew/works/KotSharedPreferences/a/classes_dex2jar.jar!/no/agens/depth/ColorAnimator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */