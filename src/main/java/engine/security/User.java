package engine.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotBlank
    @Pattern(regexp = "\\w{1,}@[\\w]+\\.[\\w]+")
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    @Size(min = 5)
    private String password;
}
