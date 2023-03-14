package pro.sky.telegram_bot_pets_shelter.exception_handling;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShelterIncorrectData {
    private String info;

    public static ShelterIncorrectData of(String info) {
        return new ShelterIncorrectData(info);
    }

}
