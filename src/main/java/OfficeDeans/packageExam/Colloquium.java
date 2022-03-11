package OfficeDeans.packageExam;

import java.time.LocalDateTime;

public class Colloquium extends Exam {
    public Colloquium(LocalDateTime date, int groupID, int teacherID, int subjectID) {
        super(date, groupID, teacherID, subjectID);
        setType();
    }
    @Override
    public Exam clone() {
        Colloquium coll = new Colloquium(this.getDate(), this.getGroupID(), this.getTeacherID(), this.getSubjectID());
        return coll;
    }
    @Override
    public void setType(){
        this.type = "Kolokwium";
    }
}
