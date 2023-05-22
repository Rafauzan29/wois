package propensi.a04.wois.service;

import propensi.a04.wois.model.CustomerModel;
import propensi.a04.wois.model.PicModel;
import propensi.a04.wois.model.UserModel;

public interface PicService {
//    UserModel addPic (UserModel pic);
    public String encrypt(String password);

    PicModel getPicByUuid (String id);

    PicModel getPicByEmail (String email);
}
