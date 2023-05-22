package propensi.a04.wois.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import propensi.a04.wois.model.CustomerModel;
import propensi.a04.wois.model.UserModel;

@Repository
public interface CustomerDb extends JpaRepository<CustomerModel, String> {
    CustomerModel findByUuid(String id);
    CustomerModel findByEmail(String email);
}
