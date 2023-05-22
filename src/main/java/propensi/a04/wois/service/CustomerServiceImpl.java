package propensi.a04.wois.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import propensi.a04.wois.model.CustomerModel;
import org.springframework.stereotype.Service;
import propensi.a04.wois.model.ReservasiModel;
import propensi.a04.wois.model.UserModel;
import propensi.a04.wois.repository.CustomerDb;
import propensi.a04.wois.repository.ReservasiDb;
import propensi.a04.wois.repository.UserDb;

import javax.transaction.Transactional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    CustomerDb customerDb;

    @Autowired
    UserDb userDb;

    @Autowired
    ReservasiDb reservasiDb;

//    @Override
//    public void addCustomer(UserModel user){
//        user.setRole("Customer");
//        String pass = encrypt(user.getPassword());
//        user.setPassword(pass);
//        userDb.save(user);
//    }

    @Override
    public String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

    @Override
    public CustomerModel getCustomerByUuid (String id){
        CustomerModel customer = customerDb.findByUuid(id);
        return customer;
    }

    @Override
    public CustomerModel getCustomerByEmail (String email){
        CustomerModel customer = customerDb.findByEmail(email);
        return customer;
    }

    @Override
    public boolean checkConfirmedPassword(String password, String confirmedPassword){
        if (password.equals(confirmedPassword)){
            return true;
        } else{
            return false;
        }
    }

    @Override
    public void setReservasi(ReservasiModel reservasi){
        CustomerModel customer = customerDb.findByEmail(reservasi.getCustomer().getEmail());
        reservasi.setCustomer(customer);

    }
}
