package com.company;
import edu.princeton.cs.introcs.StdAudio;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

public class InvaderState
{
    private final double x;
    private final double y;

    public InvaderState(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    public static void createTitleScreen() throws IOException {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledSquare(0,0,1);

        Font font = new Font("Arial", Font.BOLD, 80);
        StdDraw.setFont(font);
        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.text(0.5,0.9," SPACE ");
        StdDraw.text(0.5,0.75, "DEFENDER");

        StdDraw.setPenColor(Color.WHITE);
        Font font2 = new Font("Arcade", Font.BOLD, 20);
        StdDraw.setFont(font2);
        StdDraw.text(0.5,0.6, "SPACE TO PLAY");
        StdDraw.rectangle(0.25 , 0.45, 0.25, 0.1);
        StdDraw.text(0.1,0.52 , "Player1:");
        StdDraw.text(0.25,0.46, "Pivoting: Left(W), Right(E)");
        StdDraw.text(0.23,0.39, "Move: Left(A), Right(D)");
        StdDraw.rectangle(0.75, 0.45, 0.25, 0.1);
        StdDraw.text(0.6,0.52 , "Player2:");
        StdDraw.text(0.75,0.46, "Pivoting: Left(J), Right(K)");
        StdDraw.text(0.73,0.39, "Move: Left(B), Right(M)");
        StdDraw.text(0.5,0.28, "Quit(q)");
        String username = DatabaseConnection.extractPlayername();
        StdDraw.text(0.5,0.2, "WELCOME: " + username);

        Font font3 = new Font("Arcade", Font.BOLD, 20);
        StdDraw.setFont(font3);
        //StdAudio.loop("C:\\Users\\User\\IdeaProjects\\OOP-GAME-COMPSCI 214\\res\\merry christmas.wav");

        while(!StdDraw.isKeyPressed(32))
        {
            if (StdDraw.isKeyPressed(81))
            {
                System.exit(0);
            }
        }
        StdDraw.pause(500);
    }
    public static void createEndScreen() throws IOException, InterruptedException {

        String online_offline = DatabaseConnection.extractOnlineOrOffline();
        StdDraw.setPenColor(Color.black);
        StdDraw.filledSquare(0.5,0.5,0.5);
        Font font = new Font("Arcade", Font.BOLD, 20);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.WHITE);

        StdDraw.text(0.5,0.7, "You have fought valeantly but");
        StdDraw.text(0.5,0.6, "It is the end now...");


        int Highscore = DatabaseConnection.extractPlayerHighscore();
        String Playername = DatabaseConnection.extractPlayername();

        if(online_offline.equals("DB"))
        {
            StdDraw.text(0.5,0.5, "This might take a few sekonds");
            StdDraw.text(0.5,0.4, "Please be patient : ]");
            DatabaseConnection.insertToDatabase(Highscore, Playername);
            DatabaseConnection.readFromDatabaseToText();
            DatabaseConnection.retrieveOnlineDBHighscores();
        }
        else
        {
            DatabaseConnection.insertIntoLocalDB(Highscore, Playername);
            DatabaseConnection.retrieveLocalHighscores();
        }

        System.exit(0);
    }
    public static void createNextlevel(String condition, int[] level, PlayerModel player, PlayerModel player2, InvaderState bunker1,
                                       InvaderState bunker2, InvaderState bunker3) throws IOException, InterruptedException {

        InvaderState score = new InvaderState(0.1, 0.95);
        InvaderState heartLivesPlayer1 = new InvaderState(0.65, 0.95);
        InvaderState heartLivesPlayer2 = new InvaderState(0.3, 0.95);
        Missile[] missiles = new Missile[20];
        Enemy[] enemies = new Enemy[15];

        //giving the missle objects values
        Missile.giveMissileValues(missiles);

        StdDraw.setPenColor(Color.black);
        StdDraw.filledSquare(0.5, 0.5, 0.5);
        Font font = new Font("Arcade", Font.BOLD, 20);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.WHITE);

        if (condition.equals("Victory"))
        {
            level[0]++;
            if (DatabaseConnection.extractMultiOrSingle().equals("2"))
                StdDraw.text(0.5,0.4,"Player2 still posses: " + player2.getLives() + " lives" );
            StdDraw.text(0.5, 0.5, "Congratulations on surpassing level: " + level[0]);
            StdDraw.text(0.5, 0.35, "Player still posses: " + player.getLives() + " lives");
            StdDraw.text(0.5, 0.2, "The next level will proceed in three sekonds");
            StdDraw.show();
            Enemy.giveEnemyValues(enemies, (float) 0.0005 * level[0]);
            StdDraw.pause(3000);
            StdDraw.enableDoubleBuffering();
            condition = InvaderState.startGameloop(score, player, player2, missiles, enemies, heartLivesPlayer1,
                                                    heartLivesPlayer2, level, bunker1, bunker2, bunker3);
            StdDraw.disableDoubleBuffering();
            InvaderState.createNextlevel(condition, level, player, player2, bunker1, bunker2, bunker3);
        }
        else if (condition.equals("Defeat") && (player.getLives() > 0 || player2.getLives() > 0))
        {
            if(DatabaseConnection.extractMultiOrSingle().equals("2"))
                StdDraw.text(0.5, 0.4,"Player2 still posses: " + player2.getLives() + " lives");

            StdDraw.text(0.5, 0.5, "You have perished at level: " + level[0]);
            StdDraw.text(0.5, 0.35, "Player still posses: " + player.getLives() + " lives");
            StdDraw.text(0.5, 0.2, "Your next chance will start in three sekonds");
            StdDraw.show();
            Enemy.giveEnemyValues(enemies, (float) 0.0005 * level[0]);
            StdDraw.pause(3000);
            StdDraw.enableDoubleBuffering();
            condition = InvaderState.startGameloop(score, player, player2, missiles, enemies, heartLivesPlayer1,
                    heartLivesPlayer2, level, bunker1, bunker2, bunker3);
            StdDraw.disableDoubleBuffering();
            InvaderState.createNextlevel(condition, level, player, player2, bunker1, bunker2, bunker3);
        }
        else
        {
            InvaderState.createEndScreen();
        }
    }

    public static void createHighscore(String[] topPlayers, String[] topScores) throws IOException, InterruptedException {
        StdDraw.setPenColor(StdDraw.BLACK);
        Font font = new Font("Arcade", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.filledSquare(0.5,0.5,1);

        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(0.5,0.9, "HIGHSCORE       PLAYERNAME");
        for(int i = 0; i < 5; i++)
        {
            if(topPlayers[i] == null) {topPlayers[i] = "";}
            if(topScores[i] == null) {topScores[i] = "";}
            StdDraw.text(0.33, 0.8 - (i/10.0), topScores[i]);
            StdDraw.text(0.70, 0.8 - (i/10.0),topPlayers[i]);
        }
        while(!StdDraw.isKeyPressed(32)){}
        if(StdDraw.isKeyPressed(81)){System.exit(0);}
        StdDraw.pause(500);
        Invaders.restart();
    }

    public void createScore(int dead_enemies)
    {
        //prints the value a which
        Font font = new Font("Arcade", Font.BOLD, 25);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(this.x,this.y, " SCORE:" + dead_enemies);
    }
    public void createBunkers(int[] timesBunkerHit)
    {
        if(timesBunkerHit[0] < 3)
            StdDraw.picture(this.x, this.y, "C:\\Users\\User\\IdeaProjects\\OOP-GAME-COMPSCI 214\\images\\bunker" + timesBunkerHit[0] + ".png");
    }
    public void createHearts(int lives, int[] timesPlayerHit)
    {

        if(timesPlayerHit[0] == 0)
        {
            for(int i = 0; i < lives; i++)
                StdDraw.picture(this.x + (i)*0.12, this.y, "C:\\Users\\User\\IdeaProjects\\OOP-GAME-COMPSCI 214\\images\\Heart.png");
        }
        if(timesPlayerHit[0] == 1)
        {
            for(int i = 0; i < lives-1; i++)
                StdDraw.picture(this.x + (i)*0.12, this.y, "C:\\Users\\User\\IdeaProjects\\OOP-GAME-COMPSCI 214\\images\\Heart.png");
            StdDraw.picture(this.x + (lives-1)*0.12, this.y, "C:\\Users\\User\\IdeaProjects\\OOP-GAME-COMPSCI 214\\images\\halfHeart.png");
        }
        if(timesPlayerHit[0] == 2)
        {
            for(int k = 0; k < lives-1; k++)
                StdDraw.picture(this.x + (k)*0.12, this.y, "C:\\Users\\User\\IdeaProjects\\OOP-GAME-COMPSCI 214\\images\\Heart.png");
        }
    }

    public static int countDeadEnemies(Enemy[] enemies)
    {
        int deadEnemies;
        deadEnemies = enemies.length;
        for (Enemy enemy : enemies) {
            if (enemy.getstate() == 0)
                deadEnemies--;
        }

        return deadEnemies;
    }
    public static String startGameloop(InvaderState score, PlayerModel player, PlayerModel player2, Missile[] bullets, Enemy[] enemies,
                                       InvaderState heartLivesPlayer2, InvaderState heartLivesPlayer1, int[] level,
                                       InvaderState bunker1, InvaderState bunker2, InvaderState bunker3) throws IOException {
        long[] sec = new long[3];
        sec[0] = System.currentTimeMillis()/500;
        sec[1] = System.currentTimeMillis()/2000;
        sec[2] = System.currentTimeMillis()/500;
        int deadEnemies = 0;

        double lowestEnemyY = 1.0;

        int[] timesPlayer1Hit = new int[1];
        int[] timesPlayer2Hit = new int[1];
        int[] timesBunker1Hit = new int[1];
        int[] timesBunker2Hit = new int[1];
        int[] timesBunker3Hit = new int[1];

        float speedincrease = (float) (enemies[1].getSpeed()/2.5);

        //if level three vreate ultra enemies
        String MultiOrSingle = DatabaseConnection.extractMultiOrSingle();
        if(!MultiOrSingle.equals("2"))
            {
                timesPlayer2Hit[0] = 2;
                player2.setLives(3);
            }

        while (!StdDraw.isKeyPressed(81) && deadEnemies !=15 &&  lowestEnemyY > 0.05 && (timesPlayer1Hit[0] < 2 || timesPlayer2Hit[0] < 2))
        {
            Enemy.enableEnemyMovement(enemies);

            lowestEnemyY = Enemy.getLowestEnemyY(enemies);
            StdDraw.picture(0.5,0.5,"images/backdrop.png");

            bunker1.createBunkers(timesBunker1Hit);
            bunker2.createBunkers(timesBunker2Hit);
            bunker3.createBunkers(timesBunker3Hit);
            score.createScore(deadEnemies);
            heartLivesPlayer1.createHearts(player.getLives(), timesPlayer1Hit);

            if(MultiOrSingle.equals("2")) {
                heartLivesPlayer2.createHearts(player2.getLives(), timesPlayer2Hit);
                player2.enableControlledMovement(timesPlayer2Hit);
                Missile.enableMissileShooting2(player2.getX(), player2.getAngle(), sec, bullets);
                player2.observePlayerEnemyContact(lowestEnemyY, enemies, timesPlayer2Hit);
                Enemy.observePlayerHit(enemies, player2.getX(), timesPlayer2Hit);

            }

            player.enableControlledMovement(timesPlayer1Hit);
            Enemy.drawEnemyCurrentState(enemies, speedincrease);

            Missile.enableMissileShooting1(player.getX(), player.getAngle(), sec , bullets);
            Missile.observeEnemyHit(bullets, enemies);

            player.observePlayerEnemyContact(lowestEnemyY, enemies, timesPlayer1Hit);
            Enemy.observePlayerHit(enemies, player.getX(), timesPlayer1Hit);


            Enemy.observeBunkerHit(enemies, bunker1.getX(), timesBunker1Hit);
            Enemy.observeBunkerHit(enemies, bunker2.getX(), timesBunker2Hit);
            Enemy.observeBunkerHit(enemies, bunker3.getX(), timesBunker3Hit);


            bunker1.observeBunkerEnemyContact(enemies, timesBunker1Hit);
            bunker2.observeBunkerEnemyContact(enemies, timesBunker2Hit);
            bunker3.observeBunkerEnemyContact(enemies, timesBunker3Hit);


            if(level[0] >-1)
            {
                Enemy.enableEnemyShooting(enemies, sec);
            }

            if (StdDraw.isKeyPressed(81))
            {
                System.exit(0);
            }
            StdDraw.show();
            StdDraw.pause(4);
            deadEnemies = InvaderState.countDeadEnemies(enemies);
            //leavescreen dead both
            if(lowestEnemyY < 0.05)
            {
                timesPlayer1Hit[0] = 2;
                timesPlayer2Hit[0] = 2;
            }
        }

        try {
            FileWriter writer = new FileWriter("C:\\Users\\User\\IdeaProjects\\OOP-GAME-COMPSCI 214\\res\\PlayerScore.txt", true);
            writer.write(String.valueOf(deadEnemies));
            writer.write("\r\n");
            writer.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        if(deadEnemies == 15)
        {
            if(MultiOrSingle.equals("2")) {
                if (timesPlayer1Hit[0] == 2)
                    player.setLives(1);
                if (timesPlayer2Hit[0] == 2)
                    player2.setLives(1);
                return "Victory";
            }
            else
                return "Victory";
        }
        else
            player.setLives(1);
            if(MultiOrSingle.equals("2"))
                player2.setLives(1);
            return "Defeat";
    }
    public void observeBunkerEnemyContact(Enemy[] enemies, int[] timesBunkerhit)
    {
        if(timesBunkerhit[0] < 3) {
            for (int i = 0; i < 15; i++) {
                if (enemies[i].getY() < 0.34) {
                    if ((enemies[i].getX() < (this.getX() + 0.1)) && (enemies[i].getX() > (this.getX() - 0.1))) {
                        StdAudio.play("C:\\Users\\User\\IdeaProjects\\OOP-GAME-COMPSCI 214\\res\\explosion.wav");
                        timesBunkerhit[0]++;
                    }
                }
            }
        }
    }
    public double getX()
    {
        return this.x;
    }
}
