package engine.service;

import engine.domain.Answer;
import engine.domain.CompletedQuiz;
import engine.domain.Quiz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;


@Slf4j
@Service
public class QuizService {
    private final QuizRepository repository;
    private final CompletedQuizRepository completedQuizRepository;

    public QuizService(QuizRepository repository,CompletedQuizRepository completedQuizRepository) {
        this.repository = repository;
        this.completedQuizRepository=completedQuizRepository;
    }
    public Quiz saveQuiz(Quiz quiz, UserDetails details){
        if (Objects.isNull(quiz.getAnswer())) {
            quiz.setAnswer(Collections.emptyList());
        }
        quiz.setUser(details.getUsername());
        return repository.save(quiz);
    }
    public Page<Quiz> getAllQuiz(Integer page, Integer size){
        Pageable pageable= PageRequest.of(page,size);
        return repository.findAll(pageable);
    }

    public Quiz findQuizById(Long id){
        return repository.findQuizById(id);
    }
    public Answer getAnswer(Long id, Answer answer,UserDetails details){
       Quiz quiz = repository.findQuizById(id);
        if (quiz==null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (quiz.getAnswer().containsAll(answer.getAnswer()) && answer.getAnswer().containsAll(quiz.getAnswer())) {
            completedQuizRepository.save(new CompletedQuiz(quiz.getId(), details.getUsername(), LocalDateTime.now()));
            log.info("USER WITH NAME: {}, GET CORRECT ANSWER ON QUIZ NUMBER: {}",details.getUsername(),quiz.getId());
            return new Answer(true);
        } else {
            return new Answer(false);
        }
    }

    public void deleteQuiz(Long id, UserDetails details) {
        Quiz quiz = repository.findQuizById(id);
        if (quiz==null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if(quiz.getUser().equals(details.getUsername())){
            repository.delete(quiz);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    public Page<CompletedQuiz> getAllCompletedQuizzes(UserDetails details,Integer page,Integer size) {
        Pageable pageable=PageRequest.of(page,size);
        System.out.println(completedQuizRepository.findAll());
        return completedQuizRepository.findAllByName(details.getUsername(),pageable);
    }
}
