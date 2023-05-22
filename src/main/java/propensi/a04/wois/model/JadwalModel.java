package propensi.a04.wois.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Setter @Getter
@Table(name = "jadwal")
public class JadwalModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String text;

    @Column(name = "jadwal_mulai")
    LocalDateTime start;

    @Column(name = "jadwal_selesai")
    LocalDateTime end;

    String color;
}
