package no.agens.depth;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import no.agens.depth.headers.WaterSceneView;

public class WaterFragment
  extends Fragment
  implements MenuAnimation
{
  public static final int TRANSFORM_DURATION = 900;
  private boolean introAnimate;
  MaterialMenuDrawable menuIcon;
  View root;
  AnimatorListenerAdapter showShadowListener = new AnimatorListenerAdapter()
  {
    public void onAnimationEnd(Animator paramAnonymousAnimator)
    {
      super.onAnimationEnd(paramAnonymousAnimator);
      WaterFragment.this.showShadow();
      WaterFragment.this.waterScene.setPause(false);
    }
  };
  WaterSceneView waterScene;
  
  private void hideShadow()
  {
    this.root.findViewById(R.id.actionbar_shadow).setVisibility(8);
  }
  
  private void introAnimate()
  {
    if (this.introAnimate) {
      this.root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
      {
        public void onGlobalLayout()
        {
          WaterFragment.this.root.getViewTreeObserver().removeOnGlobalLayoutListener(this);
          TransitionHelper.startIntroAnim(WaterFragment.this.root, WaterFragment.this.showShadowListener);
          WaterFragment.this.hideShadow();
          WaterFragment.this.waterScene.postDelayed(new Runnable()
          {
            public void run()
            {
              WaterFragment.this.waterScene.setPause(true);
            }
          }, 10L);
        }
      });
    }
  }
  
  private void setupFab()
  {
    this.root.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        WaterFragment.this.root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
          public void onGlobalLayout()
          {
            WaterFragment.this.root.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            TransitionHelper.startExitAnim(WaterFragment.this.root);
          }
        });
        WindFragment localWindFragment = new WindFragment();
        localWindFragment.setIntroAnimate(true);
        ((RootActivity)WaterFragment.this.getActivity()).goToFragment(localWindFragment);
        if (((RootActivity)WaterFragment.this.getActivity()).isMenuVisible) {
          ((RootActivity)WaterFragment.this.getActivity()).hideMenu();
        }
        WaterFragment.this.hideShadow();
        WaterFragment.this.waterScene.setPause(true);
      }
    });
  }
  
  private void setupMenuButton()
  {
    ImageView localImageView = (ImageView)this.root.findViewById(R.id.menu);
    localImageView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (!((RootActivity)WaterFragment.this.getActivity()).isMenuVisible)
        {
          ((RootActivity)WaterFragment.this.getActivity()).showMenu();
          return;
        }
        ((RootActivity)WaterFragment.this.getActivity()).onBackPressed();
      }
    });
    this.menuIcon = new MaterialMenuDrawable(getActivity(), -1, MaterialMenuDrawable.Stroke.THIN, 900);
    localImageView.setImageDrawable(this.menuIcon);
  }
  
  private void setupSeekbars()
  {
    SeekBar localSeekBar1 = (SeekBar)this.root.findViewById(R.id.wave_seekbar);
    SeekBar localSeekBar2 = (SeekBar)this.root.findViewById(R.id.noise_seekbar);
    WindFragment.setProgressBarColor(localSeekBar1, getResources().getColor(R.color.fab));
    WindFragment.setProgressBarColor(localSeekBar2, getResources().getColor(R.color.fab));
    localSeekBar2.setProgress(50);
    localSeekBar1.setProgress(50);
    localSeekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
    {
      public void onProgressChanged(SeekBar paramAnonymousSeekBar, int paramAnonymousInt, boolean paramAnonymousBoolean)
      {
        WaterFragment.this.waterScene.setWaveHeight(paramAnonymousInt / 4.0F * WaterFragment.this.getResources().getDisplayMetrics().density);
      }
      
      public void onStartTrackingTouch(SeekBar paramAnonymousSeekBar) {}
      
      public void onStopTrackingTouch(SeekBar paramAnonymousSeekBar) {}
    });
    localSeekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
    {
      public void onProgressChanged(SeekBar paramAnonymousSeekBar, int paramAnonymousInt, boolean paramAnonymousBoolean)
      {
        WaterFragment.this.waterScene.setNoiseIntensity(paramAnonymousInt / 100.0F);
      }
      
      public void onStartTrackingTouch(SeekBar paramAnonymousSeekBar) {}
      
      public void onStopTrackingTouch(SeekBar paramAnonymousSeekBar) {}
    });
  }
  
  private void showShadow()
  {
    View localView = this.root.findViewById(R.id.actionbar_shadow);
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
        WaterFragment.this.waterScene.setPause(false);
      }
    });
    this.menuIcon.animateIconState(MaterialMenuDrawable.IconState.ARROW);
    hideShadow();
    this.waterScene.setPause(true);
  }
  
  public void exitFromMenu()
  {
    TransitionHelper.animateMenuOut(this.root);
    this.waterScene.setPause(true);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    this.root = paramLayoutInflater.inflate(R.layout.fragment_water, paramViewGroup, false);
    this.waterScene = ((WaterSceneView)this.root.findViewById(R.id.water_scene));
    setupFab();
    introAnimate();
    setupSeekbars();
    setupMenuButton();
    ((RootActivity)getActivity()).setCurretMenuIndex(0);
    return this.root;
  }
  
  public void revertFromMenu()
  {
    TransitionHelper.startRevertFromMenu(this.root, this.showShadowListener);
    this.menuIcon.animateIconState(MaterialMenuDrawable.IconState.BURGER);
    this.waterScene.setPause(true);
  }
  
  public void setIntroAnimate(boolean paramBoolean)
  {
    this.introAnimate = paramBoolean;
  }
}


/* Location:              /home/andrew/works/KotSharedPreferences/a/classes_dex2jar.jar!/no/agens/depth/WaterFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
