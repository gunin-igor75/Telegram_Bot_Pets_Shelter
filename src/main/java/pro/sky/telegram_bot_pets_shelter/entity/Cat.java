package pro.sky.telegram_bot_pets_shelter.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Entity(name = "cat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    private Boolean adopted;

    private LocalDate dateAdoption;

    @Column(precision = 30)
    private Integer testPeriod;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "cat_report",
            joinColumns = @JoinColumn (name = "cat_id"),
            inverseJoinColumns = @JoinColumn(name = "report_id")
    )
    private List<Report> reports;

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