package com.jasper.munselfservice.v1.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Table(name = "professor_rating", schema = "jasper")
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfessorRating {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "creator_user_id", nullable = false)
    private Integer creatorUserId;

    @Basic
    @Column(name = "target_user_id", nullable = false)
    private Integer targetUserId;

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
    @Column(name = "satisfaction", nullable = false)
    private Integer satisfaction;

    @ManyToOne
    @JoinColumn(name = "creator_user_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private User creator;

    @ManyToOne
    @JoinColumn(name = "target_user_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private User target;
}
