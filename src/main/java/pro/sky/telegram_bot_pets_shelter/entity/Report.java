package pro.sky.telegram_bot_pets_shelter.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "report")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long chatId;

    private LocalDate dateReport;

    private String healthStatus;

    private String fileId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "cat_report",
            joinColumns = @JoinColumn (name = "report_id"),
            inverseJoinColumns = @JoinColumn(name = "cat_id")
    )
    private Cat cat;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "dog_report",
            joinColumns = @JoinColumn (name = "report_id"),
            inverseJoinColumns = @JoinColumn(name = "dog_id")
    )
    private Dog dog;




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return Objects.equals(id, report.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
