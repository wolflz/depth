package no.agens.depth;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Fragment;
import android.content.res.Resources;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import no.agens.depth.headers.BearSceneView;

public class WindFragment
  extends Fragment
  implements MenuAnimation
{
  public static final int FLAMES_INITIAL_HEIGHT = 50;
  BearSceneView bearsScene;
  private boolean introAnimate;
  MaterialMenuDrawable menuIcon;
  View root;
  AnimatorListenerAdapter showShadowListener = new AnimatorListenerAdapter()
  {
    public void onAnimationEnd(Animator paramAnonymousAnimator)
    {
      super.onAnimationEnd(paramAnonymousAnimator);
      WindFragment.this.showShadow();
      WindFragment.this.bearsScene.setPause(false);
    }
  };
  
  private void doIntroAnimation()
  {
    if (this.introAnimate) {
      this.root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
      {
        public void onGlobalLayout()
        {
          WindFragment.this.root.getViewTreeObserver().removeOnGlobalLayoutListener(this);
          TransitionHelper.startIntroAnim(WindFragment.this.root, WindFragment.this.showShadowListener);
          WindFragment.this.hideShadow();
          WindFragment.this.bearsScene.postDelayed(new Runnable()
          {
            public void run()
            {
              WindFragment.this.bearsScene.setPause(true);
            }
          }, 10L);
        }
      });
    }
  }
  
  private void hideShadow()
  {
    this.root.findViewById(2131492971).setVisibility(8);
  }
  
  public static void setProgressBarColor(SeekBar paramSeekBar, int paramInt)
  {
    if ((paramSeekBar.getProgressDrawable() instanceof StateListDrawable))
    {
      ((StateListDrawable)paramSeekBar.getProgressDrawable()).setColorFilter(paramInt, PorterDuff.Mode.SRC_IN);
      paramSeekBar.getThumb().setColorFilter(paramInt, PorterDuff.Mode.SRC_IN);
    }
    while (!(paramSeekBar.getProgressDrawable() instanceof LayerDrawable)) {
      return;
    }
    LayerDrawable localLayerDrawable = (LayerDrawable)paramSeekBar.getProgressDrawable();
    for (int i = 0; i < localLayerDrawable.getNumberOfLayers(); i++) {
      localLayerDrawable.getDrawable(i).setColorFilter(paramInt, PorterDuff.Mode.SRC_IN);
    }
    paramSeekBar.getThumb().setColorFilter(paramInt, PorterDuff.Mode.SRC_IN);
  }
  
  private void setupFabButton()
  {
    this.root.findViewById(2131492988).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        WindFragment.this.root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
          public void onGlobalLayout()
          {
            WindFragment.this.root.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            TransitionHelper.startExitAnim(WindFragment.this.root);
          }
        });
        WaterFragment localWaterFragment = new WaterFragment();
        localWaterFragment.setIntroAnimate(true);
        ((RootActivity)WindFragment.this.getActivity()).goToFragment(localWaterFragment);
        if (((RootActivity)WindFragment.this.getActivity()).isMenuVisible) {
          ((RootActivity)WindFragment.this.getActivity()).hideMenu();
        }
        WindFragment.this.hideShadow();
        WindFragment.this.bearsScene.setPause(true);
      }
    });
  }
  
  private void setupMenuButton()
  {
    ImageView localImageView = (ImageView)this.root.findViewById(2131492970);
    localImageView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (!((RootActivity)WindFragment.this.getActivity()).isMenuVisible)
        {
          ((RootActivity)WindFragment.this.getActivity()).showMenu();
          return;
        }
        WindFragment.this.getActivity().onBackPressed();
      }
    });
    this.menuIcon = new MaterialMenuDrawable(getActivity(), -1, MaterialMenuDrawable.Stroke.THIN, 900);
    localImageView.setImageDrawable(this.menuIcon);
  }
  
  private void setupSliders()
  {
    SeekBar localSeekBar1 = (SeekBar)this.root.findViewById(2131492989);
    final SeekBar localSeekBar2 = (SeekBar)this.root.findViewById(2131492990);
    setProgressBarColor(localSeekBar1, getResources().getColor(2131427360));
    setProgressBarColor(localSeekBar2, getResources().getColor(2131427360));
    localSeekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
    {
      public void onProgressChanged(SeekBar paramAnonymousSeekBar, int paramAnonymousInt, boolean paramAnonymousBoolean)
      {
        WindFragment.this.bearsScene.setWind(paramAnonymousInt);
      }
      
      public void onStartTrackingTouch(SeekBar paramAnonymousSeekBar) {}
      
      public void onStopTrackingTouch(SeekBar paramAnonymousSeekBar) {}
    });
    localSeekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
    {
      public void onProgressChanged(SeekBar paramAnonymousSeekBar, int paramAnonymousInt, boolean paramAnonymousBoolean)
      {
        WindFragment.this.bearsScene.setFlamesHeight(paramAnonymousInt);
      }
      
      public void onStartTrackingTouch(SeekBar paramAnonymousSeekBar) {}
      
      public void onStopTrackingTouch(SeekBar paramAnonymousSeekBar) {}
    });
    this.bearsScene.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
    {
      public void onGlobalLayout()
      {
        WindFragment.this.bearsScene.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        localSeekBar2.setProgress(50);
      }
    });
  }
  
  private void showShadow()
  {
    View localView = this.root.findViewById(2131492971);
    localView.setVisibility(0);
    ObjectAnimator.ofFloat(localView, View.ALPHA, new float[] { 0.0F, 0.8F }).setDuration(400L).start();
  }
  
  public void animateTOMenu()
  {
    TransitionHelper.animateToMenuState(this.root, new AnimatorListenerAdapter()
    {
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        super.onAnimationEnd(paramAnonymousAnimator);
        WindFragment.this.bearsScene.setPause(false);
      }
    });
    this.menuIcon.animateIconState(MaterialMenuDrawable.IconState.ARROW);
    hideShadow();
    this.bearsScene.setPause(true);
  }
  
  public void exitFromMenu()
  {
    TransitionHelper.animateMenuOut(this.root);
    this.bearsScene.setPause(true);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    this.root = paramLayoutInflater.inflate(2130968609, paramViewGroup, false);
    this.bearsScene = ((BearSceneView)this.root.findViewById(2131492980));
    doIntroAnimation();
    setupFabButton();
    setupMenuButton();
    ((RootActivity)getActivity()).setCurretMenuIndex(1);
    setupSliders();
    return this.root;
  }
  
  public void revertFromMenu()
  {
    TransitionHelper.startRevertFromMenu(this.root, this.showShadowListener);
    this.menuIcon.animateIconState(MaterialMenuDrawable.IconState.BURGER);
    this.bearsScene.setPause(true);
  }
  
  public void setIntroAnimate(boolean paramBoolean)
  {
    this.introAnimate = paramBoolean;
  }
}


/* Location:              /home/andrew/works/KotSharedPreferences/a/classes_dex2jar.jar!/no/agens/depth/WindFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */