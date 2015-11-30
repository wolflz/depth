package no.agens.agtween;

import android.os.SystemClock;

public class FrameRateCounter
{
  private static long mLastTime;
  
  public static float timeStep()
  {
    long l1 = SystemClock.uptimeMillis();
    long l2 = l1 - mLastTime;
    boolean bool = (float)mLastTime < 0.0F;
    float f = 0.0F;
    if (bool) {
      f = (float)l2 / 1000.0F;
    }
    mLastTime = l1;
    return Math.min(0.021F, f);
  }
}


/* Location:              /home/andrew/works/KotSharedPreferences/a/classes_dex2jar.jar!/no/agens/agtween/FrameRateCounter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */