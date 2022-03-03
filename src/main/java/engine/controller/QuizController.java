package engine.controller;

import engine.domain.Answer;
import engine.domain.CompletedQuiz;
import engine.domain.Quiz;
import engine.service.QuizService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/quizzes")
public class QuizController {
    private final QuizService service;

    public QuizController(QuizService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Quiz> saveQuiz(@Valid @RequestBody(required = false) Quiz quiz,@AuthenticationPrincipal UserDetails details) {
        log.info("POST QUIZ: {}",quiz);
        return new ResponseEntity<>(service.saveQuiz(quiz,details), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getSomeQuiz(@PathVariable("id") Long id) {

        Quiz quiz=service.findQuizById(id);
        log.info("GET QUIZ WITH ID: {}. QUIZ: {}",id,quiz);
        if (quiz==null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(quiz, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllQuizzes(
            @RequestParam(value = "page", defaultValue = "0")Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Page<Quiz> list=service.getAllQuiz(page,size);
        log.info("GET ALL QUIZ: {}", list);
            return new ResponseEntity<>(list,HttpStatus.OK);

    }

    @PostMapping("/{id}/solve")
    public ResponseEntity<Answer> getAnswer(@PathVariable("id") Long id, @RequestBody(required = false) Answer answer,@AuthenticationPrincipal UserDetails details) {
        log.info("ANSWER ON QUIZ WITH ID: {}. ANSWER IS: {}",id,answer);
        return new ResponseEntity<>(service.getAnswer(id,answer,details),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable("id")Long id,@AuthenticationPrincipal UserDetails details){
        log.info("DELETE QUIZ WITH ID: {}",id);
        service.deleteQuiz(id,details);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/completed")
    public ResponseEntity<Page<CompletedQuiz>> getAllCompleted(@RequestParam(value = "page",defaultValue = "0")Integer page,
                                                               @RequestParam(value = "size",defaultValue = "10")Integer size,
                                                               @AuthenticationPrincipal UserDetails details){
        log.info("GET COMPLETED FOR USER NAME: {}",details.getUsername());
        return new ResponseEntity<>(service.getAllCompletedQuizzes(details,page,size),HttpStatus.OK);
    }
}
