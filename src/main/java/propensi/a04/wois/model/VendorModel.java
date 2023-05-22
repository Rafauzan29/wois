package propensi.a04.wois.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "vendor")
public class VendorModel extends UserModel implements Serializable  {

    @JsonManagedReference
    @OneToOne(mappedBy = "vendor")
    private KatalogModel katalog;

    //Many to many reservasi
    @ManyToMany(mappedBy = "listVendorReservasi")
    List<ReservasiModel> listReservasi;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "kategori_vendor", referencedColumnName = "kategori")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private KategoriModel kategori;

    // one to many ke reservasi

}
