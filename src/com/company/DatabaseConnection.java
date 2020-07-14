package com.company;
import java.io.*;
import java.sql.*;


public class DatabaseConnection
{

    public static void retrieveOnlineDBHighscores() throws IOException, InterruptedException {

        String[] fileContent = new String[500];
        String[] topPlayers = new String[5];
        String[] topScores = new String[5];


        try {

            BufferedReader bufferreader = new BufferedReader(new FileReader("C:\\Users\\User\\IdeaProjects\\OOP-GAME-COMPSCI 214\\res\\UniverseHighScore.txt"));
            int fileLength = 0;

            //read text file into string array
            while ((fileContent[fileLength] = bufferreader.readLine()) != null) {
                fileLength = fileLength + 1;
            }

            //read scores of players  into scorevalues array
            int[] scoreValues = new int[fileLength/2];
            for(int i = 0; i < fileLength/2; i++)
            {
                assert fileContent[i * 2] != null;
                scoreValues[i] = Integer.parseInt(fileContent[i*2]);
            }
            //create copy of scoreValues array
            int[] copyscoreValues = new int[scoreValues.length];
            System.arraycopy(scoreValues, 0, copyscoreValues, 0, scoreValues.length);

            //sort array
            useBubblesort(copyscoreValues);
            //finds top five scores and their respective players
            int copyCounter = 0;
            for (int i = 0; i < 5; i++)
            {
                for (int t = 0; t < scoreValues.length; t++)
                {
                    if (scoreValues[t] == copyscoreValues[copyCounter])
                    {
                        topPlayers[i] =fileContent[t * 2 + 1];
                        topScores[i] =String.valueOf(scoreValues[t]);
                        t = scoreValues.length;
                    }
                }
                copyCounter = copyCounter + 1;
            }
            bufferreader.close();
        } catch (IOException ex)
        {
            ex.printStackTrace();
            System.out.println("The online database seems to be offline will have to use local db");
            retrieveLocalHighscores();

        }
        InvaderState.createHighscore(topPlayers, topScores);
    }
    public static void retrieveLocalHighscores() throws IOException, InterruptedException {
        String[] fileContent = new String[500];
        String[] topPlayers = new String[5];
        String[] topScores = new String[5];
        try {

            BufferedReader bufferreader = new BufferedReader(new FileReader("C:\\Users\\User\\IdeaProjects\\OOP-GAME-COMPSCI 214\\res\\LocalHighScore.txt"));
            int fileLength = 0;

            //read text file into string array
            while ((fileContent[fileLength] = bufferreader.readLine()) != null) {
                fileLength = fileLength + 1;
            }
            System.out.println(fileLength);
            //read scores of players  into scorevalues array
            int[] scoreValues = new int[fileLength/2];
            for(int i = 0; i < fileLength/2; i++)
            {
                assert fileContent[i * 2] != null;
                scoreValues[i] = Integer.parseInt(fileContent[i*2]);
            }
            //create copy of scoreValues array
            int[] copyscoreValues = new int[scoreValues.length];
            System.arraycopy(scoreValues, 0, copyscoreValues, 0, scoreValues.length);

            //sort array
            useBubblesort(copyscoreValues);
            //finds top five scores and their respective players
            int copyCounter = 0;
            for (int i = 0; i < scoreValues.length; i++)
            {
                for (int t = 0; t < scoreValues.length; t++)
                {
                    if (scoreValues[t] == copyscoreValues[copyCounter])
                    {
                        topPlayers[i] =fileContent[t * 2 + 1];
                        topScores[i] =String.valueOf(scoreValues[t]);
                        t = scoreValues.length;
                    }
                }
                copyCounter = copyCounter + 1;
            }

            bufferreader.close();

        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
        InvaderState.createHighscore(topPlayers,topScores);
    }
    public static void deleteFileContents() throws FileNotFoundException {
        PrintWriter usernametyped = new PrintWriter("C:\\Users\\User\\IdeaProjects\\OOP-GAME-COMPSCI 214\\res\\UsernameTyped.txt");
        usernametyped.print("");
        usernametyped.close();
        PrintWriter playerscore = new PrintWriter("C:\\Users\\User\\IdeaProjects\\OOP-GAME-COMPSCI 214\\res\\PlayerScore.txt");
        playerscore.print("");
        playerscore.close();
        PrintWriter UNVIVERSEHIGHSCORE = new PrintWriter("C:\\Users\\User\\IdeaProjects\\OOP-GAME-COMPSCI 214\\res\\UniverseHighScore.txt");
        UNVIVERSEHIGHSCORE.print("");
        UNVIVERSEHIGHSCORE.close();
        PrintWriter ONLINEORNOT = new PrintWriter("C:\\Users\\User\\IdeaProjects\\OOP-GAME-COMPSCI 214\\res\\Online_Or_Offline.txt");
        ONLINEORNOT.print("");
        ONLINEORNOT.close();
        PrintWriter MULTIORNOT = new PrintWriter("C:\\Users\\User\\IdeaProjects\\OOP-GAME-COMPSCI 214\\res\\MultiorSingle.txt");
        MULTIORNOT.print("");
        MULTIORNOT.close();
    }

    public static void useBubblesort(int[] unsortedArray) {
        boolean sorted = false;
        int temp;
        while(!sorted) {
            sorted = true;
            for (int i = 0; i < unsortedArray.length - 1; i++) {
                if (unsortedArray[i] < unsortedArray[i+1]) {
                    temp = unsortedArray[i];
                    unsortedArray[i] = unsortedArray[i+1];
                    unsortedArray[i+1] = temp;
                    sorted = false;
                }
            }
        }
    }
    public static int extractPlayerHighscore() throws IOException
    {
        int Highscore = 0;
        String [] content = new String [100];
        BufferedReader bufferreader = new BufferedReader(new FileReader("C:\\Users\\User\\IdeaProjects\\OOP-GAME-COMPSCI 214\\res\\PlayerScore.txt"));

        int count = 0;

        //read text file into string
        while ((content[count] = bufferreader.readLine()) != null)
        {
            Highscore = Highscore + Integer.parseInt(content[count]);
            count++;
            System.out.println(Highscore);
        }
        return Highscore;
    }
    public static String extractPlayername() throws IOException {
        String Playername = "";
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\User\\IdeaProjects\\OOP-GAME-COMPSCI 214\\res\\UsernameTyped.txt"));

        String sCurrentLine;
        while ((sCurrentLine = br.readLine()) != null)
        {
            Playername = sCurrentLine;
        }
        return Playername.trim();
    }
    public static String extractMultiOrSingle()throws IOException {
        String MultiOrSingle = "";
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\User\\IdeaProjects\\OOP-GAME-COMPSCI 214\\res\\MultiOrSingle.txt"));

        String sCurrentLine;
        while ((sCurrentLine = br.readLine()) != null)
        {
            MultiOrSingle = sCurrentLine;
        }
        return MultiOrSingle.trim();
    }

    public static String extractOnlineOrOffline() throws IOException {
        String Playername = "";
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\User\\IdeaProjects\\OOP-GAME-COMPSCI 214\\res\\Online_Or_Offline.txt"));

        String sCurrentLine;
        while ((sCurrentLine = br.readLine()) != null)
        {
            Playername = sCurrentLine;
        }
        System.out.println(Playername.trim().toUpperCase());
        return Playername.trim().toUpperCase();

    }
    public static void insertToDatabase(int highscore, String playername)
    {
        String jdbcUrl = "jdbc:mysql://db4free.net:3306/highscore?useSSL=false";
        String username = "harryodendaal";
        String password = "b0fecd4c";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(jdbcUrl, username, password);


            String sql = "INSERT INTO highscore(SCORE,PLAYERNAME)" +
                    "values (?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, highscore);
            pstmt.setString(2, playername);
            pstmt.execute();

        } catch (Exception ex) {
            // handle the error
            System.out.println(ex);
            System.out.println("database currently offline upload to text document");
            insertIntoLocalDB(highscore, playername);
        }
    }
    //where Harry got read from data sort of
    //"https://www.javatpoint.com/example-to-connect-to-the-mysql-database"
    public static void readFromDatabaseToText() throws IOException, InterruptedException {
        String jdbcUrl = "jdbc:mysql://db4free.net:3306/highscore?useSSL=false";
        String username = "harryodendaal";
        String password = "b0fecd4c";

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection(jdbcUrl, username, password);
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from highscore");
            FileWriter writer = new FileWriter("C:\\Users\\User\\IdeaProjects\\OOP-GAME-COMPSCI 214\\res\\UniverseHighScore.txt", true);
            while(rs.next())
            {
                System.out.println(rs.getInt(1) + "  " + rs.getString(2));
                int high = rs.getInt(1);
                String player = rs.getString(2);
                writer.write(String.valueOf(high));
                writer.write("\r\n");   // write new line
                writer.write(player);
                writer.write("\r\n");   // write new line
            }
            writer.close();

        }catch(Exception e)
        {
            System.out.println(e);
            System.out.println("database offline will have to read from local database");
            retrieveLocalHighscores();
        }
    }
    public static void insertIntoLocalDB(int highscore, String playername)
    {
        try {
            FileWriter fileWriter = new FileWriter("C:\\Users\\User\\IdeaProjects\\OOP-GAME-COMPSCI 214\\res\\LocalHighScore.txt", true);
            BufferedWriter bWriter = new BufferedWriter(fileWriter);
            bWriter.write(String.valueOf(highscore));
            bWriter.newLine();
            bWriter.write(playername);
            bWriter.newLine();
            bWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


//Reads text file if database offline?
    /*}
try {
        FileReader reader = new FileReader("C:\\Users\\User\\IdeaProjects\\OOP-GAME-COMPSCI 214\\res\\LocalHighScore.txt");
        int character;

        while ((character = reader.read()) != -1) {
        System.out.print((char) character);
        }
        reader.close();

        } catch (IOException ex)
        {
        ex.printStackTrace();
        }*/
//write to text file if database offline?
            /*try {
        FileWriter writer = new FileWriter("LocalHighScore.txt", true);
        writer.write(Playername);
        writer.write("\r\n");   // write new line
        writer.write(Highscore);
        writer.close();
    } catch (IOException e) {
        e.printStackTrace();
    }*/


    //create table only neede once
   /* private static final String CREATE_TABLE_SQL="CREATE TABLE highscore ("
            + "SCORE INT NOT NULL,"
            + "PLAYERNAME VARCHAR(45) NOT NULL)";

    // https://www.boraji.com/jdbc-create-table-example
 public static void tablecreate()
    {
        String jdbcUrl = "jdbc:mysql://db4free.net:3306/highscore?useSSL=false";
        String username = "harryodendaal";
        String password = "b0fecd4c";

        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(jdbcUrl, username, password);
            stmt = conn.createStatement();

            stmt.executeUpdate(CREATE_TABLE_SQL);

            System.out.println("Table created");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // Close connection
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }*/
/*
import javax.swing.JTextField
        import java.awt.*;
        import java.awt.event.KeyListener;*/
