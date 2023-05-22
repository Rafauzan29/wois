package propensi.a04.wois.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Data
@Table(name = "review")
public class ReviewModel  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReview;

    @NotNull
    @Size(max = 500)
    @Column(name = "review", nullable = false)
    private String review;

    //Rating 1-5
    @NotNull
    @Column(name = "rating",nullable = false)
    private int rating;

    @NotNull
    @Column(name = "isActive", nullable = false)
    private boolean isActive;

    @Size(max = 500)
    @Column(name = "komentar", nullable = true)
    private String komentar;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_reservasi", referencedColumnName = "idReservasi")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ReservasiModel reservasi;
}