package propensi.a04.wois.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Data
@Table(name = "kategori")
public class KategoriModel implements Serializable {
    @Id
    @Size(max = 50)
    @Column(name = "kategori", nullable = false)
    private String kategori;
}
