
package com.demo.controllers;

import com.demo.models.dtos.QuizDTO;
import com.demo.models.dtos.QuizHistoryDTO;
import com.demo.models.entities.QuizEntity;
import com.demo.models.http.ResponseMessage;
import com.demo.models.request.QuizHistoryRequest;
import com.demo.services.QuizHistoryService;
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
    private final QuizHistoryService quizHistoryService;

    @Autowired
    public QuizController(QuizService quizService, QuizHistoryService quizHistoryService) {
        this.quizService = quizService;
        this.quizHistoryService = quizHistoryService;
    }

    @GetMapping("/{quizId}")
    public ResponseEntity<ResponseMessage<QuizDTO>> get(@PathVariable("quizId") int quizId) {
        ResponseMessage<QuizDTO> responseMessage = new ResponseMessage<>();
        HttpStatus status = HttpStatus.OK;
        try {
            responseMessage.ok(this.quizService.findById(quizId).orElse(null));
        } catch (Exception ex) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            responseMessage.error(ex.getMessage());
        }
        return ResponseEntity.status(status).body(responseMessage);
    }

    @GetMapping
    public ResponseEntity<ResponseMessage<List<QuizDTO>>> getAll() {
        ResponseMessage<List<QuizDTO>> responseMessage = new ResponseMessage<>();

        HttpStatus status = HttpStatus.OK;
        try {
            responseMessage.ok(this.quizService.findAll());
        } catch (Exception ex) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            responseMessage.error(ex.getMessage());
        }
        return ResponseEntity.status(status).body(responseMessage);
    }

    @PutMapping
    public ResponseEntity<Boolean> add(@RequestBody QuizDTO quiz) {
        boolean result = true;
        HttpStatus status = HttpStatus.OK;
        try {
            this.quizService.add(quiz);
        } catch (Exception ex) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            result = false;
        }
        return ResponseEntity.status(status).body(result);
    }

    @PostMapping
    public ResponseEntity<Boolean> update(@RequestBody QuizEntity quiz) {
        boolean result = true;
        HttpStatus status = HttpStatus.OK;
        try {
            this.quizService.update(quiz);
        } catch (Exception ex) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            result = false;
        }
        return ResponseEntity.status(status).body(result);
    }

    @DeleteMapping("/{quizId}")
    public ResponseEntity<ResponseMessage<Boolean>> delete(@PathVariable("quizId") int quizId) {
        ResponseMessage<Boolean> responseMessage = new ResponseMessage<>();
        HttpStatus status = HttpStatus.OK;
        try {
            this.quizService.deleteById(quizId);
            responseMessage.ok(true, "Delete quiz successful!");
        } catch (Exception ex) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            responseMessage.error(false, ex.getMessage());
        }
        return ResponseEntity.status(status).body(responseMessage);
    }

    @PutMapping("/submit")
    public ResponseEntity<ResponseMessage<Boolean>> delete(@RequestBody QuizHistoryRequest quizHistoryRequest) {
        ResponseMessage<Boolean> responseMessage = new ResponseMessage<>();
        HttpStatus status = HttpStatus.OK;
        try {
            var rs = this.quizHistoryService.submit(quizHistoryRequest);
            if (!rs) {
                throw new Exception("Submit fail!");
            }
            responseMessage.ok(true, "Submit quiz successful!");
        } catch (Exception ex) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            responseMessage.error(false, ex.getMessage());
        }
        return ResponseEntity.status(status).body(responseMessage);
    }

    @GetMapping("/history/{userId}/{quizId}")
    public ResponseEntity<ResponseMessage<QuizHistoryDTO>> getQuizHistoryByQuizIdAndUserId(@PathVariable("userId") int userId, @PathVariable("quizId") int quizId) {
        ResponseMessage<QuizHistoryDTO> responseMessage = new ResponseMessage<>();
        HttpStatus status = HttpStatus.OK;
        try {
            responseMessage.ok(this.quizHistoryService.findQuizHistoryByUserIdAndQuizId(userId, quizId));
        } catch (Exception ex) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            responseMessage.error(ex.getMessage());
        }
        return ResponseEntity.status(status).body(responseMessage);
    }
}
