package pro.sky.telegram_bot_pets_shelter.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity(name="visitor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Visitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "chat_id", unique = true,nullable = false)
    private long chatId;
    @Column(name = "name",unique = true,nullable = false)
    private String name;
    @Column(name = "registered_at",nullable = false)
    private LocalDateTime registeredAt;
    @Column(name = "lastAction",nullable = false)
    private String lastAction;
}
