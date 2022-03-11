package OfficeDeans;

public class Mark {
    private String value;
    private int indexNumber, teacherID, subjectID;

    public Mark(String value, int indexNumber, int teacherID, int subjectID) {
        this.value = value;
        this.indexNumber = indexNumber;
        this.teacherID = teacherID;
        this.subjectID = subjectID;
    }

    public String getValue() {
        return value;
    }

    public int getIndexNumber() {
        return indexNumber;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public int getSubjectID() {
        return subjectID;
    }
}
