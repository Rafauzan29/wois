package propensi.a04.wois.service;

import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import propensi.a04.wois.model.UserModel;

import java.security.Principal;

@Service
public interface ProfileService {
    UserModel getUserByUuid(String id);

    UserModel getUserByEmail (String Email);

    UserModel updatePassword (UserModel user, String newPassword);

    UserModel updateProfile (UserModel user);

    public String encrypt(String password);

    void encryptPassword (UserModel user);

    public boolean matchPassword (String oldPassword, String nowPassword);
}
