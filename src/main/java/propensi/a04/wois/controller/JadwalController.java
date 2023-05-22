package propensi.a04.wois.controller;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import propensi.a04.wois.model.JadwalModel;
import propensi.a04.wois.repository.JadwalDb;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/jadwal")
public class JadwalController {
    @Autowired
    JadwalDb jadwalDb;

    //https://www.wimdeblauwe.com/blog/2022/02/23/spring-boot-request-parameters-validation/
    @GetMapping(value = "/lihat")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    Iterable<JadwalModel> events(@Past @NotNull @RequestParam("start") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime start,
                                 @PastOrPresent @NotNull @RequestParam("end") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime end) {
        return jadwalDb.findBetween(start, end);
    }

    @PostMapping("/buat")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Transactional
    JadwalModel createEvent(@RequestBody EventCreateParams params) {

        JadwalModel e = new JadwalModel();
        e.setStart(params.start);
        e.setEnd(params.end);
        e.setText(params.text);
        jadwalDb.save(e);

        return e;
    }

    @PostMapping("/pindah")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Transactional
    JadwalModel moveEvent(@RequestBody EventMoveParams params) {

        JadwalModel e = jadwalDb.findById(params.id).get();
        e.setStart(params.start);
        e.setEnd(params.end);
        jadwalDb.save(e);

        return e;
    }

    @PostMapping(value = "/ubahStatus")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Transactional
    JadwalModel setColor(@RequestBody SetColorParams params) {

        JadwalModel e = jadwalDb.findById(params.id).get();
        e.setColor(params.color);
        jadwalDb.save(e);

        return e;
    }

    @PostMapping("/hapus")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Transactional
    EventDeleteResponse deleteEvent(@RequestBody EventDeleteParams params) {

        jadwalDb.deleteById(params.id);

        return new EventDeleteResponse() {{
            message = "Deleted";
        }};
    }

    public static class EventDeleteParams {
        public Long id;
    }

    public static class EventDeleteResponse {
        public String message;
    }

    public static class EventCreateParams {
        public LocalDateTime start;
        public LocalDateTime end;
        public String text;
    }

    public static class EventMoveParams {
        public Long id;
        public LocalDateTime start;
        public LocalDateTime end;
    }

    public static class SetColorParams {
        public Long id;
        public String color;
    }

}
