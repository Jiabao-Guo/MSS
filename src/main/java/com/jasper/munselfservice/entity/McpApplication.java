package com.jasper.munselfservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "mcp_application", schema = "jasper")
public class McpApplication {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "student_number", nullable = false)
    private Integer studentNumber;
    @Basic
    @Column(name = "marital_status", nullable = false, length = 125)
    private String maritalStatus;
    @Basic
    @Column(name = "application_time", nullable = false)
    private Timestamp applicationTime;
    @Basic
    @Column(name = "delivery", nullable = false)
    private Byte delivery;
    @Basic
    @Column(name = "identity_type", nullable = false, length = 125)
    private String identityType;
    @Basic
    @Column(name = "reason", nullable = false, length = 255)
    private String reason;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_number", referencedColumnName = "student_number", nullable = false, insertable = false, updatable = false)
    private Student studentByStudentNumber;
}
