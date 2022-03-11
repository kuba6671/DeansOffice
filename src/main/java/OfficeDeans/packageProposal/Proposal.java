package OfficeDeans.packageProposal;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Proposal {
    private String proposalName, name, surname, subject, id;
    private String date;
    private int session, income;
    private Double avg;
    private String decision;

    public Proposal(String proposalName, String name, String surname,
                    String fieldOfStudy, String id, String date, int session, int income) {
        this.proposalName = proposalName;
        this.name = name;
        this.surname = surname;
        this.subject = fieldOfStudy;
        this.id = id;
        this.date = date;
        this.session = session;
        this.income = income;
    }

    public Proposal(String proposalName, String name, String surname,
                    String fieldOfStudy, String id, String date, int session, Double avg) {
        this.proposalName = proposalName;
        this.name = name;
        this.surname = surname;
        this.subject = fieldOfStudy;
        this.id = id;
        this.date = date;
        this.session = session;
        this.avg = avg;
    }

    public String getProposalName() {
        return proposalName;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getfieldOfStudy() {
        return subject;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public int getSession() {
        return session;
    }

    public int getIncome() {
        return income;
    }

    public Double getAvg() {
        return avg;
    }

    public String getDecision() {
        return decision;
    }

    protected void setDecisionState(String decision) {
        this.decision = decision;
    }

    public void update(Connection con, Statement stmt, int proposalID){
        try {
            stmt.executeUpdate("UPDATE proposal SET decision='"+decision+"' WHERE proposalID="+proposalID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}


