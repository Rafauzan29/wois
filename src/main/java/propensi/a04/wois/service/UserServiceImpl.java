package propensi.a04.wois.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import propensi.a04.wois.model.UserModel;
import propensi.a04.wois.repository.UserDb;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDb userDb;

    @Override
    public UserModel addVendorUser(UserModel user) {
        String pass = encrypt(user.getPassword());
        user.setPassword(pass);
        user.setRole("Vendor");
        return userDb.save(user);
    }

    @Override
    public UserModel addPicUser(UserModel user) {
        String pass = encrypt(user.getPassword());
        user.setPassword(pass);
        user.setRole("PIC Organizer");
        return userDb.save(user);
    }

    @Override
    public List<UserModel> getListUser() {
        return userDb.findAll();
    }

    @Override
    public void deleteUser(String uuid) {
        userDb.deleteById(uuid);
    }

    @Override
    public UserModel getUserByUuid(String uuid) {
        Optional<UserModel> user = userDb.findByUuid(uuid);
        if(user.isPresent()) {
            return user.get();
        } else return null;
    }

    @Override
    public UserModel getUserByEmail(String email) {
        return userDb.findByEmail(email);
    }

    @Override
    public UserModel addUser(UserModel user) {
        String pass = encrypt(user.getPassword());
        user.setPassword(pass);
        return userDb.save(user);
    }

    @Override
    public String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }
}
