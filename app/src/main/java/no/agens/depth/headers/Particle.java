package no.agens.depth.headers;

public class Particle
  extends Renderable
{
  long lastRandomizeChange;
  float randomSpeedX;
  float randomSpeedY;
  
  public Particle(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    super(null, paramFloat1, paramFloat2);
    this.randomSpeedX = paramFloat3;
    this.randomSpeedY = paramFloat4;
    this.lastRandomizeChange = System.currentTimeMillis();
  }
  
  public void setRandomSpeed(float paramFloat1, float paramFloat2)
  {
    this.randomSpeedX = paramFloat1;
    this.randomSpeedY = paramFloat2;
  }
}


/* Location:              /home/andrew/works/KotSharedPreferences/a/classes_dex2jar.jar!/no/agens/depth/headers/Particle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */