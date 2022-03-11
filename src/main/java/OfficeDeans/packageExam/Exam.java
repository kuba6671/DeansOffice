package OfficeDeans.packageExam;

import java.time.LocalDateTime;

public abstract class Exam implements Cloneable {
    private LocalDateTime date;
    private int groupID, teacherID, subjectID;
    protected String type;

    public Exam(LocalDateTime date, int groupID, int teacherID, int subjectID) {
        this.date = date;
        this.groupID = groupID;
        this.teacherID = teacherID;
        this.subjectID = subjectID;
    }

    public abstract Exam clone();


    public LocalDateTime getDate() {
        return date;
    }

    public int getGroupID() {
        return groupID;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setType() {
        this.type = null;
    }

    public String getType() {
        return type;
    }

}
