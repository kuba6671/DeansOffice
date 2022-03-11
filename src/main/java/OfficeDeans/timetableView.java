package OfficeDeans;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class timetableView extends JFrame {
    private JPanel timetablePanel;
    private JScrollPane scroolTimetable;
    private JTable timetableTable;
    private JButton closeButton;

    public timetableView(int groupID, int teacherID, Connection con, Statement stmt){
        JFrame timetable = this;
        timetable.setSize(500, 400);
        timetable.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(timetablePanel);

        String fullName = null;
        double lessonTimeDouble =0.0;
        String lessonTime = null;
        String subjectName = null;
        String weekdayName = null;
        String[][] data = null;
        String[] columnNames = null;
        String groupName = null;
        int i = 0, j = 0;
        if(teacherID==0) {
            data = new String[20][4];
            columnNames = new String[]{"Przedmiot", "Dzien tygodnia", "Godzina", "Prowadzacy"};
            try {
                String sql = "SELECT subject.name as przedmiot ,weekday.weekdayID as idweek, " +
                        "weekday.name as dzien_tygodnia, " +
                        "lessontime as godzina, teacher.name as imie," +
                        "teacher.surname as nazwisko from lesson " +
                        "JOIN subject ON lesson.subjectid = subject.subjectid " +
                        "JOIN weekday ON weekday.weekdayID = lesson.weekdayID " +
                        "JOIN teacher ON teacher.teacherid = lesson.teacherid " +
                        "JOIN timetable ON timetable.timetableID = lesson.timetableID" +
                        " where timetable.groupID =? ORDER BY idweek, lessontime";
                PreparedStatement prepStmt = con.prepareStatement(sql);
                prepStmt.setString(1,Integer.toString(groupID));
                ResultSet lessons = prepStmt.executeQuery();

                while (lessons.next()) {
                    subjectName = lessons.getString("przedmiot");
                    subjectName = subjectName.replaceAll("\\s+", " ");
                    data[i][j] = subjectName;
                    j++;
                    weekdayName = lessons.getString("dzien_tygodnia");
                    weekdayName = weekdayName.replaceAll("\\s+", " ");
                    data[i][j] = weekdayName;
                    j++;
                    lessonTimeDouble = lessons.getDouble("godzina");
                    lessonTime = Double.toString(lessonTimeDouble);
                    char[] myNameChars = lessonTime.toCharArray();
                    for (int k = 0; k < myNameChars.length; k++) {
                        if (myNameChars[k] == '.') {
                            myNameChars[k] = ':';
                        }
                    }
                    String myLessonTime = String.valueOf(myNameChars);
                    data[i][j] = myLessonTime + "0";
                    j++;
                    fullName = lessons.getString("imie") + " " + lessons.getString("nazwisko");
                    fullName = fullName.replaceAll("\\s+", " ");

                    data[i][j] = fullName;
                    i++;
                    j = 0;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else{
            data = new String[20][4];
            columnNames = new String[]{"Przedmiot", "Dzien tygodnia", "Godzina", "Grupa"};
            try{
                String sql = "SELECT subject.name as przedmiot ,weekday.weekdayID as idweek," +
                        "weekday.name as dzien_tygodnia, " +
                        "lessontime as godzina, studentGroup.name as grupa" +
                        " from lesson " +
                        "JOIN subject ON lesson.subjectid = subject.subjectid " +
                        "JOIN weekday ON weekday.weekdayID = lesson.weekdayID " +
                        "JOIN teacher ON teacher.teacherid = lesson.teacherid " +
                        "JOIN timetable ON timetable.timetableID = lesson.timetableID " +
                        "JOIN StudentGroup ON StudentGroup.groupID = timetable.groupID " +
                        " where lesson.teacherID =? ORDER BY idweek, lessontime";
                PreparedStatement prepStmt = con.prepareStatement(sql);
                prepStmt.setString(1,Integer.toString(teacherID));
                ResultSet lessons = prepStmt.executeQuery();

                while(lessons.next()){
                    subjectName = lessons.getString("przedmiot");
                    subjectName = subjectName.replaceAll("\\s+"," ");
                    data[i][j] = subjectName;
                    j++;
                    weekdayName = lessons.getString("dzien_tygodnia");
                    weekdayName = weekdayName.replaceAll("\\s+"," ");
                    data[i][j] = weekdayName;
                    j++;
                    lessonTimeDouble = lessons.getDouble("godzina");
                    lessonTime = Double.toString(lessonTimeDouble);
                    char[] myNameChars = lessonTime.toCharArray();
                    for(int k=0;k<myNameChars.length;k++){
                        if(myNameChars[k]=='.') {
                            myNameChars[k] = ':';
                        }
                    }
                    String myLessonTime = String.valueOf(myNameChars);
                    data[i][j] = myLessonTime+"0";
                    j++;
                    groupName = lessons.getString("grupa");
                    groupName = groupName.replaceAll("\\s+"," ");
                    data[i][j] = groupName;
                    i++;
                    j=0;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        timetableTable = new JTable(data,columnNames);
        scroolTimetable = new JScrollPane(timetableTable);
        timetable.add(scroolTimetable);

        timetableTable.setDefaultEditor(Object.class, null);

        closeButton.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timetable.dispose();
            }
        }));

        timetableTable.setVisible(true);
        timetable.setVisible(true);



    }
}
