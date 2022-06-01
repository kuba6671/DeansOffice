package OfficeDeans;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class StudentsList extends JFrame {
    private JPanel StudentPanel;
    private JTable studentTable;
    private JScrollPane ScrollStudent;
    private JComboBox GroupChoose;
    private JComboBox FieldChoose;
    private JButton closeButton;

    private final DefaultTableModel model = new DefaultTableModel(0, 6);


    String[] header = {"Nr indeksu", "Nazwisko", "Imię","Wiek", "Nr telefonu","Email"};

    private String Select;
    private String Select2;



    public StudentsList(Connection con, Statement stmt){
        JFrame students = this;
        students.setSize(400, 400);
        students.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(StudentPanel);

        model.setColumnIdentifiers(header);
        studentTable = new JTable(model);

        FieldChoose.addItem("Wybierz kierunek");
        GroupChoose.addItem("Wybierz grupę");


        fieldCombo(con, stmt);

        FieldChoose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StudentsList.this.Select = FieldChoose.getSelectedItem().toString();
                groupCombo(Select, con, stmt);

            }
        });


        GroupChoose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove();
                if(GroupChoose.getItemCount()==0){
                    GroupChoose.addItem("Wybierz grupę");
                }
                if(GroupChoose.getSelectedItem()!="Wybierz grupę") {
                    StudentsList.this.Select2 = GroupChoose.getSelectedItem().toString();
                    groupSort(Select,Select2,con, stmt);
                }
            }
        });

        closeButton.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                students.dispose();
            }
        }));

        ScrollStudent = new JScrollPane(studentTable);
        students.add(ScrollStudent);
        studentTable.setVisible(true);
        students.setVisible(true);



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


    private void groupSort(String field,String group, Connection con, Statement stmt) {
        try {
            System.out.println("Connection is created successfully:");
            ResultSet rs = stmt.executeQuery("select * from student join studentgroup on student.groupid = studentgroup.groupid where studentgroup.name = '" + group + "' AND fieldofstudy = '" + field + "'");
            while (rs.next()) {
                Object[] row = {rs.getInt("indexnumber"), rs.getString("surname"), rs.getString("name"),rs.getInt("Age"),rs.getInt("phonenumber"), rs.getString("mail")};
                model.addRow(row);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();

        }
    }

    private void remove() {
        while (model.getRowCount()> 0) {
            model.removeRow(0);
        }
    }

}
