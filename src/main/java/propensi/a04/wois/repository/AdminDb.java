package propensi.a04.wois.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import propensi.a04.wois.model.AdminModel;
import propensi.a04.wois.model.CustomerModel;
import propensi.a04.wois.model.UserModel;

@Repository
public interface AdminDb extends JpaRepository<AdminModel, String> {
    AdminModel findByUuid(String id);
    AdminModel findByEmail(String email);
}
