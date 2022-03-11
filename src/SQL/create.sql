CREATE TABLE exam (
    examid     INTEGER NOT NULL,
    "date"     DATE,
    type       CHAR(30 CHAR),
    groupid    INTEGER NOT NULL,
    teacherid  INTEGER NOT NULL,
    subjectid  INTEGER NOT NULL
);

ALTER TABLE exam ADD CONSTRAINT exam_pk PRIMARY KEY ( examid );

CREATE TABLE lesson (
    lessonid     INTEGER NOT NULL,
    lessontime   NUMBER(4, 2),
    timetableid  INTEGER NOT NULL,
    weekdayid    INTEGER NOT NULL,
    subjectid    INTEGER NOT NULL,
    teacherid    INTEGER NOT NULL
);

ALTER TABLE lesson ADD CONSTRAINT lesson_pk PRIMARY KEY ( lessonid );

CREATE TABLE mark (
    markid       INTEGER NOT NULL,
    value        NUMBER(2, 1),
    indexnumber  INTEGER NOT NULL,
    teacherid    INTEGER NOT NULL,
    subjectid    INTEGER NOT NULL
);

ALTER TABLE mark ADD CONSTRAINT mark_pk PRIMARY KEY ( markid );

CREATE TABLE officeemployee (
    officeemployeeid  INTEGER NOT NULL,
    password          CHAR(30 CHAR),
    name              CHAR(30 CHAR),
    surname           CHAR(30 CHAR),
    age               INTEGER,
    phonenumber       CHAR(10 CHAR),
    mail              CHAR(30 CHAR)
);

ALTER TABLE officeemployee ADD CONSTRAINT officeemployee_pk PRIMARY KEY ( officeemployeeid );

CREATE TABLE proposal (
    proposalid    INTEGER NOT NULL,
    proposalname  CHAR(40 CHAR),
    "date"        DATE,
    "session"     INTEGER,
    income        INTEGER,
    avg           FLOAT(4),
    indexnumber   INTEGER NOT NULL,
    decision      CHAR(40 CHAR)
);

ALTER TABLE proposal ADD CONSTRAINT proposal_pk PRIMARY KEY ( proposalid );

CREATE TABLE student (
    indexnumber   INTEGER NOT NULL,
    password      CHAR(30 CHAR),
    name          CHAR(30 CHAR),
    surname       CHAR(30 CHAR),
    age           INTEGER,
    phonenumber   CHAR(10 CHAR),
    mail          CHAR(30 CHAR),
    fieldofstudy  CHAR(40 CHAR),
    groupid       INTEGER NOT NULL
);

ALTER TABLE student ADD CONSTRAINT student_pk PRIMARY KEY ( indexnumber );

CREATE TABLE studentgroup (
    groupid  INTEGER NOT NULL,
    name     CHAR(40 CHAR)
);

ALTER TABLE studentgroup ADD CONSTRAINT studentgroup_pk PRIMARY KEY ( groupid );

CREATE TABLE subject (
    subjectid  INTEGER NOT NULL,
    name       CHAR(40 CHAR)
);

ALTER TABLE subject ADD CONSTRAINT subject_pk PRIMARY KEY ( subjectid );

CREATE TABLE teacher (
    teacherid    INTEGER NOT NULL,
    password     CHAR(30 CHAR),
    name         CHAR(30 CHAR),
    surname      CHAR(30 CHAR),
    age          INTEGER,
    phonenumber  CHAR(10 CHAR),
    mail         CHAR(30 CHAR)
);

ALTER TABLE teacher ADD CONSTRAINT teacher_pk PRIMARY KEY ( teacherid );

CREATE TABLE timetable (
    timetableid  INTEGER NOT NULL,
    groupid      INTEGER NOT NULL
);

CREATE UNIQUE INDEX timetable__idx ON
    timetable (
        groupid
    ASC );

ALTER TABLE timetable ADD CONSTRAINT timetable_pk PRIMARY KEY ( timetableid );

CREATE TABLE weekday (
    weekdayid  INTEGER NOT NULL,
    name       CHAR(40 CHAR)
);

ALTER TABLE weekday ADD CONSTRAINT weekday_pk PRIMARY KEY ( weekdayid );

ALTER TABLE exam
    ADD CONSTRAINT exam_studentgroup_fk FOREIGN KEY ( groupid )
        REFERENCES studentgroup ( groupid );

ALTER TABLE exam
    ADD CONSTRAINT exam_subject_fk FOREIGN KEY ( subjectid )
        REFERENCES subject ( subjectid );

ALTER TABLE exam
    ADD CONSTRAINT exam_teacher_fk FOREIGN KEY ( teacherid )
        REFERENCES teacher ( teacherid );

ALTER TABLE lesson
    ADD CONSTRAINT lesson_subject_fk FOREIGN KEY ( subjectid )
        REFERENCES subject ( subjectid );

ALTER TABLE lesson
    ADD CONSTRAINT lesson_teacher_fk FOREIGN KEY ( teacherid )
        REFERENCES teacher ( teacherid );

ALTER TABLE lesson
    ADD CONSTRAINT lesson_timetable_fk FOREIGN KEY ( timetableid )
        REFERENCES timetable ( timetableid );

ALTER TABLE lesson
    ADD CONSTRAINT lesson_weekday_fk FOREIGN KEY ( weekdayid )
        REFERENCES weekday ( weekdayid );

ALTER TABLE mark
    ADD CONSTRAINT mark_student_fk FOREIGN KEY ( indexnumber )
        REFERENCES student ( indexnumber );

ALTER TABLE mark
    ADD CONSTRAINT mark_subject_fk FOREIGN KEY ( subjectid )
        REFERENCES subject ( subjectid );

ALTER TABLE mark
    ADD CONSTRAINT mark_teacher_fk FOREIGN KEY ( teacherid )
        REFERENCES teacher ( teacherid );

ALTER TABLE proposal
    ADD CONSTRAINT proposal_student_fk FOREIGN KEY ( indexnumber )
        REFERENCES student ( indexnumber );

ALTER TABLE student
    ADD CONSTRAINT student_studentgroup_fk FOREIGN KEY ( groupid )
        REFERENCES studentgroup ( groupid );

ALTER TABLE timetable
    ADD CONSTRAINT timetable_studentgroup_fk FOREIGN KEY ( groupid )
        REFERENCES studentgroup ( groupid );





CREATE SEQUENCE group_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE proposal_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE subject_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE exam_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE timetable_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE lesson_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE mark_seq START WITH 1 INCREMENT BY 1;