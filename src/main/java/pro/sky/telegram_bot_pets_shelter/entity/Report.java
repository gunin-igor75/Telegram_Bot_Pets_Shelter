package pro.sky.telegram_bot_pets_shelter.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
@Entity(name="report")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "owner_name",unique = true,nullable = false)
    private String ownerName;
    @Column(name = "date",unique = true,nullable = false)
    private LocalDateTime date;
    @Column(name = "health_behavior",nullable = false)
    private String health_behavior;
    @Column(name = "diet",nullable = false)
    private String diet;
    @Lob
    @Column(name = "photo", nullable = false)
    private byte[] photo;
}
