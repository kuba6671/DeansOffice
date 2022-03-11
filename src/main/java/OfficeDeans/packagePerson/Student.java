package OfficeDeans.packagePerson;


public class Student extends Person {
    private int indexNumber, groupID;
    private String fieldOfStudy;
    public Student(int indexNumber,String password,String name, String surname, int age,
                   String phoneNumber, String mail,int groupID, String fieldOfStudy) {
        super(name, surname, age, phoneNumber, mail, password);
        this.indexNumber = indexNumber;
        this.groupID = groupID;
        this.fieldOfStudy = fieldOfStudy;
    }

    public Student(int indexNumber, String name, String surname, int age, String phoneNumber, String mail,
                   int groupID, String fieldOfStudy) {
        super(name, surname, age, phoneNumber, mail);
        this.indexNumber = indexNumber;
        this.groupID = groupID;
        this.fieldOfStudy = fieldOfStudy;
    }

    public int getIndexNumber() {
        return indexNumber;
    }

    public int getGroupID() {
        return groupID;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }
}
