package pro.sky.telegram_bot_pets_shelter.service.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import pro.sky.telegram_bot_pets_shelter.controller.TelegramBot;
import pro.sky.telegram_bot_pets_shelter.entity.Owner;
import pro.sky.telegram_bot_pets_shelter.service.BlackListService;
import pro.sky.telegram_bot_pets_shelter.service.OwnerService;
import pro.sky.telegram_bot_pets_shelter.service.ReportService;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.time.LocalDate;
import java.util.List;


public abstract class ShelterServicePet {
    private final MessageUtils messageUtils;
    private final TelegramBot telegramBot;

    protected final OwnerService ownerService;

    protected final ReportService reportService;

    protected final BlackListService blackListService;
    private SendMessage message;

    protected LocalDate currentDate;

    public ShelterServicePet(OwnerService ownerService, ReportService reportService,
                             MessageUtils messageUtils, TelegramBot telegramBot,
                             BlackListService blackListService) {
        this.ownerService = ownerService;
        this.reportService = reportService;
        this.messageUtils = messageUtils;
        this.telegramBot = telegramBot;
        this.blackListService = blackListService;
    }

    @Scheduled(cron = "0 00 21 * * *")
    protected void checkEndTestPeriodPetAndSendMessage() {
        currentDate = LocalDate.now();
        List<Owner> ownersEndTestPeriod = getOwnersEndTestPeriod();
        for (Owner owner : ownersEndTestPeriod) {
            int countMissingReport = getMissingAndBadReport(owner);
            int attempt = getAttempt(owner);
            long chatId = owner.getChatId();
            if (countMissingReport == 0) {
                endOfAdoptionPositive(owner);
                message = messageUtils.generationSendMessage(chatId,
                        "Поздравляем питомец Ваш навсегда");
            } else if (attempt == 1) {
                int testPeriod = getNewTestPeriod(owner);
                message = messageUtils.generationSendMessage(chatId,
                        "Вам назначен новый испытательный срок на " + testPeriod + " дней");
            } else {
                endOfAdoptionNegative(owner);
                message = messageUtils.generationSendMessage(chatId,
                        "Увы возвращайте питомца, у Вас ничего не получилось");
            }
        }
        telegramBot.sendAnswerMessage(message);
    }

    protected abstract List<Owner> getOwnersEndTestPeriod();

    protected abstract int getMissingAndBadReport(Owner owner);

    protected abstract int getAttempt(Owner owner);

    protected abstract void endOfAdoptionPositive(Owner owner);

    protected abstract void endOfAdoptionNegative(Owner owner);

    protected abstract int getNewTestPeriod(Owner owner);

}
