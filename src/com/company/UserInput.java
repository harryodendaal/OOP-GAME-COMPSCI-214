package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class UserInput
{
    public static void getClient(String file) throws InterruptedException {

        JFrame jFrame = new JFrame(file);
        //submit button
        JButton execute = new JButton("Execute");
        execute.setBounds(150, 50, 120, 20);
        //enter name label
        JLabel label = new JLabel();
        label.setText("Enter");
        label.setBounds(5, 10, 100, 50);
        //empty label which will show event after button clicked

        //textfield to enter name
        JTextField textfield = new JTextField();
        //will give confirmation
        textfield.setBounds(150, 25, 120, 20);
        //add to frame
        jFrame.add(textfield);
        jFrame.add(label);
        jFrame.add(execute);
        jFrame.setSize(550, 150);
        jFrame.setLayout(null);
        jFrame.setVisible(true);
        jFrame.setBackground(Color.RED);
        //Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //action listener
        if (file.equals("Enter username which shall be displayed at your(s) triump")) {
            execute.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0) {
                    String textfieldContent = textfield.getText();
                    try {
                        FileWriter writer = new FileWriter("C:\\Users\\User\\IdeaProjects\\OOP-GAME-COMPSCI 214\\res\\UsernameTyped.txt", true);
                        writer.write(textfieldContent);
                        writer.write("\r\n");   // write new line
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    jFrame.dispose();
                }
            });
        }
        if (file.equals("Enter db for global highscore ranking")) {
            execute.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0) {
                    String textfieldContent = textfield.getText();
                    try {
                        FileWriter writer = new FileWriter("C:\\Users\\User\\IdeaProjects\\OOP-GAME-COMPSCI 214\\res\\Online_Or_Offline.txt", false);
                        writer.write(textfieldContent);
                        writer.write("\r\n");   // write new line
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    jFrame.dispose();
                }
            });
        }
        if (file.equals("Enter 2 For Multiplayer else singleplayer")) {
            execute.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0) {
                    String textfieldContent = textfield.getText();
                    try {
                        FileWriter writer = new FileWriter("C:\\Users\\User\\IdeaProjects\\OOP-GAME-COMPSCI 214\\res\\MultiOrSingle.txt", true);
                        writer.write(textfieldContent);
                        writer.write("\r\n");   // write new line
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    jFrame.dispose();
                }
            });
        }
        if (file.equals("Enter 2 For Multiplayer else singleplayer"))
        {
            File newFile = new File("C:\\Users\\User\\IdeaProjects\\OOP-GAME-COMPSCI 214\\res\\MultiOrSingle.txt");
            while (newFile.length() == 0)
            {
                Thread.sleep(1000);
            }
        }
        if (file.equals("Enter db for global highscore ranking"))
        {
            File newFile = new File("C:\\Users\\User\\IdeaProjects\\OOP-GAME-COMPSCI 214\\res\\Online_Or_Offline.txt");
            while(newFile.length() == 0)
            {
                Thread.sleep(1000);
            }
        }
        if (file.equals("Enter username which shall be displayed at your(s) triump"))
        {
            File newFile = new File("C:\\Users\\User\\IdeaProjects\\OOP-GAME-COMPSCI 214\\res\\UsernameTyped.txt");
            while (newFile.length() == 0)
            {
                Thread.sleep(1000);
            }
        }
    }
}

