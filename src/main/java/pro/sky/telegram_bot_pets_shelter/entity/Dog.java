package pro.sky.telegram_bot_pets_shelter.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;


@Entity(name="dog")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Сущность песика")
public class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",unique = true,nullable = false)
    private String name;
    private Boolean adopted;

    private LocalDate dateAdoption;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "report_id")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Report report;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dog dog = (Dog) o;
        return Objects.equals(id, dog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}