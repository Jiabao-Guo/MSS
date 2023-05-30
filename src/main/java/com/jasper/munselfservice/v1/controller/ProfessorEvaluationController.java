package com.jasper.munselfservice.v1.controller;

import com.jasper.munselfservice.v1.controller.forms.GenericResponse;
import com.jasper.munselfservice.v1.controller.forms.evaluation.ProfessorEvaluationForm;
import com.jasper.munselfservice.v1.controller.generic.BaseController;
import com.jasper.munselfservice.v1.entity.ProfessorRating;
import com.jasper.munselfservice.v1.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Optional;

@RestController
public class ProfessorEvaluationController extends BaseController {
    @PostMapping("/api/v1/professor-evaluation")
    public ResponseEntity<GenericResponse> create(@AuthenticationPrincipal Jwt token, @RequestBody ProfessorEvaluationForm form) {
        if (professorRatingRepository.existsByCreator_Uid(getUserUid(token))) {
            return fail("Duplicated evaluation");
        }

        if (form.getLectureEndDate().before(form.getLectureStartDate())) {
            return fail("End date cannot be earlier than start date");
        }

        Optional<User> target = userRepository.findByUid(form.getInstructorUid());
        if (target.isEmpty()) {
            return fail("Instructor not found");
        }

        if (target.get().getInstructorInfo() == null) {
            return fail("Not an instructor");
        }

        ProfessorRating rating = ProfessorRating.builder()
            .creatorUserId(getUserId(token))
            .targetUserId(target.get().getId())
            .lectureStartDate(new Timestamp(form.getLectureStartDate().getTime()))
            .lectureEndDate(new Timestamp(form.getLectureEndDate().getTime()))
            .courseType(String.join(",", form.getCourseType()))
            .evaluation(form.getEvaluation())
            .satisfaction(form.getSatisfaction())
            .build();

        try {
            professorRatingRepository.save(rating);
        }
        catch (Exception e) {
            return fail("Failed to save");
        }

        return ok();
    }
}
