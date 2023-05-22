package propensi.a04.wois.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

// @Builder
// @AllArgsConstructor
// @NoArgsConstructor
// @Getter
// @Setter
@Entity
@Data
//@JsonIgnoreProperties(value = {"listVendorReservasi"}, allowSetters = true)
@Table(name = "reservasi")
// @Inheritance(strategy = InheritanceType.JOINED)
public class ReservasiModel implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idReservasi;

    @NotNull
    @Column(nullable = false)
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private LocalDate tanggal;

    //1: softbook (#E8F553)
    //2: Down Payment 1 (#42CF61)
    //3: Down Payment 2 (#F19191)
    //4: Fully Paid (#FFC267)
    @NotNull
    @Column(nullable = false)
    private int status;


    //one to one customer
//    @JsonIgnore
//    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "id_customer", referencedColumnName = "uuid")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CustomerModel customer;
//     @OneToOne(fetch = FetchType.LAZY, optional = false)
//     @JoinColumn(name = "id_customer", referencedColumnName = "uuid")
// //    @ToString.Exclude
//     @OnDelete(action = OnDeleteAction.CASCADE)
//     private CustomerModel customer;

    


    //many to many vendor
    @ManyToMany
    @JoinTable(name = "vendor_reservasi",
            joinColumns = {
                    @JoinColumn(name = "idReservasi", referencedColumnName = "idReservasi")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "uuid", referencedColumnName = "uuid")
            }
    )
    List<VendorModel> listVendorReservasi;

    @JsonIgnore
    @OneToOne(mappedBy = "reservasi")
    private ReviewModel review;

    

}
