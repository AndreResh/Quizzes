package engine.service;

import engine.domain.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface QuizRepository extends CrudRepository<Quiz,Long> {
    public Page<Quiz> findAll(Pageable pageable);
    public Quiz save(Quiz quiz);
    @Query("SELECT q from Quiz q WHERE q.id=:id")
    public Quiz findQuizById(@Param("id") Long id);
    public void delete(Quiz quiz);
}
