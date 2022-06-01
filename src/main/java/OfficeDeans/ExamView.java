package OfficeDeans;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ExamView extends JFrame {
    private JPanel ExamPanel;
    private JTable examTable;
    private JScrollPane ScrollExam;
    private JButton closeButton;

    public ExamView(int groupID, Connection con, Statement stmt){
        JFrame exam = this;
        exam.setSize(400, 400);
        exam.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(ExamPanel);

        int i=0,j=0;
        String[][] data = new String[20][3];
        String[] columnNames = {"Przedmiot", "Data", "Typ"};
        try{
            String sql ="select subject.name, examdate, exam.type from exam " +
                    "JOIN StudentGroup ON StudentGroup.groupID =exam.groupID" +
                    " JOIN subject ON exam.subjectID = subject.subjectID" +
                    " where exam.groupID =? ORDER BY examdate";
            PreparedStatement prepStmt = con.prepareStatement(sql);
            prepStmt.setString(1,Integer.toString(groupID));
            ResultSet examine = prepStmt.executeQuery();
            while(examine.next()){
                data[i][j] = examine.getString("name");
                j++;
                data[i][j] = examine.getString("examdate");
                j++;
                data[i][j]= examine.getString("type");
                i++;
                j=0;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        examTable = new JTable(data,columnNames);
        ScrollExam = new JScrollPane(examTable);
        exam.add(ScrollExam);

        examTable.setDefaultEditor(Object.class, null);

        closeButton.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exam.dispose();
            }
        }));

        examTable.setVisible(true);
        exam.setVisible(true);
    }
}
