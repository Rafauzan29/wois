package propensi.a04.wois.repository;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import propensi.a04.wois.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface ProfileDb extends JpaRepository<UserModel, String> {
    UserModel findByUuid(String id);

    UserModel findByEmail(String email);
}
