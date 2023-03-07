package pro.sky.telegram_bot_pets_shelter.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity(name="visitor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Visitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "chat_id", unique = true,nullable = false)
    private long chatId;
    @Column(name = "name",unique = true,nullable = false)
    private String name;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "registered_at",nullable = false)
    private LocalDate registeredAt;
    @Column(name = "lastAction")
    private String lastAction;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Visitor visitor = (Visitor) o;
        return Objects.equals(id, visitor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
