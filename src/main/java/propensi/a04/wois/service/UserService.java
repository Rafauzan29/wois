package propensi.a04.wois.service;

import propensi.a04.wois.model.UserModel;

import java.util.List;

public interface UserService {
    UserModel addVendorUser(UserModel user);
    UserModel addPicUser(UserModel user);
    List<UserModel> getListUser();
    void deleteUser(String uuid);
    UserModel getUserByUuid(String uuid);
    UserModel getUserByEmail(String email);
    UserModel addUser(UserModel user);
    public String encrypt(String password);
}
