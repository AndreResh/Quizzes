package engine.service;

import engine.domain.CompletedQuiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletedQuizRepository extends CrudRepository<CompletedQuiz,Long> {
    @Query("SELECT cq FROM CompletedQuiz cq WHERE cq.username=:username ORDER BY cq.completedId DESC ")
    public Page<CompletedQuiz> findAllByName(@Param("username") String username, Pageable pageable);
}
