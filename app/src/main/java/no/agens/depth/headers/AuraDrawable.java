package no.agens.depth.headers;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import java.util.Random;
import no.agens.depth.MathHelper;

public class AuraDrawable
  extends Renderable
{
  private Drawable drawable;
  long lastflicker;
  
  public AuraDrawable(Drawable paramDrawable, Rect paramRect)
  {
    super(null, 0.0F, 0.0F);
    paramDrawable.setBounds(paramRect);
    this.drawable = paramDrawable;
    this.lastflicker = System.currentTimeMillis();
  }
  
  public void draw(Canvas paramCanvas)
  {
    this.drawable.draw(paramCanvas);
  }
  
  public void update(float paramFloat1, float paramFloat2)
  {
    if (50L + this.lastflicker < System.currentTimeMillis())
    {
      this.drawable.setAlpha((int)(255.0F * (30.0F + MathHelper.rand.nextInt(25)) / 100.0F));
      this.lastflicker = System.currentTimeMillis();
    }
  }
}


/* Location:              /home/andrew/works/KotSharedPreferences/a/classes_dex2jar.jar!/no/agens/depth/headers/AuraDrawable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */