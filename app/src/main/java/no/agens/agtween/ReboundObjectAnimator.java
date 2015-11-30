package no.agens.agtween;

import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.util.Log;
import android.util.Property;
import android.view.View;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

public class ReboundObjectAnimator
  extends ValueAnimator
{
  float currentAnimatedValue;
  float fraction;
  private double friction = 6.0D;
  private float from;
  Property<View, Float> property;
  private Method propertyMethod;
  private Spring spring;
  private Object target;
  private double tension = 150.0D;
  private float to;
  ArrayList<ValueAnimator.AnimatorUpdateListener> updateListeners = null;
  
  public ReboundObjectAnimator(Object paramObject, Property paramProperty, float paramFloat1, float paramFloat2)
  {
    this.property = paramProperty;
    this.from = paramFloat1;
    this.to = paramFloat2;
    this.target = paramObject;
  }
  
  public ReboundObjectAnimator(Object paramObject, String paramString, float paramFloat1, float paramFloat2)
  {
    this.propertyMethod = getPropertyMethod(paramObject, "set" + paramString);
    this.from = paramFloat1;
    this.to = paramFloat2;
    this.target = paramObject;
  }
  
  public static Method findMethod(Class<?> paramClass, String paramString)
    throws NoSuchMethodException
  {
    for (Method localMethod : paramClass.getMethods()) {
      if (localMethod.getName().equals(paramString)) {
        return localMethod;
      }
    }
    throw new NoSuchMethodException();
  }
  
  public static Method getPropertyMethod(Object paramObject, String paramString)
  {
    Class localClass = paramObject.getClass();
    try
    {
      Method localMethod = findMethod(localClass, paramString);
      return localMethod;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      localNoSuchMethodException.printStackTrace();
      Log.e("REBOUND_OBJECT_ANIM", "Could not find property: " + paramString);
    }
    return null;
  }
  
  public static ReboundObjectAnimator ofFloat(Object paramObject, Property paramProperty, float paramFloat1, float paramFloat2)
  {
    return new ReboundObjectAnimator(paramObject, paramProperty, paramFloat1, paramFloat2);
  }
  
  public static ReboundObjectAnimator ofFloat(Object paramObject, String paramString, float paramFloat1, float paramFloat2)
  {
    return new ReboundObjectAnimator(paramObject, paramString, paramFloat1, paramFloat2);
  }
  
  public void addUpdateListener(ValueAnimator.AnimatorUpdateListener paramAnimatorUpdateListener)
  {
    if (this.updateListeners == null) {
      this.updateListeners = new ArrayList();
    }
    this.updateListeners.add(paramAnimatorUpdateListener);
  }
  
  public void cancel()
  {
    if (this.spring != null) {
      this.spring.destroy();
    }
    if (getListeners() != null)
    {
      Iterator localIterator = getListeners().iterator();
      while (localIterator.hasNext()) {
        ((Animator.AnimatorListener)localIterator.next()).onAnimationCancel(null);
      }
    }
    removeAllListeners();
  }
  
  public float getAnimatedFraction()
  {
    return this.fraction;
  }
  
  public Object getAnimatedValue()
  {
    return Float.valueOf(this.currentAnimatedValue);
  }
  
  public double getFriction()
  {
    return this.friction;
  }
  
  public long getStartDelay()
  {
    return 0L;
  }
  
  public double getTension()
  {
    return this.tension;
  }
  
  public boolean isRunning()
  {
    return (this.spring != null) && (!this.spring.isAtRest());
  }
  
  public void removeAllUpdateListeners()
  {
    if (this.updateListeners == null) {
      return;
    }
    this.updateListeners.clear();
    this.updateListeners = null;
  }
  
  public void removeUpdateListener(ValueAnimator.AnimatorUpdateListener paramAnimatorUpdateListener)
  {
    if (this.updateListeners == null) {}
    do
    {
      return;
      this.updateListeners.remove(paramAnimatorUpdateListener);
    } while (this.updateListeners.size() != 0);
    this.updateListeners = null;
  }
  
  public ReboundObjectAnimator setFriction(double paramDouble)
  {
    this.friction = paramDouble;
    return this;
  }
  
  public void setStartDelay(long paramLong) {}
  
  public ReboundObjectAnimator setTension(double paramDouble)
  {
    this.tension = paramDouble;
    return this;
  }
  
  public void start()
  {
    this.spring = SpringSystem.create().createSpring();
    this.spring.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(this.tension, this.friction));
    final float f = this.to - this.from;
    this.spring.addListener(new SimpleSpringListener()
    {
      public void onSpringAtRest(Spring paramAnonymousSpring)
      {
        super.onSpringAtRest(paramAnonymousSpring);
        paramAnonymousSpring.destroy();
        if (ReboundObjectAnimator.this.getListeners() != null)
        {
          Iterator localIterator = ReboundObjectAnimator.this.getListeners().iterator();
          while (localIterator.hasNext()) {
            ((Animator.AnimatorListener)localIterator.next()).onAnimationEnd(null);
          }
        }
        ReboundObjectAnimator.this.removeAllListeners();
      }
      
      public void onSpringUpdate(Spring paramAnonymousSpring)
      {
        ReboundObjectAnimator.this.fraction = ((float)paramAnonymousSpring.getCurrentValue());
        ReboundObjectAnimator.this.currentAnimatedValue = (ReboundObjectAnimator.this.from + ReboundObjectAnimator.this.fraction * f);
        if (ReboundObjectAnimator.this.propertyMethod != null) {}
        for (;;)
        {
          try
          {
            Method localMethod = ReboundObjectAnimator.this.propertyMethod;
            Object localObject = ReboundObjectAnimator.this.target;
            Object[] arrayOfObject = new Object[1];
            arrayOfObject[0] = Float.valueOf(ReboundObjectAnimator.this.currentAnimatedValue);
            localMethod.invoke(localObject, arrayOfObject);
            Iterator localIterator = ReboundObjectAnimator.this.updateListeners.iterator();
            if (!localIterator.hasNext()) {
              break;
            }
            ((ValueAnimator.AnimatorUpdateListener)localIterator.next()).onAnimationUpdate(ReboundObjectAnimator.this);
            continue;
          }
          catch (IllegalAccessException localIllegalAccessException)
          {
            localIllegalAccessException.printStackTrace();
            continue;
          }
          catch (InvocationTargetException localInvocationTargetException)
          {
            localInvocationTargetException.printStackTrace();
            continue;
          }
          if (ReboundObjectAnimator.this.property != null) {
            ReboundObjectAnimator.this.property.set((View)ReboundObjectAnimator.this.target, Float.valueOf(ReboundObjectAnimator.this.currentAnimatedValue));
          }
        }
      }
    });
    this.spring.setEndValue(1.0D);
  }
}


/* Location:              /home/andrew/works/KotSharedPreferences/a/classes_dex2jar.jar!/no/agens/agtween/ReboundObjectAnimator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */