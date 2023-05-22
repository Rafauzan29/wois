package propensi.a04.wois.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import propensi.a04.wois.model.JadwalModel;

import java.time.LocalDateTime;
import java.util.List;

public interface JadwalDb extends CrudRepository<JadwalModel, Long> {
    @Query("from JadwalModel j where not(j.end < :from or j.start > :to)")
    public List<JadwalModel> findBetween(@Param("from") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime start, @Param("to") @DateTimeFormat(iso= ISO.DATE_TIME) LocalDateTime end);
}
