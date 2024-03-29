
package com.demo.controllers;

import com.demo.models.Quiz;
import com.demo.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 165139
 */
@RestController
@RequestMapping("api/v1/quizzes")
public class QuizController {
    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/{quizId}")
    public ResponseEntity<Quiz> get(@PathVariable("quizId") int quizId) {
        Quiz quiz = null;
        HttpStatus status = HttpStatus.OK;
        try {
            quiz = quizService.findById(quizId).orElse(null);
        } catch (Exception ex) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(status).body(quiz);
    }

    @GetMapping
    public ResponseEntity<List<Quiz>> getAll() {
        List<Quiz> quizzes = List.of();
        HttpStatus status = HttpStatus.OK;
        try {
            quizzes = quizService.findAll();
        } catch (Exception ex) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(status).body(quizzes);
    }

    @PutMapping
    public ResponseEntity<Boolean> add(@RequestBody Quiz quiz) {
        boolean result = true;
        HttpStatus status = HttpStatus.OK;
        try {
            quizService.add(quiz);
        } catch (Exception ex) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            result = false;
        }
        return ResponseEntity.status(status).body(result);
    }

    @PostMapping
    public ResponseEntity<Boolean> update(@RequestBody Quiz quiz) {
        boolean result = true;
        HttpStatus status = HttpStatus.OK;
        try {
            quizService.update(quiz);
        } catch (Exception ex) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            result = false;
        }
        return ResponseEntity.status(status).body(result);
    }

    @DeleteMapping("/{quizId}")
    public ResponseEntity<Boolean> delete(@PathVariable("quizId") int quizId) {
        boolean result = true;
        HttpStatus status = HttpStatus.OK;
        try {
            quizService.deleteById(quizId);
        } catch (Exception ex) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            result = false;
        }
        return ResponseEntity.status(status).body(result);
    }

}
