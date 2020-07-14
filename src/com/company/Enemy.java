package com.company;
import edu.princeton.cs.introcs.StdAudio;
import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdRandom;

import java.io.IOException;

public class Enemy {
    private double x;
    private double y;
    private double speed;
    private int state;
    private double enemyBulletX;
    private double enemyBulletY;

    public Enemy(double x, double y, double speed, int state,double enemy_bullet_y)
    {
        this.state = state;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.enemyBulletY = enemy_bullet_y;
    }
    public void hit()
    {
        //activates circle so ready for explosion
        if (this.speed != 0.02)
        {
            this.state = 1;
        }
        //removes bullets
        if (this.speed == 0.02)
        {
            this.x = -100;
        }
    }
    public static void enableEnemyMovement(Enemy[] enemies) throws IOException {
        int leftenemy = 0;
        int rightenemy = 4;
        int infinite_prevention = 0;

        // not go out of frame
        while(enemies[leftenemy].x < -1)
        {
            leftenemy = leftenemy + 5;
            infinite_prevention++;
            if(infinite_prevention == 3)
            {
                leftenemy = leftenemy  - 14;
                infinite_prevention = 0;
            }
            if (leftenemy == 15) {

            }
        }
        infinite_prevention = 0;

        while (enemies[rightenemy].x < -1)
        {
            rightenemy = rightenemy + 5;
            infinite_prevention += 1;
            if (infinite_prevention == 3)
            {
                infinite_prevention = 0;
                rightenemy = rightenemy - 16;
            }

        }
        //change direction
        if(enemies[rightenemy].x > 0.92)
        {
            for(int i = 0; i < 15; i++) {
                enemies[i].y = enemies[i].y - 0.05;
                enemies[i].speed = (-1) * enemies[i].speed;
            }
        }
        if(enemies[leftenemy].x < 0)
        {
            for(int j =0; j < 15; j++)
            {
                enemies[j].y = enemies[j].y -0.05;
                enemies[j].speed = (-1) * enemies[j].speed;
            }
        }
    }
    public static void drawEnemyCurrentState(Enemy[] enemies, float speedincrease)
    {
        String[] explosion_animations = {"images/explosioninitial.png","images/explosioninitial2.png", "images/explosionintermediate.png","images/explosionintermediate2.png", "images/explosionmax.png","images/explosionmax2.png", "images/explosionend.png"};

        for(int k = 0; k < 15; k++)
        {
            //draw as ciccle if not hit yet
            if(enemies[k].state == 0)
            {
                if (speedincrease < 0.0011)
                StdDraw.picture(enemies[k].x + 0.04, enemies[k].y - 0.04, "images/enemy.png");
                else
                {
                    StdDraw.picture(enemies[k].x + 0.04, enemies[k].y - 0.04, "images/shootingEnemy.png");
                }
                enemies[k].x = enemies[k].x + enemies[k].speed;
            }
            // draw as explosion if hit
            if (enemies[k].state > 0 && enemies[k].state < 7)
            {
                int number = enemies[k].state;
                StdDraw.picture(enemies[k].x, enemies[k].y, explosion_animations[number]);
                StdDraw.picture(enemies[k].enemyBulletX, enemies[k].enemyBulletY, explosion_animations[number]);
                enemies[k].state++;
            }
            //after explosion animation takes away enemies out of screen
            if(enemies[k].state == 7)
            {
                increaseEniemiesSpeed(enemies, speedincrease);
                enemies[k].x = -100;
                enemies[k].y = 1;
                enemies[k].speed = 0;
                enemies[k].state = 30;
                enemies[k].enemyBulletY = 1;
                enemies[k].enemyBulletX = -100;
            }
        }
    }
    public static void increaseEniemiesSpeed(Enemy[] enemies, float speedincrease)
    {
        System.out.println(speedincrease);
        for (Enemy value : enemies)
            if (value.state != 20) {
                if (value.speed > 0)
                    value.speed = value.speed + speedincrease;
                if (value.speed < 0)
                    value.speed = value.speed - speedincrease;
            }
    }
    public static void giveEnemyValues(Enemy[] enemies, float x)
    {
        for(int i =0; i < 3;i++)
        {
            for(int j = 0; j < 5 ; j++)
            {
                enemies[5*i + j] = new Enemy(0 + j*0.15, 0.9 - i*0.1, 0.002 + x,0, -1);
            }
        }
    }
    public static double getLowestEnemyY(Enemy[] enemies)
    {
        double min = 1.0;
        for(int i = 0; i < 15; i++)
        {
            if(enemies[i].y < min)
                min = enemies[i].y;
        }
        return min;

    }
    public static void enableEnemyShooting(Enemy [] enemies, long[] delay)
    {
        long sec = System.currentTimeMillis() / 2000;
        boolean access = false;
        if(sec > delay[1])
        {
            access = true;
        }

        for (int i = 0; i < 15; i++)
        {
            if(enemies[i].y == getLowestEnemyY(enemies))
            {
                if (StdRandom.uniform() > 0.8 && access)
                {
                    if(enemies[i].state == 0) {
                        delay[1] = sec;
                        enemies[i].setenemy_bullet_x();
                        enemies[i].setenemy_bullet_y(0);
                    }
                }
            }
        }
        for (int k = 0; k < 15; k++)
        {
            if (enemies[k].state == 0)
            {
                enemies[k].drawEnemyMissles();
            }
        }
    }
    public void drawEnemyMissles()
    {
        this.enemyBulletY = this.enemyBulletY - 0.01;

        //draws missile
        StdDraw.picture(this.enemyBulletX, this.enemyBulletY, "images/Enhancedbullet.png");

    }
    public static void observePlayerHit(Enemy[] enemies, double playerX, int[] timesPlayerHIT)
    {
        if(timesPlayerHIT[0] != 2) {
            for (int i = 0; i < 15; i++) {
                if (enemies[i].enemyBulletY < 0.15 && enemies[i].enemyBulletY > 0) {
                    if ((enemies[i].enemyBulletX < (playerX + 0.05)) && (enemies[i].enemyBulletX > (playerX - 0.05))) {
                        StdAudio.play("C:\\Users\\User\\IdeaProjects\\OOP-GAME-COMPSCI 214\\res\\explosion.wav");
                        enemies[i].enemyBulletY = 1;
                        enemies[i].enemyBulletX = -100;
                        System.out.println(timesPlayerHIT[0]);
                        timesPlayerHIT[0]++;
                    }
                }
            }
        }
    }
    public static void observeBunkerHit(Enemy[] enemies, double bunkerX, int[] timesBunkerHit)
    {
        if (timesBunkerHit[0] < 3)
            for(int i = 0; i < 15; i++)
            {
                if (enemies[i].enemyBulletY < 0.3 && enemies[i].enemyBulletY > 0)
                {
                    if ((enemies[i].enemyBulletX < (bunkerX + 0.14)) && (enemies[i].enemyBulletX > (bunkerX - 0.14)))
                    {
                        StdAudio.play("C:\\Users\\User\\IdeaProjects\\OOP-GAME-COMPSCI 214\\res\\explosion.wav");
                        enemies[i].enemyBulletY = 1;
                        enemies[i].enemyBulletX = -100;
                        timesBunkerHit[0]++;
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
    public double getSpeed()
    {
        return this.speed;
    }
    public double getstate()
    {
        return this.state;
    }
    public void setenemy_bullet_x()
    {
        this.enemyBulletX = getX();
    }
    public void setenemy_bullet_y(double change)
    {
        this.enemyBulletY = getY() - 0.05 - change;
    }
}