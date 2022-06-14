package OfficeDeans;

import com.github.lgooddatepicker.components.TimePicker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class addLessonToTimetable extends JFrame implements ActionListener {
    private JPanel addLessonPanel;
    private JPanel addLessonForm;
    private JPanel buttonPanel;
    private JComboBox groupComboBox;
    private JComboBox weekdayComboBox;
    private TimePicker hourTimePicker;
    private JButton addButton;
    private JButton resetButton;
    private JButton closeButton;
    private JComboBox teacherComboBox;
    private JComboBox subjectComboBox;
    Connection con;
    Statement stmt;

    public addLessonToTimetable(Connection con, Statement stmt){
        JFrame lesson = this;
        lesson.setSize(400, 400);
        lesson.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(addLessonPanel);
        String teacherFullName = null;

        this.con = con;
        this.stmt = stmt;

        resetButton.addActionListener(this);
        addButton.addActionListener(this);
        closeButton.addActionListener(this);

        String[] weekdaysName = {"Poniedzialek", "Wtorek", "Sroda", "Czwartek", "Piatek", "Sobota", "Niedziela"};

        for(int i=0;i<weekdaysName.length;i++){
            weekdayComboBox.addItem(weekdaysName[i]);
        }

        try {
            ResultSet groups = stmt.executeQuery("SELECT * from StudentGroup");
            while(groups.next()) {
                groupComboBox.addItem(groups.getString("name"));
            }
            ResultSet teachers = stmt.executeQuery("SELECT * from Teacher");
            while(teachers.next()) {
                teacherFullName = teachers.getString("name") +" "+ teachers.getString("surname");
                teacherFullName = teacherFullName.replaceAll("\\s+"," ");
                teacherComboBox.addItem(teacherFullName);
            }
            ResultSet subjects = stmt.executeQuery("SELECT * from Subject");
            while(subjects.next()) {
                subjectComboBox.addItem(subjects.getString("name"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        closeButton.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lesson.dispose();
            }
        }));

        lesson.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String subjectName = (String) subjectComboBox.getSelectedItem();
        String groupName = null;
        int subjectID=0;
        int groupID=0;
        int timetableID=0;
        int weekdayID=0;
        int teacherID=0;
        String lessonTime = null;
        int count;
        if(e.getSource()==addButton){
            try {
                ResultSet subjects = stmt.executeQuery("SELECT * from subject where name='"+subjectName+"'");

                if(subjects.next()){
                    subjectID = subjects.getInt("subjectID");
                }

                groupName = groupComboBox.getSelectedItem().toString();
                ResultSet groups = stmt.executeQuery("SELECT * from StudentGroup where name='"+groupName+"'");
                if(!groups.next()){
                    count = stmt.executeUpdate("insert into StudentGroup values(group_seq.NEXTVAL,'"+groupName+"')");
                    if(count>0)
                        System.out.println("records inserted succesfully");
                    else
                        System.out.println("records insertion failed");

                    groups = stmt.executeQuery("SELECT * from StudentGroup where name='"+groupName+"'");
                    while(groups.next()) {
                        groupID = groups.getInt("groupID");
                    }
                }
                else{
                    groupID = groups.getInt("groupID");
                }
                ResultSet timetables = stmt.executeQuery("SELECT * from timetable where groupID="+groupID);
                if(!timetables.next()){
                    count = stmt.executeUpdate("insert into timetable values(timetable_seq.NEXTVAL,'"+groupID+"')");
                    if(count>0)
                        System.out.println("records inserted succesfully");
                    else
                        System.out.println("records insertion failed");

                    timetables = stmt.executeQuery("SELECT * from timetable where groupID="+groupID);
                    while(timetables.next()) {
                        timetableID = timetables.getInt("timetableID");
                    }
                }
                else{
                    timetableID = timetables.getInt("timetableID");
                }

                String weekdayName = weekdayComboBox.getSelectedItem().toString();

                ResultSet weekdays = stmt.executeQuery("SELECT weekdayID from weekday where name='" +weekdayName+"'");
                if(!weekdays.next()) {
                    System.out.println("Error: Zero rows");
                }else{
                    weekdayID = weekdays.getInt("weekdayID");
                }

                lessonTime = hourTimePicker.getText();


                char[] myNameChars = lessonTime.toCharArray();
                for(int i=0;i<myNameChars.length;i++){
                    if(myNameChars[i]==':')
                        myNameChars[i]=',';
                }
                String myLessonTime = String.valueOf(myNameChars);

                String fullName = teacherComboBox.getSelectedItem().toString();
                String[] splitFullName = fullName.split(" "); // split line on string separated by " "
                ResultSet teachers = stmt.executeQuery("SELECT * from teacher where name='"
                        +splitFullName[0]+"' AND surname='"+splitFullName[1]+"'");
                while(teachers.next()){
                    teacherID = teachers.getInt("teacherID");
                }
                Lesson lesson = new Lesson(myLessonTime,timetableID,weekdayID,subjectID,teacherID);
                String sql = "INSERT INTO LESSON VALUES( lesson_seq.NEXTVAL,?,?,?,?,?)";
                PreparedStatement prepStmt = con.prepareStatement(sql);
                prepStmt.setString(1,lesson.getLessonTime());
                prepStmt.setInt(2,lesson.getTimetableID());
                prepStmt.setInt(3,lesson.getWeekdayID());
                prepStmt.setInt(4,lesson.getSubjectID());
                prepStmt.setInt(5,lesson.getTeacherID());
                count = prepStmt.executeUpdate();
                if(count>0)
                    System.out.println("records inserted succesfully");
                else
                    System.out.println("records insertion failed");

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
