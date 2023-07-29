DROP TABLE    ST_WEEK  CASCADE CONSTRAINTS ;


DROP TABLE ACA_STUDENT CASCADE CONSTRAINTS  ;

DROP TABLE    TEACHER  CASCADE CONSTRAINTS ;

DROP TABLE    ACADEMY   CASCADE CONSTRAINTS;


DROP TABLE    ACA_MEMBER CASCADE CONSTRAINTS  ;
CREATE TABLE  ACA_MEMBER  (
	 MID 	VARCHAR2(20)		NOT NULL,
	 MPWD 	VARCHAR2(20)		NOT NULL,
	 MTYPE 	CHAR(1)		NOT NULL
);

COMMENT ON COLUMN  ACA_MEMBER . MID  IS 'T선생님, S_A학생';

COMMENT ON COLUMN  ACA_MEMBER . MTYPE  IS 'T, S';

INSERT into ACA_MEMBER (MID, MPWD, MTYPE) VALUES ('T00001','TP00001','T');
INSERT into ACA_MEMBER (MID, MPWD, MTYPE) VALUES ('T00002','TP00002','T');
INSERT into ACA_MEMBER (MID, MPWD, MTYPE) VALUES ('T00003','TP00003','T');
INSERT INTO ACA_MEMBER (MID,MPWD,MTYPE) VALUES ('SA001001', 'SA001001', 'S');
INSERT INTO ACA_MEMBER (MID,MPWD,MTYPE) VALUES ('SA001002', 'SA001002', 'S');
INSERT INTO ACA_MEMBER (MID,MPWD,MTYPE) VALUES ('SA001003', 'SA001003', 'S');
INSERT INTO ACA_MEMBER (MID,MPWD,MTYPE) VALUES ('SA001004', 'SA001004', 'S');
INSERT INTO ACA_MEMBER (MID,MPWD,MTYPE) VALUES ('SA001005', 'SA001005', 'S');
INSERT INTO ACA_MEMBER (MID,MPWD,MTYPE) VALUES ('SA001006', 'SA001006', 'S');
INSERT INTO ACA_MEMBER (MID,MPWD,MTYPE) VALUES ('SA001007', 'SA001007', 'S');
INSERT INTO ACA_MEMBER (MID,MPWD,MTYPE) VALUES ('SA001008', 'SA001008', 'S');
INSERT INTO ACA_MEMBER (MID,MPWD,MTYPE) VALUES ('SA001009', 'SA001009', 'S');
INSERT INTO ACA_MEMBER (MID,MPWD,MTYPE) VALUES ('SA001010', 'SA001010', 'S');


CREATE TABLE  ACADEMY  (
	 ACA_NO 	VARCHAR2(10)		NOT NULL,
	 ACA_NAME 	VARCHAR2(20)		NOT NULL,
	 ACA_PHONE 	NUMBER(14)		NOT NULL,
	 ACA_ADDRESS 	VARCHAR2(100)		NOT NULL
);
COMMENT ON COLUMN  ACADEMY . ACA_NO  IS '학원 고유번호, A본원,B지원';

COMMENT ON COLUMN  ACADEMY . ACA_NAME  IS '학원이름';

COMMENT ON COLUMN  ACADEMY . ACA_PHONE  IS '원장연락처';

COMMENT ON COLUMN  ACADEMY . ACA_ADDRESS  IS '주소';

INSERT into ACADEMY (ACA_NO, ACA_NAME, ACA_PHONE,ACA_ADDRESS) VALUES ('A001','염호준수학','01012345678','강남역');

CREATE TABLE  TEACHER  (
	 MID 	VARCHAR2(20)		NOT NULL,
	 TEACHER_NAME 	VARCHAR2(20)		NOT NULL,
	 ENTRANCE_DATE 	DATE		NOT NULL,
	 ACA_NO 	VARCHAR2(30)		NOT NULL
);
SELECT * FROM TEACHER;
COMMENT ON COLUMN  TEACHER . MID  IS 'T선생님, S_A학생';

INSERT INTO TEACHER (MID,TEACHER_NAME,ENTRANCE_DATE,ACA_NO) VALUES ('T00001', '염호준', '230728', 'A001');
INSERT INTO TEACHER (MID,TEACHER_NAME,ENTRANCE_DATE,ACA_NO) VALUES ('T00002', '이지영', '220101', 'A001');
INSERT INTO TEACHER (MID,TEACHER_NAME,ENTRANCE_DATE,ACA_NO) VALUES ('T00003', '김종호', '180525', 'A001');


CREATE TABLE  ACA_STUDENT  (
	 MID2 	VARCHAR2(20)		NOT NULL,
	 STUDENT_NAME 	VARCHAR2(20)		NOT NULL,
	 EXAM_SCORE 	NUMBER(3)		NULL,
	 BIRTHDAY 	DATE		NOT NULL,
	 ENTER_DATE 	DATE		NULL,
	 IMPORTANT 	VARCHAR2(4000)		NULL,
	 MID 	VARCHAR2(20)		NOT NULL
);

COMMENT ON COLUMN  ACA_STUDENT . MID2  IS 'T선생님, S_A학생';

COMMENT ON COLUMN  ACA_STUDENT . EXAM_SCORE  IS '시험을 아직 안봤을수도 있음';

COMMENT ON COLUMN  ACA_STUDENT . BIRTHDAY  IS '나이와 학년은 VIEW로구성';

COMMENT ON COLUMN  ACA_STUDENT . ENTER_DATE  IS '상담일';

COMMENT ON COLUMN  ACA_STUDENT . IMPORTANT  IS '학생 특이사항 주의사항';

COMMENT ON COLUMN  ACA_STUDENT . MID  IS 'T선생님, S_A학생';

INSERT INTO ACA_STUDENT (MID2, STUDENT_NAME, EXAM_SCORE,BIRTHDAY, ENTER_DATE, IMPORTANT, MID) VALUES ('SA001001', '천영준', 100, to_date('950303','yyMMDD'), to_date('200511','yyMMDD'),'특이사항 없음','T00001');




CREATE TABLE  ST_WEEK  (
	 YYMMDD 	DATE		NOT NULL,
	 TC_ID 	VARCHAR2(20)		NOT NULL,
	 ST_ID 	VARCHAR2(20)		NOT NULL,
	 EXPECT 	VARCHAR2(30)		NOT NULL,
	 STUDY 	VARCHAR2(1000)		NULL,
	 ST_COMMENT 	VARCHAR2(1000)		NULL,
	 ACHEIVE 	VARCHAR2(10)		NULL,
	 WEEK_SCORE 	NUMBER(3)		NULL
);

COMMENT ON COLUMN  ST_WEEK . TC_ID  IS 'T선생님, S_A학생';

COMMENT ON COLUMN  ST_WEEK . ST_ID  IS 'T선생님, S_A학생';



ALTER TABLE  ACA_STUDENT  ADD CONSTRAINT  PK_ACA_STUDENT  PRIMARY KEY (
	 MID2 
);

ALTER TABLE  TEACHER  ADD CONSTRAINT  PK_TEACHER  PRIMARY KEY (
	 MID 
);

ALTER TABLE  ACADEMY  ADD CONSTRAINT  PK_ACADEMY  PRIMARY KEY (
	 ACA_NO 
);

ALTER TABLE  ST_WEEK  ADD CONSTRAINT  PK_ST_WEEK  PRIMARY KEY (
	 YYMMDD ,
	 TC_ID ,
	 ST_ID 
);

ALTER TABLE  ACA_MEMBER  ADD CONSTRAINT  PK_ACA_MEMBER  PRIMARY KEY (
	 MID 
);

ALTER TABLE  TEACHER  ADD CONSTRAINT  FK_ACADEMY_TO_TEACHER_1  FOREIGN KEY (
	 ACA_NO 
)
REFERENCES  ACADEMY  (
	 ACA_NO 
);

ALTER TABLE  TEACHER  ADD CONSTRAINT  FK_ACA_MEMBER_TO_TEACHER_1  FOREIGN KEY (
	 MID 
)
REFERENCES  ACA_MEMBER  (
	 MID 
);
ALTER TABLE  ACA_STUDENT  ADD CONSTRAINT  FK_ACA_MEMBER_TO_ACA_STUDENT_1  FOREIGN KEY (
	 MID2 
)
REFERENCES  ACA_MEMBER  (
	 MID 
);

ALTER TABLE  ACA_STUDENT  ADD CONSTRAINT  FK_TEACHER_TO_ACA_STUDENT_1  FOREIGN KEY (
	 MID 
)
REFERENCES  TEACHER  (
	 MID 
);





ALTER TABLE  ST_WEEK  ADD CONSTRAINT  FK_TEACHER_TO_ST_WEEK_1  FOREIGN KEY (
	 TC_ID 
)
REFERENCES  TEACHER  (
	 MID 
);

ALTER TABLE  ST_WEEK  ADD CONSTRAINT  FK_ACA_STUDENT_TO_ST_WEEK_1  FOREIGN KEY (
	 ST_ID 
)
REFERENCES  ACA_STUDENT  (
	 MID2 
);

SELECT * FROM ST_WEEK;

