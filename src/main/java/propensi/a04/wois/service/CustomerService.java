package propensi.a04.wois.service;

import propensi.a04.wois.model.CustomerModel;
import propensi.a04.wois.model.ReservasiModel;
import propensi.a04.wois.model.UserModel;

public interface CustomerService {
//    void addCustomer(UserModel user);
    public String encrypt(String password);
    boolean checkConfirmedPassword(String password, String confirmedPassword);
    CustomerModel getCustomerByEmail (String email);
    CustomerModel getCustomerByUuid (String id);
    void setReservasi(ReservasiModel reservasi);
}