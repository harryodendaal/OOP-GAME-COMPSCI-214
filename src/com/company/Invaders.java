package com.company;
import edu.princeton.cs.introcs.StdDraw;

import java.io.IOException;

// hello sir who is currently is marking our work I might of left to many comments but the intention
//was only that you can understand the work as fast as possible : ]

public class Invaders
{

    public static void main(String[] args) throws IOException, InterruptedException {
        int[] level = new int[1];

        DatabaseConnection.deleteFileContents();
        InvaderState score = new InvaderState(0.1, 0.95);
        InvaderState bunker1 = new InvaderState(0.15, 0.25);
        InvaderState bunker2 = new InvaderState(0.50, 0.25);
        InvaderState bunker3 = new InvaderState(0.85, 0.25);
        InvaderState heartLivesPlayer1 = new InvaderState(0.7, 0.95);
        PlayerModel player = new PlayerModel(0.5,0.02,3, 0);
        Missile[] bullets = new Missile[20];
        Enemy[] enemies = new Enemy[15];

        SekondPlayer player2 =  new SekondPlayer(0.5,0.02,3, 0);
        InvaderState heartLivesPlayer2 = new InvaderState(0.3, 0.95);

        //giving the missle objects values
        Missile.giveMissileValues(bullets);
        //givin the enemy objects values
        Enemy.giveEnemyValues(enemies, 0);

        //get userinput

        UserInput.getClient("Enter 2 For Multiplayer else singleplayer");
        UserInput.getClient("Enter db for global highscore ranking");
        UserInput.getClient("Enter username which shall be displayed at your(s) triump");
        //shows instructions and settings

        InvaderState.createTitleScreen();
        StdDraw.enableDoubleBuffering();
        String condition = InvaderState.startGameloop(score, player, player2, bullets, enemies,heartLivesPlayer1, heartLivesPlayer2, level, bunker1,bunker2,bunker3);
        StdDraw.disableDoubleBuffering();
        InvaderState.createNextlevel(condition, level, player, player2, bunker1,bunker2,bunker3);
    }
    public static void restart() throws IOException, InterruptedException {
        int[] level = new int[1];

        DatabaseConnection.deleteFileContents();
        InvaderState score = new InvaderState(0.1, 0.95);
        InvaderState bunker1 = new InvaderState(0.15, 0.25);
        InvaderState bunker2 = new InvaderState(0.50, 0.25);
        InvaderState bunker3 = new InvaderState(0.85, 0.25);
        InvaderState heartLivesPlayer1 = new InvaderState(0.7, 0.95);
        PlayerModel player = new PlayerModel(0.5,0.02,3, 0);
        Missile[] bullets = new Missile[20];
        Enemy[] enemies = new Enemy[15];

        SekondPlayer player2 =  new SekondPlayer(0.5,0.02,3, 0);
        InvaderState heartLivesPlayer2 = new InvaderState(0.3, 0.95);

        //giving the missle objects values
        Missile.giveMissileValues(bullets);
        //givin the enemy objects values
        Enemy.giveEnemyValues(enemies, 0);

        //get userinput

        UserInput.getClient("Enter 2 For Multiplayer else singleplayer");
        UserInput.getClient("Enter db for global highscore ranking");
        UserInput.getClient("Enter username which shall be displayed at your(s) triump");
        //shows instructions and settings

        InvaderState.createTitleScreen();
        StdDraw.enableDoubleBuffering();
        String condition = InvaderState.startGameloop(score, player, player2, bullets, enemies,heartLivesPlayer1, heartLivesPlayer2, level, bunker1,bunker2,bunker3);
        StdDraw.disableDoubleBuffering();
        InvaderState.createNextlevel(condition, level, player, player2, bunker1,bunker2,bunker3);
    }
}
