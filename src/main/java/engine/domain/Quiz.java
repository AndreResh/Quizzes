package engine.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "quiz")
public class Quiz {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @NotBlank
    @Column(name = "title")
    private String title;
    @NotBlank
    @Column(name = "text")
    private String text;
    @Size(min = 2)
    @NotNull
    @Column(name = "options")
    @ElementCollection
    private List<String> options;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "answer")
    @ElementCollection
    private List<Integer> answer;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "user")
    private String user;
}
