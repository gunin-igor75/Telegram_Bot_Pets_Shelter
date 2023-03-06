package pro.sky.telegram_bot_pets_shelter.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;


@Entity(name="cat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",unique = true,nullable = false)
    private String name;

    @Column(name = "adopted")
    private Boolean adopted;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "report_id")
    private Report report;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cat cat = (Cat) o;
        return Objects.equals(id, cat.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}