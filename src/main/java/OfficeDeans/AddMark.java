package OfficeDeans;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class AddMark extends JFrame{
    private JComboBox StudentChoose;
    private JComboBox GroupChoose;
    private JTable markTable;
    private JPanel MarkPanel;
    private JComboBox FieldChoose;
    private JComboBox SubjectChoose;
    private JComboBox MarkChoose;
    private JButton CloseButton;
    private JButton CommitButton;
    private JScrollPane ScrollMark;

    private int i;

    private String selectedField;
    private String selectedGroup;
    private String selectedStudent;
    private String selectedSubject;
    private String selectedMark;

    private int subjectID;
    private int studentID;

    private final DefaultTableModel model = new DefaultTableModel(0, 6);

    public AddMark(String user, Connection con, Statement stmt) {
        JFrame marks = this;
        marks.setSize(400, 400);
        marks.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(MarkPanel);


        FieldChoose.addItem("Wybierz kierunek");
        GroupChoose.addItem("Wybierz grupę");
        StudentChoose.addItem("Wybierz studenta");
        SubjectChoose.addItem("Wybierz przedmiot");


        fieldCombo(con, stmt);

        FieldChoose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddMark.this.selectedField = FieldChoose.getSelectedItem().toString();
                groupCombo(selectedField, con, stmt);

            }
        });


        GroupChoose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove();
                if(GroupChoose.getItemCount()==0){
                    GroupChoose.addItem("Wybierz grupę");
                }
                else {
                    AddMark.this.selectedGroup = GroupChoose.getSelectedItem().toString();
                    studentCombo(selectedField, selectedGroup, con, stmt);
                    subjectCombo(selectedGroup, con, stmt);
                }
            }
        });

        StudentChoose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove();
                if(StudentChoose.getItemCount()==0){
                    StudentChoose.addItem("Wybierz studenta");
                }
                else {
                    AddMark.this.selectedStudent = StudentChoose.getSelectedItem().toString();
                }

            }
        });

        SubjectChoose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove();
                if(SubjectChoose.getItemCount()==0){
                    SubjectChoose.addItem("Wybierz przedmiot");
                }
                if(SubjectChoose.getSelectedItem()!="Wybierz przedmiot") {
                    AddMark.this.selectedSubject = SubjectChoose.getSelectedItem().toString();
                }

            }
        });

        MarkChoose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddMark.this.selectedMark = MarkChoose.getSelectedItem().toString();
            }
        });

        CommitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(MarkChoose.getSelectedItem()!="Wybierz ocenę") {
                    markAdd(user, con, stmt);
                }
            }
        });
        CloseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                marks.dispose();
            }
        });



        ScrollMark = new JScrollPane(markTable);
        marks.add(ScrollMark);
        MarkPanel.setVisible(true);
        marks.setVisible(true);

    }


    private void fieldCombo(Connection con, Statement stmt){
        try {
            ResultSet rs = stmt.executeQuery("select distinct fieldofstudy from student order by fieldofstudy asc");
            while(rs.next())
            {
                FieldChoose.addItem(rs.getString("fieldofstudy"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void groupCombo(String field, Connection con, Statement stmt){
        GroupChoose.removeAllItems();
        try {
            ResultSet rs = stmt.executeQuery("select distinct studentgroup.name from student join studentgroup on studentgroup.groupid=student.groupid where fieldofstudy = '" + field + "' order by studentgroup.name asc");
            while(rs.next())
            {
                GroupChoose.addItem(rs.getString("name"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void studentCombo(String field, String group, Connection con, Statement stmt){
        StudentChoose.removeAllItems();
        try {
            ResultSet rs = stmt.executeQuery("select indexnumber, surname from student" +
                    " join studentgroup on studentgroup.groupid=student.groupid" +
                    " where fieldofstudy = '" + field + "' AND studentgroup.name = '" + group + "'");
            while(rs.next())
            {
                StudentChoose.addItem(rs.getInt("indexnumber") + " " + rs.getString("surname"));
                studentID = rs.getInt("indexnumber");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void subjectCombo(String group, Connection con, Statement stmt){
        SubjectChoose.removeAllItems();
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM SUBJECT");
            while(rs.next())
            {
                SubjectChoose.addItem(rs.getString("name"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void markAdd(String teacher, Connection con, Statement stmt){
        String result;
        int count = 0;
        int update = 0;
        try {
            ResultSet subid = stmt.executeQuery("Select * from subject " +
                    "WHERE name ='"+ selectedSubject + "'");
            while(subid.next()){
                subjectID = subid.getInt("subjectid");
            }
            ResultSet rs = stmt.executeQuery("Select markid from mark" +
                    " join student on student.indexnumber = mark.indexnumber" +
                    " join subject on subject.subjectid = mark.subjectid" +
                    " where mark.indexnumber = '" + studentID +
                    "' and subject.subjectid = '" + subjectID + "'");
            if(!rs.next()){
                Mark mark = new Mark(selectedMark,studentID,Integer.parseInt(teacher),subjectID);
                count = stmt.executeUpdate("insert into mark values(mark_seq.NEXTVAL,'"+mark.getValue()+"','"
                        +mark.getIndexNumber()+"','"+mark.getTeacherID()+"','"+mark.getSubjectID()+ "')");
                if(count>0)
                    System.out.println("records inserted succesfully");
            }
            else{
                Mark mark = new Mark(selectedMark,studentID,Integer.parseInt(teacher),subjectID);
                update = stmt.executeUpdate("update mark set value = '"+ mark.getValue() +
                        "' WHERE indexnumber = '"+ mark.getIndexNumber() +
                        "' AND subjectid = '"+ mark.getSubjectID() + "'");
                if(update>0)
                    System.out.println("records updated succesfully");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void remove() {
        while (model.getRowCount()> 0) {
            model.removeRow(0);
        }
    }



}
