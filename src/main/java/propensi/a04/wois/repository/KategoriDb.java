package propensi.a04.wois.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import propensi.a04.wois.model.KategoriModel;

@Repository
public interface KategoriDb extends JpaRepository<KategoriModel,String>  {

}

