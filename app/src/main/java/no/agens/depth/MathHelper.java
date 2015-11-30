package no.agens.depth;

import java.util.Random;

public class MathHelper
{
  public static Random rand = new Random();
  
  public static float randomRange(float paramFloat1, float paramFloat2)
  {
    return rand.nextInt(1 + ((int)paramFloat2 - (int)paramFloat1)) + (int)paramFloat1;
  }
}


/* Location:              /home/andrew/works/KotSharedPreferences/a/classes_dex2jar.jar!/no/agens/depth/MathHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */