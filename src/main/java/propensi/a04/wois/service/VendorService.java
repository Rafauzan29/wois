package propensi.a04.wois.service;

import propensi.a04.wois.model.CustomerModel;
import propensi.a04.wois.model.UserModel;
import propensi.a04.wois.model.VendorModel;
import java.util.List;

public interface VendorService {
//    VendorModel addVendor(VendorModel vendor);
    List<VendorModel> getListVendor();
    List<VendorModel> getListVendorAll();
    List<VendorModel> getListVendorUpdate(Long id);
    public String encrypt(String password);
    
    VendorModel getVendorByUuid (String id);
    VendorModel getVendorByEmail (String email);
}