package no.agens.depth;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.util.Property;
import android.util.TypedValue;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

public class MaterialMenuDrawable
  extends Drawable
  implements Animatable
{
  private static final float ARROW_BOT_LINE_ANGLE = 225.0F;
  private static final float ARROW_MID_LINE_ANGLE = 180.0F;
  private static final float ARROW_TOP_LINE_ANGLE = 135.0F;
  private static final int BASE_CIRCLE_RADIUS = 18;
  private static final int BASE_DRAWABLE_HEIGHT = 40;
  private static final int BASE_DRAWABLE_WIDTH = 40;
  private static final int BASE_ICON_WIDTH = 20;
  private static final float CHECK_BOTTOM_ANGLE = -90.0F;
  private static final float CHECK_MIDDLE_ANGLE = 135.0F;
  private static final int DEFAULT_CIRCLE_ALPHA = 200;
  public static final int DEFAULT_COLOR = -1;
  public static final int DEFAULT_SCALE = 1;
  public static final int DEFAULT_TRANSFORM_DURATION = 800;
  public static final boolean DEFAULT_VISIBLE = true;
  private static final float TRANSFORMATION_END = 2.0F;
  private static final float TRANSFORMATION_MID = 1.0F;
  private static final float TRANSFORMATION_START = 0.0F;
  private static final float X_BOT_LINE_ANGLE = -44.0F;
  private static final float X_ROTATION_ANGLE = 90.0F;
  private static final float X_TOP_LINE_ANGLE = 44.0F;
  private IconState animatingIconState;
  private AnimationState animationState = AnimationState.BURGER_ARROW;
  private Animator.AnimatorListener animatorListener;
  private final Paint circlePaint = new Paint();
  private final float circleRadius;
  private IconState currentIconState = IconState.BURGER;
  private final float dip1;
  private final float dip2;
  private final float dip3;
  private final float dip4;
  private final float dip8;
  private final float diph;
  private final int height;
  private final Paint iconPaint = new Paint();
  private final float iconWidth;
  private final Object lock = new Object();
  private MaterialMenuState materialMenuState;
  private boolean rtlEnabled;
  private final float sidePadding;
  private final Stroke stroke;
  private final float strokeWidth;
  private final float topPadding;
  private ObjectAnimator transformation;
  private Property<MaterialMenuDrawable, Float> transformationProperty = new Property(Float.class, "transformation")
  {
    public Float get(MaterialMenuDrawable paramAnonymousMaterialMenuDrawable)
    {
      return paramAnonymousMaterialMenuDrawable.getTransformationValue();
    }
    
    public void set(MaterialMenuDrawable paramAnonymousMaterialMenuDrawable, Float paramAnonymousFloat)
    {
      paramAnonymousMaterialMenuDrawable.setTransformationValue(paramAnonymousFloat);
    }
  };
  private boolean transformationRunning = false;
  private float transformationValue = 0.0F;
  private boolean visible;
  private final int width;
  
  private MaterialMenuDrawable(int paramInt1, Stroke paramStroke, long paramLong, int paramInt2, int paramInt3, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    this.dip1 = paramFloat4;
    this.dip2 = (paramFloat4 * 2.0F);
    this.dip3 = (3.0F * paramFloat4);
    this.dip4 = (4.0F * paramFloat4);
    this.dip8 = (8.0F * paramFloat4);
    this.diph = (paramFloat4 / 2.0F);
    this.stroke = paramStroke;
    this.width = paramInt2;
    this.height = paramInt3;
    this.iconWidth = paramFloat1;
    this.circleRadius = paramFloat2;
    this.strokeWidth = paramFloat3;
    this.sidePadding = ((paramInt2 - paramFloat1) / 2.0F);
    this.topPadding = ((paramInt3 - 5.0F * this.dip3) / 2.0F);
    initPaint(paramInt1);
    initAnimations((int)paramLong);
    this.materialMenuState = new MaterialMenuState(null);
  }
  
  public MaterialMenuDrawable(Context paramContext, int paramInt, Stroke paramStroke)
  {
    this(paramContext, paramInt, paramStroke, 1, 800);
  }
  
  public MaterialMenuDrawable(Context paramContext, int paramInt1, Stroke paramStroke, int paramInt2)
  {
    this(paramContext, paramInt1, paramStroke, 1, paramInt2);
  }
  
  public MaterialMenuDrawable(Context paramContext, int paramInt1, Stroke paramStroke, int paramInt2, int paramInt3)
  {
    Resources localResources = paramContext.getResources();
    this.dip1 = (dpToPx(localResources, 1.0F) * paramInt2);
    this.dip2 = (dpToPx(localResources, 2.0F) * paramInt2);
    this.dip3 = (dpToPx(localResources, 3.0F) * paramInt2);
    this.dip4 = (dpToPx(localResources, 4.0F) * paramInt2);
    this.dip8 = (dpToPx(localResources, 8.0F) * paramInt2);
    this.diph = (this.dip1 / 2.0F);
    this.stroke = paramStroke;
    this.visible = true;
    this.width = ((int)(dpToPx(localResources, 40.0F) * paramInt2));
    this.height = ((int)(dpToPx(localResources, 40.0F) * paramInt2));
    this.iconWidth = (dpToPx(localResources, 20.0F) * paramInt2);
    this.circleRadius = (dpToPx(localResources, 18.0F) * paramInt2);
    this.strokeWidth = (dpToPx(localResources, paramStroke.strokeWidth) * paramInt2);
    this.sidePadding = ((this.width - this.iconWidth) / 2.0F);
    this.topPadding = ((this.height - 5.0F * this.dip3) / 2.0F);
    initPaint(paramInt1);
    initAnimations(paramInt3);
    this.materialMenuState = new MaterialMenuState(null);
  }
  
  static float dpToPx(Resources paramResources, float paramFloat)
  {
    return TypedValue.applyDimension(1, paramFloat, paramResources.getDisplayMetrics());
  }
  
  private void drawBottomLine(Canvas paramCanvas, float paramFloat)
  {
    paramCanvas.restore();
    paramCanvas.save();
    float f1 = this.width / 2 + this.dip3 / 2.0F;
    float f2 = this.height - this.topPadding - this.dip2;
    float f3 = this.sidePadding;
    float f4 = this.height - this.topPadding - this.dip2;
    float f5 = this.width - this.sidePadding;
    float f6 = this.height - this.topPadding - this.dip2;
    int i = 3.$SwitchMap$no$agens$depth$MaterialMenuDrawable$AnimationState[this.animationState.ordinal()];
    float f7 = 0.0F;
    float f8 = 0.0F;
    float f9 = 0.0F;
    float f10 = 0.0F;
    switch (i)
    {
    }
    for (;;)
    {
      paramCanvas.rotate(f9, f7, f8);
      paramCanvas.rotate(f10, f1, f2);
      paramCanvas.drawLine(f3, f4, f5, f6, this.iconPaint);
      return;
      if (isMorphingForward()) {}
      for (f9 = 135.0F * paramFloat;; f9 = 135.0F + 225.0F * (1.0F - paramFloat))
      {
        f7 = this.width / 2;
        f8 = this.height / 2;
        f5 = this.width - this.sidePadding - resolveStrokeModifier(paramFloat);
        f3 = this.sidePadding + paramFloat * this.dip3;
        f10 = 0.0F;
        break;
      }
      if (isMorphingForward()) {}
      for (f10 = -90.0F * paramFloat;; f10 = 90.0F * paramFloat)
      {
        f9 = -44.0F * paramFloat;
        f7 = this.sidePadding + this.dip4;
        f8 = this.height - this.topPadding - this.dip3;
        f3 += paramFloat * this.dip3;
        break;
      }
      f9 = 135.0F + 181.0F * paramFloat;
      f10 = -90.0F * paramFloat;
      f7 = this.width / 2 + paramFloat * (this.sidePadding + this.dip4 - this.width / 2);
      f8 = this.height / 2 + paramFloat * (this.height / 2 - this.topPadding - this.dip3);
      f5 -= resolveStrokeModifier(paramFloat);
      f3 += this.dip3;
      continue;
      f9 = 135.0F + -90.0F * paramFloat;
      f7 = this.width / 2 + paramFloat * this.dip3;
      f8 = this.height / 2 - paramFloat * this.dip3;
      f5 -= resolveStrokeModifier(1.0F);
      f3 += this.dip3 + paramFloat * (this.dip4 + this.dip1);
      f10 = 0.0F;
      continue;
      f9 = paramFloat * 45.0F;
      f7 = this.width / 2 + paramFloat * this.dip3;
      f8 = this.height / 2 - paramFloat * this.dip3;
      f3 += paramFloat * this.dip8;
      f5 -= resolveStrokeModifier(paramFloat);
      f10 = 0.0F;
      continue;
      f10 = -90.0F * (1.0F - paramFloat);
      f9 = -44.0F + 89.0F * paramFloat;
      f7 = this.sidePadding + this.dip4 + paramFloat * (this.width / 2 + this.dip3 - this.sidePadding - this.dip4);
      f8 = this.height - this.topPadding - this.dip3 + paramFloat * (this.topPadding + this.height / 2 - this.height);
      f3 += this.dip8 - (this.dip4 + this.dip1) * (1.0F - paramFloat);
      f5 -= resolveStrokeModifier(1.0F - paramFloat);
    }
  }
  
  private void drawMiddleLine(Canvas paramCanvas, float paramFloat)
  {
    paramCanvas.restore();
    paramCanvas.save();
    float f1 = this.width / 2;
    float f2 = this.width / 2;
    float f3 = this.sidePadding;
    float f4 = this.topPadding + 5.0F * (this.dip3 / 2.0F);
    float f5 = this.width - this.sidePadding;
    float f6 = this.topPadding + 5.0F * (this.dip3 / 2.0F);
    int i = 255;
    int j = 3.$SwitchMap$no$agens$depth$MaterialMenuDrawable$AnimationState[this.animationState.ordinal()];
    float f7 = 0.0F;
    switch (j)
    {
    }
    for (;;)
    {
      this.iconPaint.setAlpha(i);
      paramCanvas.rotate(f7, f1, f2);
      paramCanvas.drawLine(f3, f4, f5, f6, this.iconPaint);
      this.iconPaint.setAlpha(255);
      return;
      if (isMorphingForward()) {}
      for (f7 = paramFloat * 180.0F;; f7 = 180.0F + 180.0F * (1.0F - paramFloat))
      {
        f5 -= paramFloat * resolveStrokeModifier(paramFloat) / 2.0F;
        break;
      }
      i = (int)(255.0F * (1.0F - paramFloat));
      f7 = 0.0F;
      continue;
      i = (int)(255.0F * (1.0F - paramFloat));
      f3 += (1.0F - paramFloat) * this.dip2;
      f7 = 0.0F;
      continue;
      if (isMorphingForward()) {}
      for (f7 = paramFloat * 135.0F;; f7 = 135.0F - 135.0F * (1.0F - paramFloat))
      {
        f3 += this.dip3 / 2.0F + this.dip4 - (1.0F - paramFloat) * this.dip2;
        f5 += paramFloat * this.dip1;
        f1 = this.width / 2 + this.dip3 + this.diph;
        break;
      }
      f7 = paramFloat * 135.0F;
      f3 += paramFloat * (this.dip4 + this.dip3 / 2.0F);
      f5 += paramFloat * this.dip1;
      f1 = this.width / 2 + this.dip3 + this.diph;
      continue;
      i = (int)(255.0F * paramFloat);
      f7 = paramFloat * 135.0F;
      f3 += paramFloat * (this.dip4 + this.dip3 / 2.0F);
      f5 += paramFloat * this.dip1;
      f1 = this.width / 2 + this.dip3 + this.diph;
    }
  }
  
  private void drawTopLine(Canvas paramCanvas, float paramFloat)
  {
    paramCanvas.save();
    float f1 = this.width / 2 + this.dip3 / 2.0F;
    float f2 = this.topPadding + this.dip2;
    float f3 = this.sidePadding;
    float f4 = this.topPadding + this.dip2;
    float f5 = this.width - this.sidePadding;
    float f6 = this.topPadding + this.dip2;
    int i = 255;
    int j = 3.$SwitchMap$no$agens$depth$MaterialMenuDrawable$AnimationState[this.animationState.ordinal()];
    float f7 = 0.0F;
    float f8 = 0.0F;
    float f9 = 0.0F;
    float f10 = 0.0F;
    switch (j)
    {
    }
    for (;;)
    {
      this.iconPaint.setAlpha(i);
      paramCanvas.rotate(f9, f7, f8);
      paramCanvas.rotate(f10, f1, f2);
      paramCanvas.drawLine(f3, f4, f5, f6, this.iconPaint);
      this.iconPaint.setAlpha(255);
      return;
      if (isMorphingForward()) {}
      for (f9 = paramFloat * 225.0F;; f9 = 225.0F + 135.0F * (1.0F - paramFloat))
      {
        f7 = this.width / 2;
        f8 = this.height / 2;
        f5 -= resolveStrokeModifier(paramFloat);
        f3 += paramFloat * this.dip3;
        f10 = 0.0F;
        break;
      }
      f9 = 44.0F * paramFloat;
      f10 = 90.0F * paramFloat;
      f7 = this.sidePadding + this.dip4;
      f8 = this.topPadding + this.dip3;
      f3 += paramFloat * this.dip3;
      continue;
      f9 = 225.0F + -181.0F * paramFloat;
      f10 = 90.0F * paramFloat;
      f7 = this.width / 2 + paramFloat * (this.sidePadding + this.dip4 - this.width / 2);
      f8 = this.height / 2 + paramFloat * (this.topPadding + this.dip3 - this.height / 2);
      f5 -= resolveStrokeModifier(paramFloat);
      f3 += this.dip3;
      continue;
      i = (int)(255.0F * (1.0F - paramFloat));
      f9 = 225.0F;
      f7 = this.width / 2;
      f8 = this.height / 2;
      f5 -= resolveStrokeModifier(1.0F);
      f3 += this.dip3;
      f10 = 0.0F;
      continue;
      i = (int)(255.0F * (1.0F - paramFloat));
      f7 = 0.0F;
      f8 = 0.0F;
      f9 = 0.0F;
      f10 = 0.0F;
      continue;
      f9 = 44.0F;
      f10 = 90.0F;
      f7 = this.sidePadding + this.dip4;
      f8 = this.topPadding + this.dip3;
      f5 += this.dip3 - this.dip3 * (1.0F - paramFloat);
      f3 += this.dip3;
      i = (int)(255.0F * (1.0F - paramFloat));
    }
  }
  
  private void initAnimations(int paramInt)
  {
    this.transformation = ObjectAnimator.ofFloat(this, this.transformationProperty, new float[] { 0.0F });
    this.transformation.setInterpolator(new DecelerateInterpolator(3.0F));
    this.transformation.setDuration(paramInt);
    this.transformation.addListener(new AnimatorListenerAdapter()
    {
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        MaterialMenuDrawable.access$202(MaterialMenuDrawable.this, false);
        MaterialMenuDrawable.this.setIconState(MaterialMenuDrawable.this.animatingIconState);
      }
    });
  }
  
  private void initPaint(int paramInt)
  {
    this.iconPaint.setAntiAlias(true);
    this.iconPaint.setStyle(Paint.Style.STROKE);
    this.iconPaint.setStrokeWidth(this.strokeWidth);
    this.iconPaint.setColor(paramInt);
    this.circlePaint.setAntiAlias(true);
    this.circlePaint.setStyle(Paint.Style.FILL);
    this.circlePaint.setColor(paramInt);
    this.circlePaint.setAlpha(200);
    setBounds(0, 0, this.width, this.height);
  }
  
  private boolean isMorphingForward()
  {
    return this.transformationValue <= 1.0F;
  }
  
  private float resolveStrokeModifier(float paramFloat)
  {
    switch (3.$SwitchMap$no$agens$depth$MaterialMenuDrawable$Stroke[this.stroke.ordinal()])
    {
    default: 
      return 0.0F;
    case 1: 
      if ((this.animationState == AnimationState.ARROW_X) || (this.animationState == AnimationState.X_CHECK)) {
        return this.dip3 - paramFloat * this.dip3;
      }
      return paramFloat * this.dip3;
    case 2: 
      if ((this.animationState == AnimationState.ARROW_X) || (this.animationState == AnimationState.X_CHECK)) {
        return this.dip3 + this.diph - paramFloat * (this.dip3 + this.diph);
      }
      return paramFloat * (this.dip3 + this.diph);
    }
    if ((this.animationState == AnimationState.ARROW_X) || (this.animationState == AnimationState.X_CHECK)) {
      return this.dip4 - paramFloat * (this.dip3 + this.dip1);
    }
    return paramFloat * this.dip4;
  }
  
  private boolean resolveTransformation()
  {
    boolean bool1;
    boolean bool2;
    label24:
    boolean bool3;
    label36:
    int i;
    label49:
    int j;
    label62:
    int k;
    label75:
    int m;
    if (this.currentIconState == IconState.BURGER)
    {
      bool1 = true;
      if (this.currentIconState != IconState.ARROW) {
        break label133;
      }
      bool2 = true;
      if (this.currentIconState != IconState.X) {
        break label138;
      }
      bool3 = true;
      if (this.currentIconState != IconState.CHECK) {
        break label143;
      }
      i = 1;
      if (this.animatingIconState != IconState.BURGER) {
        break label149;
      }
      j = 1;
      if (this.animatingIconState != IconState.ARROW) {
        break label155;
      }
      k = 1;
      if (this.animatingIconState != IconState.X) {
        break label161;
      }
      m = 1;
      label88:
      if (this.animatingIconState != IconState.CHECK) {
        break label167;
      }
    }
    label133:
    label138:
    label143:
    label149:
    label155:
    label161:
    label167:
    for (int n = 1;; n = 0)
    {
      if (((!bool1) || (k == 0)) && ((!bool2) || (j == 0))) {
        break label173;
      }
      this.animationState = AnimationState.BURGER_ARROW;
      return bool1;
      bool1 = false;
      break;
      bool2 = false;
      break label24;
      bool3 = false;
      break label36;
      i = 0;
      break label49;
      j = 0;
      break label62;
      k = 0;
      break label75;
      m = 0;
      break label88;
    }
    label173:
    if (((bool2) && (m != 0)) || ((bool3) && (k != 0)))
    {
      this.animationState = AnimationState.ARROW_X;
      return bool2;
    }
    if (((bool1) && (m != 0)) || ((bool3) && (j != 0)))
    {
      this.animationState = AnimationState.BURGER_X;
      return bool1;
    }
    if (((bool2) && (n != 0)) || ((i != 0) && (k != 0)))
    {
      this.animationState = AnimationState.ARROW_CHECK;
      return bool2;
    }
    if (((bool1) && (n != 0)) || ((i != 0) && (j != 0)))
    {
      this.animationState = AnimationState.BURGER_CHECK;
      return bool1;
    }
    if (((bool3) && (n != 0)) || ((i != 0) && (m != 0)))
    {
      this.animationState = AnimationState.X_CHECK;
      return bool3;
    }
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = this.currentIconState;
    arrayOfObject[1] = this.animatingIconState;
    throw new IllegalStateException(String.format("Animating from %s to %s is not supported", arrayOfObject));
  }
  
  public void animateIconState(IconState paramIconState)
  {
    synchronized (this.lock)
    {
      if (this.transformationRunning) {
        this.transformation.end();
      }
      this.animatingIconState = paramIconState;
      start();
      return;
    }
  }
  
  public void draw(Canvas paramCanvas)
  {
    if (!this.visible) {
      return;
    }
    if (this.transformationValue <= 1.0F) {}
    for (float f = this.transformationValue;; f = 2.0F - this.transformationValue)
    {
      if (this.rtlEnabled)
      {
        paramCanvas.save();
        paramCanvas.scale(-1.0F, 1.0F, 0.0F, 0.0F);
        paramCanvas.translate(-getIntrinsicWidth(), 0.0F);
      }
      drawTopLine(paramCanvas, f);
      drawMiddleLine(paramCanvas, f);
      drawBottomLine(paramCanvas, f);
      if (!this.rtlEnabled) {
        break;
      }
      paramCanvas.restore();
      return;
    }
  }
  
  public Drawable.ConstantState getConstantState()
  {
    MaterialMenuState.access$402(this.materialMenuState, getChangingConfigurations());
    return this.materialMenuState;
  }
  
  public IconState getIconState()
  {
    return this.currentIconState;
  }
  
  public int getIntrinsicHeight()
  {
    return this.height;
  }
  
  public int getIntrinsicWidth()
  {
    return this.width;
  }
  
  public int getOpacity()
  {
    return -2;
  }
  
  public Float getTransformationValue()
  {
    return Float.valueOf(this.transformationValue);
  }
  
  public boolean isDrawableVisible()
  {
    return this.visible;
  }
  
  public boolean isRunning()
  {
    return this.transformationRunning;
  }
  
  public Drawable mutate()
  {
    this.materialMenuState = new MaterialMenuState(null);
    return this;
  }
  
  public void setAlpha(int paramInt)
  {
    this.iconPaint.setAlpha(paramInt);
  }
  
  public void setAnimationListener(Animator.AnimatorListener paramAnimatorListener)
  {
    if (this.animatorListener != null) {
      this.transformation.removeListener(this.animatorListener);
    }
    if (paramAnimatorListener != null) {
      this.transformation.addListener(paramAnimatorListener);
    }
    this.animatorListener = paramAnimatorListener;
  }
  
  public void setColor(int paramInt)
  {
    this.iconPaint.setColor(paramInt);
    this.circlePaint.setColor(paramInt);
    invalidateSelf();
  }
  
  public void setColorFilter(ColorFilter paramColorFilter)
  {
    this.iconPaint.setColorFilter(paramColorFilter);
  }
  
  public void setIconState(IconState paramIconState)
  {
    for (;;)
    {
      synchronized (this.lock)
      {
        if (this.transformationRunning)
        {
          this.transformation.cancel();
          this.transformationRunning = false;
        }
        if (this.currentIconState == paramIconState) {
          return;
        }
        switch (paramIconState)
        {
        default: 
          this.currentIconState = paramIconState;
          invalidateSelf();
          return;
        }
      }
      this.animationState = AnimationState.BURGER_ARROW;
      this.transformationValue = 0.0F;
      continue;
      this.animationState = AnimationState.BURGER_ARROW;
      this.transformationValue = 1.0F;
      continue;
      this.animationState = AnimationState.BURGER_X;
      this.transformationValue = 1.0F;
      continue;
      this.animationState = AnimationState.BURGER_CHECK;
      this.transformationValue = 1.0F;
    }
  }
  
  public void setInterpolator(Interpolator paramInterpolator)
  {
    this.transformation.setInterpolator(paramInterpolator);
  }
  
  public void setRTLEnabled(boolean paramBoolean)
  {
    this.rtlEnabled = paramBoolean;
    invalidateSelf();
  }
  
  public void setTransformationDuration(int paramInt)
  {
    this.transformation.setDuration(paramInt);
  }
  
  public IconState setTransformationOffset(AnimationState paramAnimationState, float paramFloat)
  {
    if ((paramFloat < 0.0F) || (paramFloat > 2.0F))
    {
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Float.valueOf(0.0F);
      arrayOfObject[1] = Float.valueOf(2.0F);
      throw new IllegalArgumentException(String.format("Value must be between %s and %s", arrayOfObject));
    }
    this.animationState = paramAnimationState;
    int i;
    if (paramFloat >= 1.0F)
    {
      boolean bool = paramFloat < 2.0F;
      i = 0;
      if (bool) {}
    }
    else
    {
      i = 1;
    }
    IconState localIconState1;
    if (i != 0)
    {
      localIconState1 = paramAnimationState.getFirstState();
      this.currentIconState = localIconState1;
      if (i == 0) {
        break label129;
      }
    }
    label129:
    for (IconState localIconState2 = paramAnimationState.getSecondState();; localIconState2 = paramAnimationState.getFirstState())
    {
      this.animatingIconState = localIconState2;
      setTransformationValue(Float.valueOf(paramFloat));
      return this.currentIconState;
      localIconState1 = paramAnimationState.getSecondState();
      break;
    }
  }
  
  public void setTransformationValue(Float paramFloat)
  {
    this.transformationValue = paramFloat.floatValue();
    invalidateSelf();
  }
  
  public void setVisible(boolean paramBoolean)
  {
    this.visible = paramBoolean;
    invalidateSelf();
  }
  
  public void start()
  {
    float f1 = 1.0F;
    if (this.transformationRunning) {
      return;
    }
    ObjectAnimator localObjectAnimator;
    float[] arrayOfFloat;
    float f2;
    if ((this.animatingIconState != null) && (this.animatingIconState != this.currentIconState))
    {
      this.transformationRunning = true;
      boolean bool = resolveTransformation();
      localObjectAnimator = this.transformation;
      arrayOfFloat = new float[2];
      if (!bool) {
        break label88;
      }
      f2 = 0.0F;
      arrayOfFloat[0] = f2;
      if (!bool) {
        break label94;
      }
    }
    for (;;)
    {
      arrayOfFloat[1] = f1;
      localObjectAnimator.setFloatValues(arrayOfFloat);
      this.transformation.start();
      invalidateSelf();
      return;
      label88:
      f2 = f1;
      break;
      label94:
      f1 = 2.0F;
    }
  }
  
  public void stop()
  {
    if ((isRunning()) && (this.transformation.isRunning()))
    {
      this.transformation.end();
      return;
    }
    this.transformationRunning = false;
    invalidateSelf();
  }
  
  public static enum AnimationState
  {
    static
    {
      ARROW_X = new AnimationState("ARROW_X", 2);
      ARROW_CHECK = new AnimationState("ARROW_CHECK", 3);
      BURGER_CHECK = new AnimationState("BURGER_CHECK", 4);
      X_CHECK = new AnimationState("X_CHECK", 5);
      AnimationState[] arrayOfAnimationState = new AnimationState[6];
      arrayOfAnimationState[0] = BURGER_ARROW;
      arrayOfAnimationState[1] = BURGER_X;
      arrayOfAnimationState[2] = ARROW_X;
      arrayOfAnimationState[3] = ARROW_CHECK;
      arrayOfAnimationState[4] = BURGER_CHECK;
      arrayOfAnimationState[5] = X_CHECK;
      $VALUES = arrayOfAnimationState;
    }
    
    private AnimationState() {}
    
    public MaterialMenuDrawable.IconState getFirstState()
    {
      switch (MaterialMenuDrawable.3.$SwitchMap$no$agens$depth$MaterialMenuDrawable$AnimationState[ordinal()])
      {
      default: 
        return null;
      case 1: 
        return MaterialMenuDrawable.IconState.BURGER;
      case 2: 
        return MaterialMenuDrawable.IconState.BURGER;
      case 3: 
        return MaterialMenuDrawable.IconState.ARROW;
      case 4: 
        return MaterialMenuDrawable.IconState.ARROW;
      case 5: 
        return MaterialMenuDrawable.IconState.BURGER;
      }
      return MaterialMenuDrawable.IconState.X;
    }
    
    public MaterialMenuDrawable.IconState getSecondState()
    {
      switch (MaterialMenuDrawable.3.$SwitchMap$no$agens$depth$MaterialMenuDrawable$AnimationState[ordinal()])
      {
      default: 
        return null;
      case 1: 
        return MaterialMenuDrawable.IconState.ARROW;
      case 2: 
        return MaterialMenuDrawable.IconState.X;
      case 3: 
        return MaterialMenuDrawable.IconState.X;
      case 4: 
        return MaterialMenuDrawable.IconState.CHECK;
      case 5: 
        return MaterialMenuDrawable.IconState.CHECK;
      }
      return MaterialMenuDrawable.IconState.CHECK;
    }
  }
  
  public static enum IconState
  {
    static
    {
      ARROW = new IconState("ARROW", 1);
      X = new IconState("X", 2);
      CHECK = new IconState("CHECK", 3);
      IconState[] arrayOfIconState = new IconState[4];
      arrayOfIconState[0] = BURGER;
      arrayOfIconState[1] = ARROW;
      arrayOfIconState[2] = X;
      arrayOfIconState[3] = CHECK;
      $VALUES = arrayOfIconState;
    }
    
    private IconState() {}
  }
  
  private final class MaterialMenuState
    extends Drawable.ConstantState
  {
    private int changingConfigurations;
    
    private MaterialMenuState() {}
    
    public int getChangingConfigurations()
    {
      return this.changingConfigurations;
    }
    
    public Drawable newDrawable()
    {
      MaterialMenuDrawable localMaterialMenuDrawable = new MaterialMenuDrawable(MaterialMenuDrawable.this.circlePaint.getColor(), MaterialMenuDrawable.this.stroke, MaterialMenuDrawable.this.transformation.getDuration(), MaterialMenuDrawable.this.width, MaterialMenuDrawable.this.height, MaterialMenuDrawable.this.iconWidth, MaterialMenuDrawable.this.circleRadius, MaterialMenuDrawable.this.strokeWidth, MaterialMenuDrawable.this.dip1, null);
      if (MaterialMenuDrawable.this.animatingIconState != null) {}
      for (MaterialMenuDrawable.IconState localIconState = MaterialMenuDrawable.this.animatingIconState;; localIconState = MaterialMenuDrawable.this.currentIconState)
      {
        localMaterialMenuDrawable.setIconState(localIconState);
        localMaterialMenuDrawable.setVisible(MaterialMenuDrawable.this.visible);
        localMaterialMenuDrawable.setRTLEnabled(MaterialMenuDrawable.this.rtlEnabled);
        return localMaterialMenuDrawable;
      }
    }
  }
  
  public static enum Stroke
  {
    private final int strokeWidth;
    
    static
    {
      EXTRA_THIN = new Stroke("EXTRA_THIN", 2, 1);
      Stroke[] arrayOfStroke = new Stroke[3];
      arrayOfStroke[0] = REGULAR;
      arrayOfStroke[1] = THIN;
      arrayOfStroke[2] = EXTRA_THIN;
      $VALUES = arrayOfStroke;
    }
    
    private Stroke(int paramInt)
    {
      this.strokeWidth = paramInt;
    }
    
    protected static Stroke valueOf(int paramInt)
    {
      switch (paramInt)
      {
      default: 
        return THIN;
      case 3: 
        return REGULAR;
      case 2: 
        return THIN;
      }
      return EXTRA_THIN;
    }
  }
}


/* Location:              /home/andrew/works/KotSharedPreferences/a/classes_dex2jar.jar!/no/agens/depth/MaterialMenuDrawable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */