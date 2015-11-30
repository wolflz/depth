package no.agens.depth;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class PlayGroundActivity
  extends Activity
{
  private static final float CAMERA_DISTANCE = 6000.0F;
  private static final float MAX_DEPTH = 20.0F;
  private static final float MAX_ELEVATION = 50.0F;
  private static final float MAX_ROTATION_X = 90.0F;
  private static final float MAX_ROTATION_Y = 90.0F;
  private static final float MAX_ROTATION_Z = 360.0F;
  private DepthLayout depthView;
  private int seekbarColor;
  
  private void makeAppFullscreen()
  {
    getWindow().setStatusBarColor(0);
    getWindow().getDecorView().setSystemUiVisibility(1280);
  }
  
  private void setupDepthSeekbar()
  {
    SeekBar localSeekBar = (SeekBar)findViewById(R.id.depth_seekbar);
    WindFragment.setProgressBarColor(localSeekBar, this.seekbarColor);
    localSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
    {
      public void onProgressChanged(SeekBar paramAnonymousSeekBar, int paramAnonymousInt, boolean paramAnonymousBoolean)
      {
        PlayGroundActivity.this.depthView.setDepth(20.0F * PlayGroundActivity.this.getResources().getDisplayMetrics().density * (paramAnonymousInt / paramAnonymousSeekBar.getMax()));
      }
      
      public void onStartTrackingTouch(SeekBar paramAnonymousSeekBar) {}
      
      public void onStopTrackingTouch(SeekBar paramAnonymousSeekBar) {}
    });
    localSeekBar.setProgress((int)(0.1F * localSeekBar.getMax()));
  }
  
  private void setupElevationSeekbar()
  {
    SeekBar localSeekBar = (SeekBar)findViewById(R.id.elevation_seekbar);
    localSeekBar.setProgress(0);
    WindFragment.setProgressBarColor(localSeekBar, this.seekbarColor);
    localSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
    {
      public void onProgressChanged(SeekBar paramAnonymousSeekBar, int paramAnonymousInt, boolean paramAnonymousBoolean)
      {
        PlayGroundActivity.this.depthView.setCustomShadowElevation(50.0F * (paramAnonymousInt / paramAnonymousSeekBar.getMax()) * PlayGroundActivity.this.getResources().getDisplayMetrics().density);
      }
      
      public void onStartTrackingTouch(SeekBar paramAnonymousSeekBar) {}
      
      public void onStopTrackingTouch(SeekBar paramAnonymousSeekBar) {}
    });
    localSeekBar.setProgress((int)(0.5F * localSeekBar.getMax()));
  }
  
  private void setupMenuButton()
  {
    ImageView localImageView = (ImageView)findViewById(R.id.menu);
    localImageView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        PlayGroundActivity.this.finish();
      }
    });
    MaterialMenuDrawable localMaterialMenuDrawable = new MaterialMenuDrawable(this, -1, MaterialMenuDrawable.Stroke.THIN, 900);
    localImageView.setImageDrawable(localMaterialMenuDrawable);
    localMaterialMenuDrawable.setIconState(MaterialMenuDrawable.IconState.ARROW);
  }
  
  private SeekBar setupRotationXSeekbar()
  {
    SeekBar localSeekBar = (SeekBar)findViewById(R.id.rotation_x_seekbar);
    WindFragment.setProgressBarColor(localSeekBar, this.seekbarColor);
    localSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
    {
      public void onProgressChanged(SeekBar paramAnonymousSeekBar, int paramAnonymousInt, boolean paramAnonymousBoolean)
      {
        PlayGroundActivity.this.depthView.setRotationX(-90.0F + 180.0F * (paramAnonymousInt / paramAnonymousSeekBar.getMax()));
      }
      
      public void onStartTrackingTouch(SeekBar paramAnonymousSeekBar) {}
      
      public void onStopTrackingTouch(SeekBar paramAnonymousSeekBar) {}
    });
    localSeekBar.setProgress((int)(0.1F * localSeekBar.getMax()));
    return localSeekBar;
  }
  
  private void setupRotationYSeekbar()
  {
    SeekBar localSeekBar = (SeekBar)findViewById(R.id.rotation_y_seekbar);
    localSeekBar.setProgress((int)(0.5F * localSeekBar.getMax()));
    WindFragment.setProgressBarColor(localSeekBar, this.seekbarColor);
    localSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
    {
      public void onProgressChanged(SeekBar paramAnonymousSeekBar, int paramAnonymousInt, boolean paramAnonymousBoolean)
      {
        PlayGroundActivity.this.depthView.setRotationY(-90.0F + 180.0F * (paramAnonymousInt / paramAnonymousSeekBar.getMax()));
      }
      
      public void onStartTrackingTouch(SeekBar paramAnonymousSeekBar) {}
      
      public void onStopTrackingTouch(SeekBar paramAnonymousSeekBar) {}
    });
  }
  
  private void setupRotationZSeekbar()
  {
    SeekBar localSeekBar = (SeekBar)findViewById(R.id.rotation_z_seekbar);
    localSeekBar.setProgress(0);
    WindFragment.setProgressBarColor(localSeekBar, this.seekbarColor);
    localSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
    {
      public void onProgressChanged(SeekBar paramAnonymousSeekBar, int paramAnonymousInt, boolean paramAnonymousBoolean)
      {
        PlayGroundActivity.this.depthView.setRotation(-360.0F * (paramAnonymousInt / paramAnonymousSeekBar.getMax()));
      }
      
      public void onStartTrackingTouch(SeekBar paramAnonymousSeekBar) {}
      
      public void onStopTrackingTouch(SeekBar paramAnonymousSeekBar) {}
    });
  }
  
  private void setupSeekBars()
  {
    setupRotationXSeekbar();
    setupRotationYSeekbar();
    setupRotationZSeekbar();
    setupElevationSeekbar();
    setupDepthSeekbar();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.fragment_playground);
    this.seekbarColor = getResources().getColor(R.color.fab);
    this.depthView = ((DepthLayout)findViewById(R.id.depth_view));
    this.depthView.setCameraDistance(6000.0F * getResources().getDisplayMetrics().density);
    setupSeekBars();
    makeAppFullscreen();
    setupMenuButton();
  }
}


/* Location:              /home/andrew/works/KotSharedPreferences/a/classes_dex2jar.jar!/no/agens/depth/PlayGroundActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */