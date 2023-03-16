package pro.sky.telegram_bot_pets_shelter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.telegram_bot_pets_shelter.entity.Report;

import java.time.LocalDate;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {

    @Query(value = "select r.* from report r " +
            "inner join cat_report cr on r.id= cr.report_id" +
            " inner join cat c on cr.cat_id=c.id " +
            "where r.chat_id=?1 and r.date_report=?2",
            nativeQuery = true)
    Optional<Report> getReportCat(long chatId, LocalDate dateReport);

    @Query(value = "select r.* from report r " +
            "inner join dog_report dr on r.id= dr.report_id" +
            " inner join dog d on dr.dog_id=d.id " +
            "where r.chat_id=?1 and r.date_report=?2",
            nativeQuery = true)
    Report getReportDog(long chatId, LocalDate dateReport);
}