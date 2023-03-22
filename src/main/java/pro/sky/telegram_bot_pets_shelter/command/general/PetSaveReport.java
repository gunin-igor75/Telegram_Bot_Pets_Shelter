package pro.sky.telegram_bot_pets_shelter.command.general;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.telegram_bot_pets_shelter.entity.Owner;
import pro.sky.telegram_bot_pets_shelter.entity.Report;
import pro.sky.telegram_bot_pets_shelter.service.OwnerService;
import pro.sky.telegram_bot_pets_shelter.service.ReportService;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public abstract class PetSaveReport {

    protected final MessageUtils messageUtils;
    protected final ReportService reportService;

    protected final OwnerService ownerService;

    protected Owner owner;

    protected Report report;
    protected Set<Report> reports;

    public PetSaveReport(MessageUtils messageUtils, ReportService reportService, OwnerService ownerService) {
        this.messageUtils = messageUtils;
        this.reportService = reportService;
        this.ownerService = ownerService;
    }

    public SendMessage execute(Update update) {
        String fileId = getString(update);
        String caption = update.getMessage().getCaption();
        String text = update.getMessage().getText();
        boolean isPhoto = update.getMessage().hasPhoto();
        boolean isCaption = checkReportString(caption);
        boolean isText = checkReportString(text);
        long chatId = messageUtils.getChatId(update);
        LocalDate dateReport = LocalDate.now();
        report = getReportPet(chatId, dateReport);
        if (isText && report == null) {
            creteReportPet(chatId, fileId, text);
            return messageUtils.generationSendMessage(update,
                    "send a photo");
        }
        if (isPhoto && !isCaption && report == null) {
            creteReportPet(chatId, fileId, text);
            return messageUtils.generationSendMessage(update,
                    "send information in text");
        }
        if (report != null && fileId != null) {
            editReportPet(report, fileId);
            return messageUtils.generationSendMessage(update, "Thank you");
        }
        if (report != null && isText) {
            editReportPet(report, text);
            return messageUtils.generationSendMessage(update, "Thank you");
        } else {
            return messageUtils.generationSendMessage(update, "Submit a report or enter /cancel");
        }
    }

    private String getString(Update update) {
        String fileId = null;
        List<PhotoSize> photo = update.getMessage().getPhoto();
        if (photo != null) {
            fileId = photo.get(0).getFileId();
        }
        return fileId;
    }

    private boolean checkReportString(String string) {
        return string != null
                && (string.contains("diet")
                || string.contains("health")
                || string.contains("behavior"));
    }

    protected Report setFieldOrHealthStatus(Report report, String fileIdOrHealthStatus) {
        if (report.getFileId() == null) {
            report.setFileId(fileIdOrHealthStatus);
        } else {
            report.setHealthStatus(fileIdOrHealthStatus);
        }
        return report;
    }

    protected abstract Report getReportPet(long chatId, LocalDate dateReport);

    protected abstract void creteReportPet(long chatId, String fileId, String text);

    protected abstract void editReportPet(Report report, String fileId);

}
