package propensi.a04.wois.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import propensi.a04.wois.model.CustomerModel;
import propensi.a04.wois.model.PicModel;

@Repository
public interface PicDb extends JpaRepository<PicModel, String> {
    PicModel findByUuid(String id);

    PicModel findByEmail(String email);
}
