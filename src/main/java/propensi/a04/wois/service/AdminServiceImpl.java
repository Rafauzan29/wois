package propensi.a04.wois.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import propensi.a04.wois.model.AdminModel;
import propensi.a04.wois.repository.AdminDb;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;

@Service
@Transactional
public class AdminServiceImpl implements AdminService{

    @Autowired
    AdminDb adminDb;


    @Override
    public AdminModel getAdminByUuid (String id){
        AdminModel admin = adminDb.findByUuid(id);
        return admin;
    }

    @Override
    public AdminModel getAdminByEmail (String email){
        AdminModel admin = adminDb.findByEmail(email);
        return admin;
    }

}
