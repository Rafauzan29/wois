package propensi.a04.wois.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import propensi.a04.wois.model.CustomerModel;
import propensi.a04.wois.model.VendorModel;

import java.util.Optional;

@Repository
public interface VendorDb extends JpaRepository<VendorModel, String> {
    VendorModel findByUuid(String id);
    VendorModel findByEmail(String email);
    // VendorModel findByKategoriVendor(String kategori);
}