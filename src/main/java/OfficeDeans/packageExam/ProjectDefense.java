package OfficeDeans.packageExam;

import java.time.LocalDateTime;

public class ProjectDefense extends Exam {
    public ProjectDefense(LocalDateTime date, int groupID, int teacherID, int subjectID) {
        super(date, groupID, teacherID, subjectID);
        setType();
    }
    @Override
    public Exam clone() {
        ProjectDefense project = new ProjectDefense(this.getDate(), this.getGroupID(), this.getTeacherID(), this.getSubjectID());
        return project;
    }
    @Override
    public void setType(){
        this.type = "Obrona projektu";
    }
}
