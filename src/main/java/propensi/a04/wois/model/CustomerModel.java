package propensi.a04.wois.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "customer")
public class CustomerModel extends UserModel implements Serializable  {

    // @JsonIgnore
    // @OneToOne(mappedBy = "customer")
    // private ReservasiModel reservasi;

    @JsonManagedReference(value = "customer")
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<ReservasiModel> daftarReservasi;
}
