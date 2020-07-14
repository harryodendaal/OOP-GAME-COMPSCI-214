package com.company;
import edu.princeton.cs.introcs.StdAudio;
import edu.princeton.cs.introcs.StdDraw;

public class Missile {
    private double x;
    private double y;
    private final double speed;
    private double speedX;
    private double speedY;
    private int state;

    public Missile(double x, double y, double speed, double speedX, double speed_y, int state)
    {
        this.state = state;
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speed_y;
        this.speed = speed;
    }
    public void observeMissileHits()
    {
        //activates circle so ready for explosion
        if ((this.speed >= -0.002) && (this.speed <= 0.002))
        {
            this.state = 1;
        }
        //removes bullets
        if (this.speed == 0.02)
        {
            this.state = 0;
            this.x = -100;
            this.y = -0.1;
            this.speedY = 0;
            this.speedX = 0;
        }
    }
    public void drawMissles(double alpha, int k)
    {
        // takes the angle to radians as it was in degrees... I think
        alpha = (alpha * (Math.PI)) / 180;
        //If the missile objects leave the screen which also happens when it hits and enemy
        // it is reinitialized and removed from sight sou that it is ready to be shot again
        if (this.y > 1 || this.y < 0 || this.x > 1 || this.x < 0)
        {
            this.state = 0;
            this.y = -0.1;
            this.speedX = 0;
            this.speedY = 0;
            return;
        }
        //Calculate angle at which bullet will move and places the bullets at the correct position
        if((this.speedX == 0) && (this.speedY == 0))
        {
            this.speedY = this.speed * Math.cos(alpha);
            this.speedX = this.speed * Math.sin(alpha);

            this.y = this.y/2 +  (42.0/520.0)* Math.cos(alpha);
            this.x =this.x + (42.0/540.0)* Math.sin(alpha);
        }
        this.y = this.y + this.speedY;
        this.x = this.x + this.speedX;

        //draws missile
        StdDraw.picture(this.x, this.y, "images/Defaultbullet.png");

    }
    public static void enableMissileShooting1(double player_x, double player_angle, long[] delay, Missile[] bullets)
    {
        // enables recharge time between missles will be replaced with time in due time
        long sec = System.currentTimeMillis() / 500;
        boolean access = false;
        if(sec > delay[0])
        {
            access = true;
        }
        // when shoot button pressed and delay over this function
        // "activates" the bullet so that it is ready to be shot
        if(StdDraw.isKeyPressed(32) && access)
        {
            StdAudio.play("C:\\Users\\User\\IdeaProjects\\OOP-GAME-COMPSCI 214\\res\\shoot.wav");
            int i;
            do {
                i = (int) (10 * Math.random());
            }while (bullets[i].state == 1);//loop continues until a bullet is selected that is not currently being shot
            delay[0] = sec;
            //readies the bullet to be shot
            bullets[i].state = 1;
            bullets[i].x = player_x;
            bullets[i].y = 0.15;
        }
        //goes through the Missile objects and if acticated by the block of code above it
        //will be sent through to be drawn
        for (int k = 0; k < 10; k++)
        {
            if (bullets[k].state == 1)
            {
                bullets[k].drawMissles((-player_angle),k);
            }
        }
    }
    public static void enableMissileShooting2(double player_x, double player_angle, long[] delay, Missile[] bullets)
    {
        // enables recharge time between missles will be replaced with time in due time
        long sec = System.currentTimeMillis() / 500;
        boolean access = false;
        if(sec > delay[2])
        {
            access = true;
        }
        // when shoot button pressed and delay over this function
        // "activates" the bullet so that it is ready to be shot

        if(StdDraw.isKeyPressed(32) && access)
        {
            StdAudio.play("C:\\Users\\User\\IdeaProjects\\OOP-GAME-COMPSCI 214\\res\\shoot.wav");
            int i;
            do {
                i = (int) (10 * Math.random());
            }while (bullets[i].state == 1);//loop continues until a bullet is selected that is not currently being shot
            delay[2] = sec;
            //readies the bullet to be shot
            bullets[i].state = 1;
            bullets[i].x = player_x;
            bullets[i].y = 0.15;
        }
        //goes through the Missile objects and if acticated by the block of code above it
        //will be sent through to be drawn
        for (int k = 0; k < 10; k++)
        {
            if (bullets[k].state == 1)
            {
                bullets[k].drawMissles((-player_angle),k);
            }
        }
    }
    public static void giveMissileValues(Missile[] missiles)
    {
        for(int i = 0; i < missiles.length; i++)
        {
            missiles[i] = new Missile(0.5,-0.1, 0.02, 0,0, 0);
        }
    }
    public static void observeEnemyHit(Missile[] missiles, Enemy [] enemies)
    {
        for(int k = 0; k < 15; k++)
        {
            for(int j = 0; j < 10; j++)
            {
                //lessons strain on function by only looping through missiles currently being shot
                if(missiles[j].getState() == 1)
                {
                    if ((missiles[j].getY() > enemies[k].getY()- 0.05) && (missiles[j].getY()  < enemies[k].getY() + 0.05) && (missiles[j].getX() < enemies[k].getX() + 0.05) && (missiles[j].getX() > enemies[k].getX() - 0.05)) {
                        enemies[k].hit();
                        missiles[j].observeMissileHits();
                        StdAudio.play("C:\\Users\\User\\IdeaProjects\\OOP-GAME-COMPSCI 214\\res\\explosion.wav");
                    }
                }
            }
        }
    }

    public double getX()
    {
        return this.x;
    }
    public double getY()
    {
        return this.y;
    }
    public double getState()
    {
        return this.state;
    }


}
