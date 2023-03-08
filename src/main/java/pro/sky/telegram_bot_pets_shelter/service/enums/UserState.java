package pro.sky.telegram_bot_pets_shelter.service.enums;

/**
 * Константы состояния пользователей для логики работы бота
 */
public enum UserState {
    /**
     * BASIC_STATE - базове состояние разрешены опреации
     * кроме отправки отчета иначе ввод не корректен
     * REPORT_CATS_STATE - состояние отправки отчета по котам
     * REPORT_DOGS_STATE - состояние отправки отчета по собакам
     */
    BASIC_STATE,
    REPORT_CATS_STATE,
    REPORT_DOGS_STATE
}
