package OfficeDeans;

import OfficeDeans.packageProposal.AcceptedProposalState;
import OfficeDeans.packageProposal.PendingProposalState;
import OfficeDeans.packageProposal.Proposal;
import OfficeDeans.packageProposal.RejectedProposalState;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class examiningProposal extends JFrame implements ActionListener {
    private JPanel examiningPanel;
    private JComboBox proposalTypeComboBox;
    private JComboBox proposalIDComboBox;
    private JPanel ProposalForm;
    private JPanel ButtonPanel;
    private JTextField nameField;
    private JTextField surnameField;
    private JTextField fieldOfStudyField;
    private JTextField sessionField;
    private JTextField indexField;
    private JTextField dateField;
    private JTextField incomeField;
    private JTextField avgField;
    private JPanel incomePanel;
    private JPanel avgPanel;
    private JComboBox DecisionComboBox;
    private JButton submitButton;
    private JButton closeButton;
    private Vector<String> SocialID = new Vector<String>();
    private Vector<String> FellowID = new Vector<String>();
    private String tmpID = null;
    Connection con = null;
    Statement stmt = null;

    public examiningProposal(Connection con, Statement stmt){
        JFrame proposal = this;
        proposal.setSize(400, 500);
        proposal.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(examiningPanel);
        this.con = con;
        this.stmt = stmt;

        proposalTypeComboBox.addItem("Wybierz typ wniosku");
        proposalTypeComboBox.addItem("Stypendium socjalne");
        proposalTypeComboBox.addItem("Stypendium naukowe");

        DecisionComboBox.addItem("Oczekujace");
        DecisionComboBox.addItem("Przyjete");
        DecisionComboBox.addItem("Odrzucone");

        proposalIDComboBox.addItem("Wybierz wniosek");

        try {
            ResultSet proposalsID = stmt.executeQuery("SELECT * from proposal WHERE DECISION='Oczekujace'");
            while (proposalsID.next()) {

                tmpID = proposalsID.getString("proposalName");
                tmpID = tmpID.replaceAll("\\s+"," ");
                if(tmpID.equals("Stypendium socjalne")) {
                    SocialID.add(proposalsID.getString("proposalID"));
                }else if( tmpID.equals("Stypendium naukowe")){
                    FellowID.add(proposalsID.getString("proposalID"));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        proposalTypeComboBox.addActionListener(this);
        proposalIDComboBox.addActionListener(this);
        DecisionComboBox.addActionListener(this);

        submitButton.addActionListener(this);

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

    private void resetField(){
        nameField.setText("");
        surnameField.setText("");
        fieldOfStudyField.setText("");
        sessionField.setText("");
        indexField.setText("");
        dateField.setText("");
        incomeField.setText("");
        avgField.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == proposalTypeComboBox){
            proposalIDComboBox.removeAllItems();
            resetField();
        }
        incomePanel.setVisible(false);
        avgPanel.setVisible(false);
        String isSelectedID = (String) proposalIDComboBox.getSelectedItem();
        String isSelectedType = (String) proposalTypeComboBox.getSelectedItem();
        if (isSelectedType == "Stypendium socjalne") {
            for (String array : SocialID) {
                if(((DefaultComboBoxModel)proposalIDComboBox.getModel()).getIndexOf(array) == -1)
                    proposalIDComboBox.addItem(array);
            }
            incomePanel.setVisible(true);
            try {
                if(isSelectedID != "Wybierz wniosek"){
                    ResultSet proposals = stmt.executeQuery("SELECT * from proposal " +
                            "JOIN Student ON student.indexNumber = proposal.indexNumber" +
                            " where proposalName = \'Stypendium socjalne\' AND proposal.proposalID="+isSelectedID);
                    if(proposals.next()){
                        nameField.setText(proposals.getString("name"));
                        surnameField.setText(proposals.getString("surname"));
                        fieldOfStudyField.setText(proposals.getString("fieldOfStudy"));
                        sessionField.setText(proposals.getString("academicsession"));
                        indexField.setText(proposals.getString("indexNumber"));
                        String dateFormat = proposals.getString("proposaldate");
                        String[] parts = dateFormat.split(" ");
                        dateFormat = parts[0];
                        dateField.setText(dateFormat);
                        incomeField.setText(proposals.getString("income"));
                    }
                    else{
                        resetField();
                    }
                }
                else{
                    resetField();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (isSelectedType == "Stypendium naukowe") {
            avgPanel.setVisible(true);
            for (String array : FellowID) {
                if(((DefaultComboBoxModel)proposalIDComboBox.getModel()).getIndexOf(array) == -1)
                    proposalIDComboBox.addItem(array);
            }
            try {
                if(isSelectedID != "Wybierz wniosek"){
                    ResultSet proposals = stmt.executeQuery("SELECT * from proposal " +
                            "JOIN Student ON student.indexNumber = proposal.indexNumber" +
                            " where proposalName = \'Stypendium naukowe\' AND proposal.proposalID="+isSelectedID);
                    if(proposals.next()){
                        nameField.setText(proposals.getString("name"));
                        surnameField.setText(proposals.getString("surname"));
                        fieldOfStudyField.setText(proposals.getString("fieldOfStudy"));
                        sessionField.setText(proposals.getString("academicsession"));
                        indexField.setText(proposals.getString("indexNumber"));
                        String dateFormat = proposals.getString("proposaldate");
                        String[] parts = dateFormat.split(" ");
                        dateFormat = parts[0];
                        dateField.setText(dateFormat);
                        avgField.setText(proposals.getString("markavg"));
                    }
                    else{
                        resetField();
                    }
                }
                else{
                    resetField();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if(e.getSource()==submitButton){
            String decision = (String) DecisionComboBox.getSelectedItem();
            Proposal proposal = null;
            if(isSelectedType == "Stypendium socjalne"){
                proposal = new Proposal(isSelectedType,nameField.getText(),surnameField.getText(),
                        fieldOfStudyField.getText(), indexField.getText(),
                        dateField.getText(), Integer.parseInt(sessionField.getText()),
                        Integer.parseInt(incomeField.getText()));
            }
            if(isSelectedType == "Stypendium naukowe"){
                proposal = new Proposal(isSelectedType,nameField.getText(),surnameField.getText(),
                        fieldOfStudyField.getText(), indexField.getText(),
                        dateField.getText(), Integer.parseInt(sessionField.getText()),
                        Double.parseDouble(avgField.getText()));
            }
            if(decision == "Przyjete"){
                AcceptedProposalState state = new AcceptedProposalState();
                state.updateState(proposal);
            }
            else if(decision== "Odrzucone"){
                RejectedProposalState state = new RejectedProposalState();
                state.updateState(proposal);
            }
            else if(decision == "Oczekujace"){
                PendingProposalState state = new PendingProposalState();
                state.updateState(proposal);
            }
            proposal.update(con, stmt, Integer.parseInt(isSelectedID));
            JOptionPane.showMessageDialog(null,"Decyzja zostala dodana do wniosku","Info",1);
        }
    }
}

