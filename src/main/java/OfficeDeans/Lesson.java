package OfficeDeans;

public class Lesson {
    private String lessonTime;
    private int timetableID, weekdayID,subjectID, teacherID;

    public Lesson(String lessonTime, int timetableID, int weekdayID, int subjectID, int teacherID) {
        this.lessonTime = lessonTime;
        this.timetableID = timetableID;
        this.weekdayID = weekdayID;
        this.subjectID = subjectID;
        this.teacherID = teacherID;
    }

    public String getLessonTime() {
        return lessonTime;
    }

    public int getTimetableID() {
        return timetableID;
    }

    public int getWeekdayID() {
        return weekdayID;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public int getTeacherID() {
        return teacherID;
    }
}
