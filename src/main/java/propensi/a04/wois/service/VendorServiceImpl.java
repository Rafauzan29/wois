package propensi.a04.wois.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import propensi.a04.wois.model.CustomerModel;
import propensi.a04.wois.model.KatalogModel;
import propensi.a04.wois.model.KategoriModel;
import propensi.a04.wois.model.UserModel;
import propensi.a04.wois.model.VendorModel;
import propensi.a04.wois.repository.KatalogDb;
import propensi.a04.wois.repository.UserDb;
import propensi.a04.wois.repository.VendorDb;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class VendorServiceImpl implements VendorService {
    @Autowired
    private VendorDb vendorDb;

    @Autowired
    private KatalogDb katalogDb;

//    @Override
//    public VendorModel addVendor(VendorModel vendor) {
//        String pass = encrypt(vendor.getPassword());
//        vendor.setPassword(pass);
//        vendor.setRole("Vendor");
//        return vendorDb.save(vendor);
//    }

    @Override
    public List<VendorModel> getListVendor() {
        List<VendorModel> listVendorAvail = new ArrayList<>();

        List<VendorModel> listAllVendor = vendorDb.findAll();
        List<KatalogModel> listAllKatalog = katalogDb.findAll();

        for(VendorModel vendor : listAllVendor) {
            boolean stat = true;
            for(KatalogModel katalog : listAllKatalog){
                VendorModel vendorKatalog = katalog.getVendor();
                if (vendor == vendorKatalog){
                    stat = false;
                    break;
                }
            } if (stat == true){
                listVendorAvail.add(vendor);
            }
        }
        return listVendorAvail;
    }


    @Override
    public List<VendorModel> getListVendorAll(){
        List<VendorModel> listAllVendor = vendorDb.findAll();
        // List<VendorModel> listCateringFix = new ArrayList<>();
       
        // for(VendorModel vendor : listAllVendor){
        //     if(vendor.getKategori().equals(KategoriModel.getKategori().equals("Catering"))){
        //         listCateringFix.add(vendor);
        //     }

        // }

        return listAllVendor;
    }

    @Override
    public List<VendorModel> getListVendorUpdate(Long id) {
        List<VendorModel> listVendorAvailUpdate = new ArrayList<>();

        List<VendorModel> listAllVendor = vendorDb.findAll();
        List<KatalogModel> listAllKatalog = katalogDb.findAll();

        for(VendorModel vendor : listAllVendor) {
            boolean stat = true;
            for(KatalogModel katalog : listAllKatalog){
                VendorModel vendorKatalog = katalog.getVendor();
                if (vendor == vendorKatalog){
                    if(katalog.getId() != id){
                        stat = false;
                        break;
                    }
                }
            } if (stat == true){
                listVendorAvailUpdate.add(vendor);
            }
        }
        return listVendorAvailUpdate;
    }

    @Override
    public String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

    @Override
    public VendorModel getVendorByUuid (String id){
        VendorModel vendor = vendorDb.findByUuid(id);
        return vendor;
    }

    @Override
    public VendorModel getVendorByEmail (String email){
        VendorModel vendor = vendorDb.findByEmail(email);
        return vendor;
    }
}
