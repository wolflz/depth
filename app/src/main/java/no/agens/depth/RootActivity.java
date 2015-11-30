package no.agens.depth;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Property;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import no.agens.agtween.interpolators.ExpoIn;
import no.agens.agtween.interpolators.QuintOut;

public class RootActivity
  extends Activity
{
  Fragment currentFragment;
  int curretMenuIndex = 0;
  boolean isMenuVisible = false;
  ViewGroup menu;
  
  private void addMenuItem(ViewGroup paramViewGroup, String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    ViewGroup localViewGroup = (ViewGroup)LayoutInflater.from(this).inflate(2130968612, paramViewGroup, false);
    ((TextView)localViewGroup.findViewById(2131492994)).setText(paramString);
    CircularSplashView localCircularSplashView = (CircularSplashView)localViewGroup.findViewById(2131492993);
    localCircularSplashView.setSplash(BitmapFactory.decodeResource(getResources(), paramInt1));
    localCircularSplashView.setSplashColor(paramInt2);
    localViewGroup.setOnClickListener(getMenuItemCLick(paramInt4, paramInt2));
    if (paramInt4 == 0)
    {
      int j = (int)getResources().getDimension(2131165274);
      paramViewGroup.addView(localViewGroup, new LinearLayout.LayoutParams(-1, j + (int)getResources().getDimension(2131165273)));
      localViewGroup.setPadding(0, j, 0, 0);
    }
    for (;;)
    {
      localViewGroup.setBackground(getResources().getDrawable(paramInt3, null));
      return;
      if (paramInt4 == 3)
      {
        int i = (int)getResources().getDimension(2131165274);
        paramViewGroup.addView(localViewGroup, new LinearLayout.LayoutParams(-1, i + (int)getResources().getDimension(2131165273)));
        localViewGroup.setPadding(0, 0, 0, i);
      }
      else
      {
        paramViewGroup.addView(localViewGroup, new LinearLayout.LayoutParams(-1, (int)getResources().getDimension(2131165273)));
      }
    }
  }
  
  private void fadeColoTo(int paramInt, TextView paramTextView)
  {
    ArgbEvaluator localArgbEvaluator = new ArgbEvaluator();
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Integer.valueOf(paramTextView.getCurrentTextColor());
    arrayOfObject[1] = Integer.valueOf(paramInt);
    ObjectAnimator localObjectAnimator = ObjectAnimator.ofObject(paramTextView, "TextColor", localArgbEvaluator, arrayOfObject);
    localObjectAnimator.setDuration(200L);
    localObjectAnimator.start();
  }
  
  private View.OnClickListener getMenuItemCLick(final int paramInt1, final int paramInt2)
  {
    new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (paramInt1 == RootActivity.this.curretMenuIndex) {
          RootActivity.this.onBackPressed();
        }
        do
        {
          return;
          if ((paramInt1 == 0) && (!(RootActivity.this.currentFragment instanceof WaterFragment)))
          {
            ((MenuAnimation)RootActivity.this.currentFragment).exitFromMenu();
            WaterFragment localWaterFragment = new WaterFragment();
            localWaterFragment.setIntroAnimate(true);
            RootActivity.this.goToFragment(localWaterFragment);
            RootActivity.this.hideMenu();
            RootActivity.this.selectMenuItem(paramInt1, paramInt2);
            return;
          }
          if ((paramInt1 == 1) && (!(RootActivity.this.currentFragment instanceof WindFragment)))
          {
            ((MenuAnimation)RootActivity.this.currentFragment).exitFromMenu();
            WindFragment localWindFragment = new WindFragment();
            localWindFragment.setIntroAnimate(true);
            RootActivity.this.goToFragment(localWindFragment);
            RootActivity.this.hideMenu();
            RootActivity.this.selectMenuItem(paramInt1, paramInt2);
            return;
          }
        } while (paramInt1 != 2);
        RootActivity.this.startActivity(new Intent(RootActivity.this, PlayGroundActivity.class));
        RootActivity.this.onBackPressed();
      }
    };
  }
  
  private void makeAppFullscreen()
  {
    getWindow().setStatusBarColor(0);
    getWindow().getDecorView().setSystemUiVisibility(1280);
  }
  
  private void select(View paramView, int paramInt)
  {
    CircularSplashView localCircularSplashView = (CircularSplashView)paramView.findViewById(2131492993);
    localCircularSplashView.setScaleX(1.0F);
    localCircularSplashView.setScaleY(1.0F);
    localCircularSplashView.setVisibility(0);
    localCircularSplashView.introAnimate();
    fadeColoTo(paramInt, (TextView)paramView.findViewById(2131492994));
  }
  
  private void selectMenuItem(int paramInt1, int paramInt2)
  {
    int i = 0;
    if (i < this.menu.getChildCount())
    {
      View localView = this.menu.getChildAt(i);
      if (i == paramInt1) {
        select(localView, paramInt2);
      }
      for (;;)
      {
        i++;
        break;
        unSelect(localView);
      }
    }
    this.curretMenuIndex = paramInt1;
  }
  
  private void setupMenu()
  {
    this.menu = ((ViewGroup)findViewById(2131492967));
    int i = getResources().getColor(2131427399);
    addMenuItem(this.menu, "Water And Noise", 2130837584, i, 2130837573, 0);
    addMenuItem(this.menu, "Two Bears", 2130837585, getResources().getColor(2131427400), 2130837574, 1);
    addMenuItem(this.menu, "Depth Playground", 2130837586, getResources().getColor(2131427401), 2130837575, 2);
    addMenuItem(this.menu, "About", 2130837587, getResources().getColor(2131427402), 2130837576, 3);
    selectMenuItem(0, i);
    this.menu.setTranslationY(20000.0F);
  }
  
  private void unSelect(View paramView)
  {
    final View localView = paramView.findViewById(2131492993);
    localView.animate().scaleX(0.0F).scaleY(0.0F).setDuration(150L).withEndAction(new Runnable()
    {
      public void run()
      {
        localView.setVisibility(4);
      }
    }).start();
    fadeColoTo(-16777216, (TextView)paramView.findViewById(2131492994));
  }
  
  public void goToFragment(Fragment paramFragment)
  {
    getFragmentManager().beginTransaction().add(2131492966, paramFragment).commit();
    final Fragment localFragment = this.currentFragment;
    this.currentFragment = paramFragment;
    getWindow().getDecorView().postDelayed(new Runnable()
    {
      public void run()
      {
        RootActivity.this.getFragmentManager().beginTransaction().remove(localFragment).commit();
      }
    }, 2000L);
  }
  
  public void hideMenu()
  {
    this.isMenuVisible = false;
    ViewGroup localViewGroup = this.menu;
    Property localProperty = View.TRANSLATION_Y;
    float[] arrayOfFloat = new float[1];
    arrayOfFloat[0] = this.menu.getHeight();
    ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(localViewGroup, localProperty, arrayOfFloat);
    localObjectAnimator.setDuration(750L);
    localObjectAnimator.setInterpolator(new ExpoIn());
    localObjectAnimator.start();
  }
  
  public void onBackPressed()
  {
    if (this.isMenuVisible)
    {
      hideMenu();
      ((MenuAnimation)this.currentFragment).revertFromMenu();
      return;
    }
    super.onBackPressed();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130968601);
    makeAppFullscreen();
    if (paramBundle == null)
    {
      this.currentFragment = new WaterFragment();
      getFragmentManager().beginTransaction().add(2131492966, this.currentFragment).commit();
    }
    setupMenu();
  }
  
  public void setCurretMenuIndex(int paramInt)
  {
    this.curretMenuIndex = paramInt;
  }
  
  public void showMenu()
  {
    this.isMenuVisible = true;
    ViewGroup localViewGroup = this.menu;
    Property localProperty = View.TRANSLATION_Y;
    float[] arrayOfFloat = new float[2];
    arrayOfFloat[0] = this.menu.getHeight();
    arrayOfFloat[1] = 0.0F;
    ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(localViewGroup, localProperty, arrayOfFloat);
    localObjectAnimator.setDuration(1000L);
    localObjectAnimator.setInterpolator(new QuintOut());
    localObjectAnimator.setStartDelay(150L);
    localObjectAnimator.start();
    selectMenuItem(this.curretMenuIndex, ((TextView)this.menu.getChildAt(this.curretMenuIndex).findViewById(2131492994)).getCurrentTextColor());
    ((MenuAnimation)this.currentFragment).animateTOMenu();
  }
}


/* Location:              /home/andrew/works/KotSharedPreferences/a/classes_dex2jar.jar!/no/agens/depth/RootActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */