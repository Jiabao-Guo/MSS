package com.example.munselfservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "professor_rating", schema = "jasper", catalog = "")
@IdClass(ProfessorRatingPK.class)
@AllArgsConstructor
@NoArgsConstructor
public class ProfessorRating {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "student_number", nullable = false)
    private Integer studentNumber;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "instructor_number", nullable = false)
    private Integer instructorNumber;
    @Basic
    @Column(name = "lecture_start_date", nullable = false)
    private Timestamp lectureStartDate;
    @Basic
    @Column(name = "lecture_end_date", nullable = false)
    private Timestamp lectureEndDate;
    @Basic
    @Column(name = "course_type", nullable = false, length = 128)
    private String courseType;
    @Basic
    @Column(name = "evaluation", nullable = false, length = 255)
    private String evaluation;
    @Basic
    @Column(name = "satisfaction", nullable = false, precision = 0)
    private Double satisfaction;

    public Integer getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(Integer studentNumber) {
        this.studentNumber = studentNumber;
    }

    public Integer getInstructorNumber() {
        return instructorNumber;
    }

    public void setInstructorNumber(Integer instructorNumber) {
        this.instructorNumber = instructorNumber;
    }

    public Timestamp getLectureStartDate() {
        return lectureStartDate;
    }

    public void setLectureStartDate(Timestamp lectureStartDate) {
        this.lectureStartDate = lectureStartDate;
    }

    public Timestamp getLectureEndDate() {
        return lectureEndDate;
    }

    public void setLectureEndDate(Timestamp lectureEndDate) {
        this.lectureEndDate = lectureEndDate;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public Double getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(Double satisfaction) {
        this.satisfaction = satisfaction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfessorRating that = (ProfessorRating) o;
        return Objects.equals(studentNumber, that.studentNumber) && Objects.equals(instructorNumber, that.instructorNumber) && Objects.equals(lectureStartDate, that.lectureStartDate) && Objects.equals(lectureEndDate, that.lectureEndDate) && Objects.equals(courseType, that.courseType) && Objects.equals(evaluation, that.evaluation) && Objects.equals(satisfaction, that.satisfaction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentNumber, instructorNumber, lectureStartDate, lectureEndDate, courseType, evaluation, satisfaction);
    }
}
