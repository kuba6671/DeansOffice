package OfficeDeans.packagePerson;

public class OfficeEmployee extends Person {
    private int OfficeEmployeeID;

    public OfficeEmployee(String name, String surname, int age, String phoneNumber, String mail, int OfficeEmployeeID,String password) {
        super(name, surname, age, phoneNumber, mail, password);
        setOfficeEmployeeID(OfficeEmployeeID);
    }

    public void setOfficeEmployeeID(int officeEmployeeID) {
        OfficeEmployeeID = officeEmployeeID;
    }

    public int getOfficeEmployeeID() {
        return OfficeEmployeeID;
    }
}
