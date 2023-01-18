package com.example.munselfservice.controller;

import com.example.munselfservice.entity.ProfessorRating;
import com.example.munselfservice.entity.Student;
import com.example.munselfservice.controller.forms.ProfessorEvaluationForm;
import com.example.munselfservice.controller.forms.ProfessorEvaluationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@RestController
public class ProfessorEvaluationController extends BaseController {

    @RequestMapping("/professor-evaluation")
    public ResponseEntity<ProfessorEvaluationResponse>
    professorEvaluation(@RequestBody ProfessorEvaluationForm form) {
        Student student = validateStudentSession(form.getStudentNumber(), form.getSessionId());

        if (student == null) {
            return ResponseEntity.ok(
                    new ProfessorEvaluationResponse(false,"Student not exists"));
        }


       // List<Instructor> instructors = instructorRepository.findByNameContaining(form.getProfessorName());

        //入库前驗證 同一學生不可以重複評價
        if (professorRatingRepository.existsProfessorRatingByStudentNumberAndInstructorNumber(
                form.getStudentNumber(),form.getInstructorNumber()
        )) {
            return ResponseEntity.ok(
                    new ProfessorEvaluationResponse(false,"you had evaluated teacher!!!"));

        }
        //入库前驗證 時間問題
        if(form.getLectureEndDate().before(form.getLectureStartDate())){
            return ResponseEntity.ok(
                    new ProfessorEvaluationResponse(false,"wrong time"));

        }

        //入库
        ProfessorRating rating = new ProfessorRating(
                form.getStudentNumber(),
                form.getInstructorNumber(),
                new Timestamp(form.getLectureStartDate().getTime()),
                new Timestamp(form.getLectureEndDate().getTime()),
                String.join(",", form.getCourseType()),
                form.getEvaluation(),
                form.getSatisfaction()
        );
        professorRatingRepository.save(rating);

        return ResponseEntity.ok(
                new ProfessorEvaluationResponse(true,"Congratulations!"));

    }





}



