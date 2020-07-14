package com.company;
import edu.princeton.cs.introcs.*;

public class PlayerModel {
    private double x;
    private final double speed;
    private int lives;
    private double angle;

    public PlayerModel(double x, double speed, int lives, double angle)
    {
        this.x = x;
        this.speed = speed;
        this.angle = angle;
        this.lives = lives;
    }


    public  void enableControlledMovement(int[] timesPlayer1Hit) {
        if (this.lives > 0)
        {
            if (timesPlayer1Hit[0] < 2) {
                //moves left
                if (StdDraw.isKeyPressed(68)) {
                    this.x = this.x + this.speed;
                }
                //moves right
                if (StdDraw.isKeyPressed(65)) {
                    this.x = this.x - this.speed;
                }
                //stops from going out of frame on the right
                if (this.x > (1.0 - 0.08)) {
                    this.x = 0.92;
                }
                //stops from going out of frame on the left
                if (this.x < (0 + 0.08)) {
                    this.x = 0.08;
                }
                if (StdDraw.isKeyPressed(69))
                    if (this.angle > -90) {
                        this.angle = this.angle - 3;
                    }
                if (StdDraw.isKeyPressed(87))
                    if (this.angle < 90) {
                        this.angle = this.angle + 3;
                    }

                StdDraw.picture(this.x, 0.06, "images/defender.png", this.angle);
            } else
                setX(1000);
        }
    }

    public void observePlayerEnemyContact(double lowestEnemyY, Enemy[] enemies, int[] timesPlayerHIT)
    {
        if(timesPlayerHIT[0] !=2) {
            if (lowestEnemyY < 0.15) {
                for (int i = 0; i < 15; i++) {
                    if (enemies[i].getY() < 0.15) {
                        if ((enemies[i].getX() < (this.getX() + 0.05)) && (enemies[i].getX() > (this.getX() - 0.05))) {
                            StdAudio.play("C:\\Users\\User\\IdeaProjects\\OOP-GAME-COMPSCI 214\\res\\explosion.wav");
                            timesPlayerHIT[0]++;
                            if(timesPlayerHIT[0] == 2) {
                                System.out.println("lostlife due to enemycontact");
                            }
                        }
                    }
                }
            }
        }
    }


    public double getX()
    {
        return this.x;
    }
    public double getAngle()
    {
        return this.angle;
    }
    public int getLives(){return this.lives;}
    public double getSpeed(){return this.speed;}
    public void setAngle(double change) { this.angle = this.angle + change; }
    public void setLives(int change){this.lives = this.lives - change;}
    public void setX(double change)
    {
        if (change == 0.08)
        {
            this.x = change;
        }
        else if(change == 0.92)
        {
            this.x = change;
        }
        else {
            this.x = this.x + change;
        }
    }
}
