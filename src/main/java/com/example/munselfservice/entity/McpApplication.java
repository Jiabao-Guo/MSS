package com.example.munselfservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "mcp_application", schema = "jasper", catalog = "")
@NoArgsConstructor
@AllArgsConstructor
public class McpApplication {

    //看情況注釋掉
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
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

    public Integer getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(Integer studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Timestamp getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(Timestamp applicationTime) {
        this.applicationTime = applicationTime;
    }

    public Byte getDelivery() {
        return delivery;
    }

    public void setDelivery(Byte delivery) {
        this.delivery = delivery;
    }

    public String getIdentityType() {
        return identityType;
    }

    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        McpApplication that = (McpApplication) o;
        return Objects.equals(studentNumber, that.studentNumber) && Objects.equals(maritalStatus, that.maritalStatus) && Objects.equals(applicationTime, that.applicationTime) && Objects.equals(delivery, that.delivery) && Objects.equals(identityType, that.identityType) && Objects.equals(reason, that.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentNumber, maritalStatus, applicationTime, delivery, identityType, reason);
    }
}
