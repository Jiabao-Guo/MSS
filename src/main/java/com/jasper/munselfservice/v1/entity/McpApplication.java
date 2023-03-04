package com.jasper.munselfservice.v1.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Table(name = "mcp_application", schema = "jasper")
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class McpApplication {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "user_id", nullable = false)
    private Integer userId;

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

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private User user;
}
