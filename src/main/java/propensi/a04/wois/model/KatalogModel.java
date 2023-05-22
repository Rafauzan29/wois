package propensi.a04.wois.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Data
@Table(name = "katalog")
public class KatalogModel implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "instagram", nullable = true)
    private String instagram;

    @NotNull
    @Column(name = "harga", nullable = true)
    private Long harga;

    @NotNull
    @Size(max = 50)
    @Column(name = "lokasi", nullable = true)
    private String lokasi;

    @NotNull
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String foto;

    @NotNull
    @Size(max = 500)
    @Column(name = "deskripsi", nullable = false)
    private String deskripsi;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vendor", referencedColumnName = "uuid")
    private VendorModel vendor;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "kategori_vendor", referencedColumnName = "kategori")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private KategoriModel kategori;

}
