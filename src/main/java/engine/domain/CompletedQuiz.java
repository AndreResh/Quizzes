package engine.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CompletedQuiz")
public class CompletedQuiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "completed_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long completedId;
    @Column(name = "quiz_id")
    private Long id;
    @Column(name = "username")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String username;
    @Column(name = "date_of_completing")
    private LocalDateTime completedAt;

    public CompletedQuiz(Long id, String username, LocalDateTime completedAt) {
        this.id = id;
        this.username = username;
        this.completedAt = completedAt;
    }
}
