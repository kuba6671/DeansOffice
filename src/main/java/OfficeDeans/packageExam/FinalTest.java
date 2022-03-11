package OfficeDeans.packageExam;

import java.time.LocalDateTime;

public class FinalTest extends Exam {
    public FinalTest(LocalDateTime date, int groupID, int teacherID, int subjectID) {
        super(date, groupID, teacherID, subjectID);
        setType();
    }
    @Override
    public Exam clone() {
        FinalTest finaltest = new FinalTest(this.getDate(), this.getGroupID(), this.getTeacherID(), this.getSubjectID());
        return finaltest;
    }
    @Override
    public void setType(){
        this.type = "Egzamin";
    }
}
