package OfficeDeans;

import OfficeDeans.packageExam.Colloquium;
import OfficeDeans.packageExam.Exam;
import OfficeDeans.packageExam.FinalTest;
import OfficeDeans.packageExam.ProjectDefense;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class addExam extends JFrame implements ActionListener {
    private JPanel addExamPanel;
    private JPanel examForm;
    private JPanel DatePanel;
    private JPanel DateTextPanel;
    private JComboBox typeComboBox;
    private JPanel buttonPanel;
    private JButton submitButton;
    private JButton exitButton;
    private JComboBox groupComboBox;
    private JComboBox subjectComboBox;
    JFrame exam;
    DatePicker datePicker1;
    TimePicker timePicker1;
    Statement stmt;
    Connection con;
    int teacherID;

    public addExam(int teacherID,Connection con, Statement stmt){
        exam = this;
        exam.setSize(400, 300);
        exam.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setContentPane(addExamPanel);
        this.teacherID = teacherID;

        this.stmt = stmt;
        this.con = con;

        typeComboBox.addItem("Egzamin");
        typeComboBox.addItem("Kolokwium");
        typeComboBox.addItem("Obrona projektu");

        submitButton.addActionListener(this);
        exitButton.addActionListener(this);
        typeComboBox.addActionListener(this);


        JPanel myDatePanel = DatePanel;
        myDatePanel.setSize(400,40);

        datePicker1 = new DatePicker();
        datePicker1.setSize(100,30);
        myDatePanel.add(datePicker1);

        timePicker1 = new TimePicker();
        timePicker1.setSize(80,30);
        myDatePanel.add(timePicker1);

        try {
            ResultSet subjects = stmt.executeQuery("SELECT * from Subject");
            while (subjects.next()) {
                subjectComboBox.addItem(subjects.getString("name"));
            }

            ResultSet groups = stmt.executeQuery("SELECT * from StudentGroup");
            while (groups.next()) {
                groupComboBox.addItem(groups.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        exam.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        LocalDateTime date = null;
        String groupName;
        String subject;
        String dateFormString = null;
        int subjectID=0;
        int count=0;
        int groupID=0;
        if(e.getSource()==exitButton) {
            exam.dispose();
        }
        else if (e.getSource() == submitButton) {
            if(!this.datePicker1.getText().isEmpty()) {
                dateFormString = this.datePicker1.getDate().toString() +" "+ this.timePicker1.getText();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm",Locale.ENGLISH);
                date = LocalDateTime.parse(dateFormString, formatter);
            }
                groupName = (String) groupComboBox.getSelectedItem();
                ResultSet rs = null;
                try {
                    rs = stmt.executeQuery("select * from studentGroup where name ='" + groupName+"'");
                    if(rs.next()){
                        groupID = rs.getInt("groupID");
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            subject = (String) subjectComboBox.getSelectedItem();
                try {
                    rs = stmt.executeQuery("select * from subject where name = '" + subject+"'");
                    if(rs.next()){
                            subjectID = rs.getInt("subjectID");
                        }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            String selectedType = (String) typeComboBox.getSelectedItem();
            Exam exam = null;
            if(selectedType == "Egzamin"){
                FinalTest tmpObject = new FinalTest(date,groupID,teacherID,subjectID);
                exam = tmpObject.clone();
            }
            else if(selectedType == "Kolokwium"){
                Colloquium tmpObject = new Colloquium(date,groupID,teacherID,subjectID);
                exam = tmpObject.clone();
            }
            else if(selectedType == "Obrona projektu"){
                ProjectDefense tmpObject = new ProjectDefense(date,groupID,teacherID,subjectID);
                exam = tmpObject.clone();
            }
            exam.setType();

            try {
                String sql = "INSERT INTO EXAM (EXAMID, examdate, GROUPID, TEACHERID, SUBJECTID, type) " +
                        "VALUES (exam_seq.NEXTVAL,TO_DATE(?,'YYYY-MM-DD HH24:MI')," +
                        "?,?,?,?)";
                PreparedStatement prepStmt = con.prepareStatement(sql);
                prepStmt.setString(1,dateFormString);
                prepStmt.setInt(2,exam.getGroupID());
                prepStmt.setInt(3,exam.getTeacherID());
                prepStmt.setInt(4,exam.getSubjectID());
                prepStmt.setString(5,exam.getType());
                count = prepStmt.executeUpdate();
                if(count>0)
                    System.out.println("records exam inserted succesfully");
                else
                    System.out.println("records insertion failed");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}

