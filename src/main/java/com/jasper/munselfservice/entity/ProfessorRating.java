package com.jasper.munselfservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "professor_rating", schema = "jasper")
public class ProfessorRating {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "student_number", nullable = false)
    private Integer studentNumber;
    @Basic
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
