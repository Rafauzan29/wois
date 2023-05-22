package propensi.a04.wois.repository;
import propensi.a04.wois.model.KatalogModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KatalogDb extends JpaRepository<KatalogModel,String>  {
    Optional<KatalogModel> findById(Long id);
}

