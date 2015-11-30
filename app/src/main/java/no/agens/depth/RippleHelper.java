package no.agens.depth;

import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.RippleDrawable;

public class RippleHelper
{
  public static ColorDrawable getColorDrawableFromColor(int paramInt)
  {
    return new ColorDrawable(paramInt);
  }
  
  public static RippleDrawable getPressedColorRippleDrawable(int paramInt1, int paramInt2)
  {
    return new RippleDrawable(getPressedColorSelector(paramInt1, paramInt2), getColorDrawableFromColor(paramInt1), null);
  }
  
  public static ColorStateList getPressedColorSelector(int paramInt1, int paramInt2)
  {
    return new ColorStateList(new int[][] { { 16842919 }, { 16842908 }, { 16843518 }, new int[0] }, new int[] { paramInt2, paramInt2, paramInt2, paramInt1 });
  }
}


/* Location:              /home/andrew/works/KotSharedPreferences/a/classes_dex2jar.jar!/no/agens/depth/RippleHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */