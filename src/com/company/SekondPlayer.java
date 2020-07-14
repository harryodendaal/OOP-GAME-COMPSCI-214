package com.company;

import edu.princeton.cs.introcs.StdDraw;

public class SekondPlayer extends PlayerModel
{
    public SekondPlayer(double x, double speed, int lives, double angle) {
        super(x, speed, lives, angle);
    }

    public  void enableControlledMovement(int[] timesPlayer2Hit) {
        if (this.getLives() > 0)
        {
            if (timesPlayer2Hit[0] < 2) {
                //moves left
                if (StdDraw.isKeyPressed(77)) {
                    this.setX(this.getSpeed());
                }
                //moves right
                if (StdDraw.isKeyPressed(66)) {
                    this.setX((-1 * this.getSpeed()));
                }
                //stops from going out of frame on the right
                if (this.getX() > (1.0 - 0.08)) {
                    this.setX(0.92);
                }
                //stops from going out of frame on the left
                if (this.getX() < (0 + 0.08)) {
                    this.setX(0.08);
                }
                if (StdDraw.isKeyPressed(75))
                    if (this.getAngle() > -90) {
                        this.setAngle(-3);
                    }
                if (StdDraw.isKeyPressed(74))
                    if (this.getAngle() < 90) {
                        this.setAngle(3);
                    }
                StdDraw.picture(this.getX(), 0.06, "images/defender.png", this.getAngle());
            } else
                setX(1000);
        }
    }
}
