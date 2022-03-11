package OfficeDeans;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Statement;

public class TeacherView extends MainWindow {
    JButton button2 = new JButton();
    JButton button3 = new JButton();
    JButton button4 = new JButton();
    JButton logoutButton = new JButton();


    public void openWindow(String user, String name, String surname, Connection con, Statement stmt){
        JPanel panel = new JPanel();
        JFrame frame = new JFrame();
        frame.setSize(400, 240);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);

        String s = name+" "+surname;
        s = s.replaceAll("\\s+"," ");
        panel.setLayout(null);

        userLabel = new JLabel("Panel prowadzącego");
        userLabel.setBounds(140, 10, 130, 25);
        panel.add(userLabel);

        userLabel2 = new JLabel("Prowadzący : " + s);
        userLabel2.setBounds(10, 35, 300, 25);
        panel.add(userLabel2);

        button = new JButton("Wpisz ocenę");
        button.setBounds(40, 70, 150, 25);
        panel.add(button);

        button2 = new JButton("Zaplanuj zaliczenie");
        button2.setBounds(210, 70, 150, 25);
        panel.add(button2);

        button3 = new JButton("Ustal plan zajęć");
        button3.setBounds(40, 110, 150, 25);
        panel.add(button3);


        button4 = new JButton("Sprawdź swój plan");
        button4.setBounds(210, 110, 150, 25);
        panel.add(button4);

        logoutButton = new JButton("Wyloguj");
        logoutButton.setBounds(40,150,320,25);
        panel.add(logoutButton);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddMark addMark = new AddMark(user, con, stmt);
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addExam exam = new addExam(Integer.parseInt(user),con, stmt);
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addLessonToTimetable lesson = new addLessonToTimetable(con, stmt);
            }
        });

        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timetableView timetable = new timetableView(0,Integer.parseInt(user),con,stmt);
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                MainWindow mainwind = new MainWindow();
                JFrame mainWind = new JFrame();
                mainwind.openWindow(mainWind);
            }
        });


        frame.setVisible(true);
    }


}



