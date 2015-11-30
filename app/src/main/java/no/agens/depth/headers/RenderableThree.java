package no.agens.depth.headers;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PathMeasure;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;

public class RenderableThree
  extends Renderable
{
  private static final int HORIZONTAL_SLICES = 1;
  private static final int TOTAL_SLICES_COUNT = 192;
  private static final int VERTICAL_SLICES = 95;
  private final float[] drawingVerts = new float['ƀ'];
  private boolean isBounceAnimatin = false;
  private float offsetInPercent;
  private Paint p = new Paint();
  private Paint paint = new Paint();
  private Path pathLeft = new Path();
  private Path pathRight = new Path();
  Spring spring;
  private SpringSystem springSystem = SpringSystem.create();
  private final float[] staticVerts = new float['ƀ'];
  
  public RenderableThree(Bitmap paramBitmap, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    super(paramBitmap, paramFloat1, paramFloat2);
    this.p.setColor(-16777216);
    this.p.setStrokeWidth(6.0F);
    this.p.setStyle(Paint.Style.STROKE);
    createVerts();
    this.paint.setAlpha((int)(255.0F * paramFloat3));
  }
  
  private void createPath()
  {
    this.pathLeft.reset();
    this.pathLeft.moveTo(this.x, this.y + this.bitmap.getHeight());
    this.pathLeft.cubicTo(this.x, this.y + this.bitmap.getHeight(), this.x, this.y, this.x + 1.5F * this.bitmap.getWidth() * this.offsetInPercent, this.y);
    this.pathRight.reset();
    this.pathRight.moveTo(this.x + this.bitmap.getWidth(), this.y + this.bitmap.getHeight());
    this.pathRight.cubicTo(this.x + this.bitmap.getWidth(), this.y + this.bitmap.getHeight(), this.x + this.bitmap.getWidth(), this.y + 0.3F * this.bitmap.getWidth() * this.offsetInPercent, this.x + this.bitmap.getWidth() + this.bitmap.getWidth() / 2 * this.offsetInPercent, this.y + 0.8F * this.bitmap.getWidth() * this.offsetInPercent);
    matchVertsToPath();
  }
  
  private void createVerts()
  {
    float f1 = this.bitmap.getWidth();
    float f2 = this.bitmap.getHeight();
    int i = 0;
    for (int j = 0; j <= 95; j++)
    {
      float f3 = f2 * j / 95.0F;
      for (int k = 0; k <= 1; k++)
      {
        float f4 = f1 * k / 1.0F;
        setXY(this.drawingVerts, i, f4, f3);
        setXY(this.staticVerts, i, f4, f3);
        i++;
      }
    }
  }
  
  private void matchVertsToPath()
  {
    PathMeasure localPathMeasure1 = new PathMeasure(this.pathLeft, false);
    PathMeasure localPathMeasure2 = new PathMeasure(this.pathRight, false);
    float[] arrayOfFloat = new float[2];
    int i = 0;
    if (i < this.staticVerts.length / 2)
    {
      float f1 = this.staticVerts[(1 + i * 2)];
      if (this.staticVerts[(i * 2)] == 0.0F)
      {
        float f3 = (1.0E-6F + f1) / this.bitmap.getHeight();
        localPathMeasure1.getPosTan(localPathMeasure1.getLength() * (1.0F - f3), arrayOfFloat, null);
        setXY(this.drawingVerts, i, arrayOfFloat[0], arrayOfFloat[1]);
      }
      for (;;)
      {
        i++;
        break;
        float f2 = (1.0E-6F + f1) / this.bitmap.getHeight();
        localPathMeasure2.getPosTan(localPathMeasure2.getLength() * (1.0F - f2), arrayOfFloat, null);
        setXY(this.drawingVerts, i, arrayOfFloat[0], arrayOfFloat[1]);
      }
    }
  }
  
  public void bounceBack()
  {
    cancelBounce();
    this.isBounceAnimatin = true;
    this.spring = this.springSystem.createSpring();
    this.spring.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(150.0D, 4.0D));
    final float f = this.offsetInPercent;
    this.spring.addListener(new SimpleSpringListener()
    {
      public void onSpringAtRest(Spring paramAnonymousSpring)
      {
        super.onSpringAtRest(paramAnonymousSpring);
        RenderableThree.access$102(RenderableThree.this, false);
      }
      
      public void onSpringUpdate(Spring paramAnonymousSpring)
      {
        float f = (float)paramAnonymousSpring.getCurrentValue();
        RenderableThree.access$002(RenderableThree.this, f - f * f);
      }
    });
    this.spring.setEndValue(1.0D);
  }
  
  public void cancelBounce()
  {
    if (this.spring != null) {
      this.spring.destroy();
    }
    this.isBounceAnimatin = false;
  }
  
  public void draw(Canvas paramCanvas)
  {
    createPath();
    paramCanvas.save();
    if ((this.scaleX != 1.0F) || (this.scaleY != 1.0F)) {
      paramCanvas.scale(this.scaleX, this.scaleY, this.x + this.bitmap.getWidth() / 2, this.y + this.bitmap.getHeight());
    }
    paramCanvas.drawBitmapMesh(this.bitmap, 1, 95, this.drawingVerts, 0, null, 0, this.paint);
    paramCanvas.restore();
  }
  
  public boolean isBounceAnimatin()
  {
    return this.isBounceAnimatin;
  }
  
  public void setOffsetPercent(float paramFloat)
  {
    if (!this.isBounceAnimatin) {
      this.offsetInPercent = paramFloat;
    }
  }
  
  public void setScale(float paramFloat1, float paramFloat2)
  {
    this.scaleX = paramFloat1;
    this.scaleY = paramFloat2;
  }
  
  public void setXA(float[] paramArrayOfFloat, int paramInt, float paramFloat)
  {
    paramArrayOfFloat[(0 + paramInt * 2)] = paramFloat;
  }
  
  public void setXY(float[] paramArrayOfFloat, int paramInt, float paramFloat1, float paramFloat2)
  {
    paramArrayOfFloat[(0 + paramInt * 2)] = paramFloat1;
    paramArrayOfFloat[(1 + paramInt * 2)] = paramFloat2;
  }
  
  public void setYA(float[] paramArrayOfFloat, int paramInt, float paramFloat)
  {
    paramArrayOfFloat[(1 + paramInt * 2)] = (paramFloat + this.staticVerts[(1 + paramInt * 2)]);
  }
  
  public void update(float paramFloat1, float paramFloat2)
  {
    super.update(paramFloat1, paramFloat2);
    setOffsetPercent(paramFloat2 / 100.0F);
  }
}


/* Location:              /home/andrew/works/KotSharedPreferences/a/classes_dex2jar.jar!/no/agens/depth/headers/RenderableThree.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */