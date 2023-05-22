package propensi.a04.wois.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import propensi.a04.wois.model.CustomerModel;
import propensi.a04.wois.model.PicModel;
import propensi.a04.wois.model.UserModel;
import propensi.a04.wois.repository.PicDb;
import propensi.a04.wois.repository.UserDb;

@Service
public class PicServiceImpl implements PicService {
    @Autowired
    private PicDb picDb;

    @Autowired
    private UserDb userDb;

//    @Override
//    public UserModel addPic(UserModel pic) {
//        String pass = encrypt(pic.getPassword());
//        pic.setPassword(pass);
//        //pic.setRole("PIC Organizer");
//        return userDb.save(pic);
//    }

    // @Override
    // public UserModel addPic(PicModel pic) {
    //     String pass = encrypt(pic.getPassword());
    //     pic.setPassword(pass);
    //     pic.setRole("PIC Organizer");
    //     return picDb.save(pic);
    // }

    public PicModel getPicByUuid (String id){
        PicModel pic = picDb.findByUuid(id);
        return pic;
    }

    @Override
    public PicModel getPicByEmail (String email){
        PicModel pic = picDb.findByEmail(email);
        return pic;
    }

    @Override
    public String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }
}
