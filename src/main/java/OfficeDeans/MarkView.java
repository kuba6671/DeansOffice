package OfficeDeans;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MarkView extends JFrame {
    private JTable markTable;
    private JPanel MarkPanel;
    private JScrollPane ScrollMark;
    private JButton closeButton;

    public MarkView(int indexNumber, Connection con, Statement stmt){
        JFrame mark = this;
        mark.setSize(400, 400);
        mark.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(MarkPanel);

        int i=0,j=0;
        String[] columnNames = {"Przedmiot", "Ocena"};
        String[][] data = new String[20][2];

        try{
            ResultSet examine = stmt.executeQuery("select subject.name, value from mark" +
                    " JOIN subject ON subject.subjectid =mark.subjectid" +
                    " where mark.indexnumber ="+indexNumber);
            while(examine.next()){
                data[i][j] = examine.getString("name");
                j++;
                data[i][j] = examine.getString("value");
                i++;
                j=0;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        markTable = new JTable(data,columnNames);
        ScrollMark = new JScrollPane(markTable);
        mark.add(ScrollMark);

        markTable.setDefaultEditor(Object.class, null);

        closeButton.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mark.dispose();
            }
        }));

        markTable.setVisible(true);
        mark.setVisible(true);
    }
}
