package OfficeDeans;

import OfficeDeans.packageProposal.Proposal;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ProposalView extends JFrame implements ActionListener {
    private JComboBox ProposalChoose;
    private JPanel ProposalPanel;
    private JPanel ProposalForm;
    private JTextField name;
    private JTextField surname;
    private JTextField fieldOfStudy;
    private JTextField session;
    private JTextField id;
    // JTextField date;
    private JTextField income;
    private JTextField avg;
    private JPanel ButtonPanel;
    private JButton sendButton;
    private JButton resetButton;
    private JPanel avgPanel;
    private JPanel incomePanel;
    private JButton closeButton;
    private DatePicker date;
    Statement stmt;
    Connection con;

    public ProposalView(int indexNumber, Connection con, Statement stmt) {
        JFrame proposal = this;
        proposal.setSize(400, 500);
        proposal.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(ProposalPanel);
        this.stmt = stmt;
        this.con = con;
        //this.pack();

        ProposalChoose.addItem("Stypendium socjalne");
        ProposalChoose.addItem("Stypendium naukowe");
        ProposalChoose.addActionListener(this);

        resetButton.addActionListener(this);
        sendButton.addActionListener(this);

        closeButton.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                proposal.dispose();
            }
        }));


        ProposalForm.setVisible(true);
        incomePanel.setVisible(true);
        proposal.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        incomePanel.setVisible(false);
        avgPanel.setVisible(false);
        String isSelected = (String) ProposalChoose.getSelectedItem();
        if (isSelected == "Stypendium socjalne") {
            incomePanel.setVisible(true);
        } else if (isSelected == "Stypendium naukowe") {
            avgPanel.setVisible(true);
        }

        int session=0;
        int income=0;
        double avg= 0.0;
        LocalDate date = null;
        int count=0;
        String dateFormString = null;

        if (e.getSource() == resetButton) {
            name.setText("");
            surname.setText("");
            fieldOfStudy.setText("");
            this.session.setText("");
            id.setText("");
            this.date.setText("");
            this.income.setText("");
            this.avg.setText("");
        }
        else if(e.getSource() == sendButton){
            if(!this.session.getText().isEmpty()) {
                session = Integer.parseInt(this.session.getText());
            }
            if(!this.date.getText().isEmpty()) {
                dateFormString = this.date.getDate().toString();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
                date = LocalDate.parse(dateFormString, formatter);
            }
            if (isSelected == "Stypendium socjalne") {
                if(!this.income.getText().isEmpty()) {
                    income = Integer.parseInt(this.income.getText());
                }
                Proposal SocialGrantForm = new Proposal("Stypendium socjalne",name.getText(),surname.getText(),
                        fieldOfStudy.getText(),id.getText(),dateFormString,session,income);

                try {
                    count = stmt.executeUpdate("INSERT INTO PROPOSAL (PROPOSALID, PROPOSALNAME, PROPOSALDATE, ACADEMICSESSION" +
                            ", INCOME, INDEXNUMBER, DECISION) VALUES " + "(proposal_seq.NEXTVAL,'"+SocialGrantForm.getProposalName()+
                            "',TO_DATE('"+dateFormString+"','YYYY-MM-DD'),'"+SocialGrantForm.getSession()+
                            "',"+SocialGrantForm.getIncome()+","+SocialGrantForm.getId()+", 'Oczekujace')");
                    if(count>0) {
                        System.out.println("records inserted succesfully");
                        JOptionPane.showMessageDialog(null,"Wniosek zostal pomyslnie dodany","Info",1);
                    }
                    else {
                        System.out.println("records insertion failed");
                    }
                } catch (SQLException throwables) {
                    JOptionPane.showMessageDialog(null,"Wniosek zawiera bledne dane","Info",1);
                    throwables.printStackTrace();
                }

            }
            else if(isSelected == "Stypendium naukowe"){
                if(!this.avg.getText().isEmpty()) {
                    avg = Double.parseDouble(this.avg.getText());
                }


                Proposal FellowShipForm = new Proposal("Stypendium naukowe",name.getText(),surname.getText(),
                        fieldOfStudy.getText(),id.getText(),dateFormString,session,avg);

                try {
                    count = stmt.executeUpdate("INSERT INTO PROPOSAL (PROPOSALID, PROPOSALNAME, PROPOSALDATE, ACADEMICSESSION" +
                            ", MARKAVG, INDEXNUMBER,DECISION) VALUES " + "(proposal_seq.NEXTVAL,'"+FellowShipForm.getProposalName()+
                            "','"+FellowShipForm.getDate()+"','"+FellowShipForm.getSession()+
                            "',"+FellowShipForm.getAvg()+","+FellowShipForm.getId()+",'Oczekujace')");
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


}
