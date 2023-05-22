package propensi.a04.wois.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "history")
public class HistoryModel  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLogHistory;

    @NotNull
    @Column(nullable = false)
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private LocalDate tanggal;

    @NotNull
    @Size(max = 500)
    @Column(name = "namaUser", nullable = false)
    private String namaUser;

    @NotNull
    @Size(max = 500)
    @Column(name = "roleUser", nullable = false)
    private String roleUser;

    @NotNull
    @Size(max = 500)
    @Column(name = "deskripsi", nullable = false)
    private String deskripsi;


}