package com.jasper.munselfservice.v1.repository;

import com.jasper.munselfservice.v1.entity.StudentInfo;
import com.jasper.munselfservice.v1.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Page<User> findAllByNameContainingIgnoreCaseAndInstructorInfoIsNotNull(Pageable pageable, String name);

    Optional<User> findByUid(Integer uid);

    @Query("SELECT u FROM User u WHERE u.uid = ?1 OR (u.name ILIKE %?2% AND u.email ILIKE %?3% AND u.blocked = ?4 AND u.privileged = ?5)")
    Page<User> advancedFilterForUser(Pageable pageable, Integer uid, String name, String email, Integer blocked, Integer privileged);

    @Query(
        "SELECT u " +
        "FROM User u WHERE " +
            "u.uid = ?1 OR (u.email ILIKE %?4% AND u.name ILIKE %?2% AND (u.studentInfo.age BETWEEN ?5 AND ?6) AND u.studentInfo.gender ILIKE %?3%)")
    Page<User> advancedFilterForStudent(
        Pageable pageable,
        Integer uid,
        String name,
        String gender,
        String email,
        Integer ageLowerBound,
        Integer ageUpperBound
    );

    @Query(
        "SELECT u " +
        "FROM User u WHERE " +
            "u.uid = ?1 OR (" +
            "u.name ILIKE %?2% AND u.email ILIKE %?3% AND u.instructorInfo.salary BETWEEN ?4 AND ?5)")
    Page<User> advancedFilterForInstructor(
        Pageable pageable,
        Integer uid,
        String name,
        String email,
        Integer salaryLowerBound,
        Integer salaryUpperBound
    );

    void deleteAllByIdIn(Iterable<Integer> ids);
}
