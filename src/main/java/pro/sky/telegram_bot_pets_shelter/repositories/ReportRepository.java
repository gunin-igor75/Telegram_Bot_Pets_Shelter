package pro.sky.telegram_bot_pets_shelter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.telegram_bot_pets_shelter.entity.Report;

import java.time.LocalDate;

public interface ReportRepository extends JpaRepository<Report,Long> {

    @Query(value = "select * from report r inner join cat c on r.id = c.report_id inner join owner o on c.id = o.cat_id where o.cat_id = ?1 and r.date_report = ?2 ",
            nativeQuery = true)
    Report getReportCat(long chatId, LocalDate dateReport);

    @Query(value = "select * from report r inner join dog d on r.id = d.report_id inner join owner o on d.id = o.cat_id where o.dog_id = ?1 and r.date_report = ?2 ",
            nativeQuery = true)
    Report getReportDog(long chatId, LocalDate dateReport);
}