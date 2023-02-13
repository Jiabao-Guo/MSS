package com.jasper.munselfservice.controller;

import com.jasper.munselfservice.controller.forms.GenericResponse;
import com.jasper.munselfservice.controller.forms.evaluation.ProfessorEvaluationForm;
import com.jasper.munselfservice.entity.ProfessorRating;
import com.jasper.munselfservice.entity.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@RestController
public class ProfessorEvaluationController extends BaseController {
    @PostMapping("/professor-evaluation")
    public ResponseEntity<GenericResponse> professorEvaluation(@RequestBody ProfessorEvaluationForm form) {
        Student student = getStudentFromSessionId(form.getStudentNumber(), form.getSessionId());

        if (student == null) {
            return fail("Student does not exist");
        }

        if (professorRatingRepository.existsProfessorRatingByStudentNumberAndInstructorNumber(
            form.getStudentNumber(), form.getInstructorNumber()
        )) {
            return fail("Duplicated evaluation");

        }

        if (form.getLectureEndDate().before(form.getLectureStartDate())) {
            return fail("End date cannot be earlier than start date");

        }

        ProfessorRating rating = new ProfessorRating(
            null,
            form.getStudentNumber(),
            form.getInstructorNumber(),
            new Timestamp(form.getLectureStartDate().getTime()),
            new Timestamp(form.getLectureEndDate().getTime()),
            String.join(",", form.getCourseType()),
            form.getEvaluation(),
            form.getSatisfaction()
        );
        professorRatingRepository.save(rating);

        return ok();
    }
}
