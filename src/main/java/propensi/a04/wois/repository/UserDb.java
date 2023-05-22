package propensi.a04.wois.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import propensi.a04.wois.model.UserModel;

import java.util.Optional;

@Repository
public interface UserDb extends JpaRepository<UserModel, String> {
    Optional<UserModel> findByUuid(String uuid);
    UserModel findByEmail(String email);

}
