package propensi.a04.wois.service;

import org.apache.catalina.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import propensi.a04.wois.model.UserModel;
import propensi.a04.wois.repository.ProfileDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.a04.wois.repository.UserDb;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileDb profileDb;

    @Autowired
    private UserDb userDb;

    @Override
    public UserModel getUserByUuid (String id){
        UserModel user = profileDb.findByUuid(id);
        return user;
    }

    @Override
    public UserModel getUserByEmail (String email){
        UserModel user = profileDb.findByEmail(email);
        return user;
    }

    @Override
    public UserModel updatePassword (UserModel user, String newPassword){
        String pass = encrypt(newPassword);
        user.setPassword(pass);
        return userDb.save(user);
    }

    @Override
    public UserModel updateProfile (UserModel user){
        return userDb.save(user);
    }

    @Override
    public String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

    @Override
    public boolean matchPassword(String oldPassword,String nowPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean isMatched = passwordEncoder.matches(oldPassword, nowPassword);
        return isMatched;
    }

    @Override
    public void encryptPassword(UserModel user) {
        String pass = encrypt(user.getPassword());
        user.setPassword(pass);
    }
}
