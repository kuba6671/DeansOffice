package OfficeDeans;

import OfficeDeans.packagePerson.Student;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class RegistrationView extends GUI {
    private JPanel RegistrationPanel;
    private JPanel ButtonPanel;
    private JPanel RegistrationForm;
    private JButton register;

    private JTextField idText;
    private JTextField surnameText;
    private JTextField nameText;
    private JTextField phoneText;
    private JTextField mailText;
    private JTextField groupText;

    private JLabel nazwiskoLabel;
    private JButton close;
    private JTextField ageText;
    private JTextField fieldOfStudyText;
    private JTextField passwordText;

    Connection con = null;
    Statement stmt = null;
    int i;

    public RegistrationView(Connection con, Statement stmt) {
        JFrame registration = this;
        registration.setSize(400, 450);
        registration.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(RegistrationPanel);
        //this.pack();

        this.con = con;
        this.stmt = stmt;

        registration.setVisible(true);

        register.addActionListener(new ActionListener() { //dodać zabezpieczenie przed wprowadzaniem pustych wierszy i złych wartości(np. w pole numeryczne liter)
            @Override
            public void actionPerformed(ActionEvent e) {
                registerdb();
                clearform();
            }
        });

        close.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registration.dispose();
            }
        }));

    }

    private void registerdb(){
        String id = idText.getText();
        String surname = surnameText.getText();
        String name = nameText.getText();
        String phone = phoneText.getText();
        String mail = mailText.getText();
        String group = groupText.getText();
        String age = ageText.getText();
        String fieldOfStudy = fieldOfStudyText.getText();
        String password = passwordText.getText();

        try {
            int count;

            int groupID=0;
            String groupName = null;

            ResultSet rs = stmt.executeQuery("select * from StudentGroup where name = " + group);
            if(!rs.next()){
                count = stmt.executeUpdate("insert into StudentGroup values(group_seq.NEXTVAL,'"+group+"')");
                if(count>0)
                    System.out.println("records inserted succesfully");
                else
                    System.out.println("records insertion failed");
                rs = stmt.executeQuery("select * from StudentGroup where name = " + group);
                while(rs.next()) {
                    groupID = rs.getInt("groupID");
                    groupName = rs.getString("name");
                }
            }else {
                groupID = rs.getInt("groupID");
                groupName = rs.getString("name");
            }

            Group group1 = new Group(groupID,groupName);
            Student student = new Student(Integer.parseInt(id),password,name,surname,Integer.parseInt(age),phone,mail,group1.getGroupID(),fieldOfStudy);

            count = stmt.executeUpdate("insert into Student values("+student.getIndexNumber()+",'"+student.getPassword()+"','"
                    +student.getName()+"','" +student.getSurname()+ "',"+student.getAge()+",'"
                    +student.getPhoneNumber()+"','" +student.getMail()+"','"+student.getFieldOfStudy()+"',"+group1.getGroupID()+")");
            if(count>0)
                System.out.println("records inserted succesfully");
            else
                System.out.println("records insertion failed");

            System.out.println("Record is inserted in the table successfully..................");
        } catch (SQLException excep) {
            excep.printStackTrace();
        } catch (Exception excep) {
            excep.printStackTrace();
        }
    }

    private void clearform(){
        idText.setText("");
        surnameText.setText("");
        nameText.setText("");
        phoneText.setText("");
        mailText.setText("");
        groupText.setText("");
        ageText.setText("");
        fieldOfStudyText.setText("");
        passwordText.setText("");
    }
}

