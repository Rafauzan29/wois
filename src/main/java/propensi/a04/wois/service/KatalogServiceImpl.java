package propensi.a04.wois.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import propensi.a04.wois.model.KatalogModel;
import propensi.a04.wois.model.KategoriModel;
import propensi.a04.wois.model.VendorModel;
import propensi.a04.wois.repository.KatalogDb;
import propensi.a04.wois.repository.KategoriDb;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class KatalogServiceImpl implements KatalogService {
    @Autowired
    KatalogDb katalogDb;

    @Override
    public void addKatalog(MultipartFile file, KatalogModel katalog) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if(fileName.contains(".."))
        {
            System.out.println("not a a valid file");
        }
        try {
            katalog.setFoto(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        katalogDb.save(katalog);
    }

    @Override
    public List<KatalogModel> getListKatalog(String kategori) {
        List<KatalogModel> listKatalog = katalogDb.findAll();

        List<KatalogModel> listCatering = new ArrayList<>();
        List<KatalogModel> listWo = new ArrayList<>();
        List<KatalogModel> listEntertainment = new ArrayList<>();
        List<KatalogModel> listDecoration = new ArrayList<>();
        List<KatalogModel> listPhotography = new ArrayList<>();
        List<KatalogModel> listAttire = new ArrayList<>();
        List<KatalogModel> listAllin = new ArrayList<>();

        for (KatalogModel katalog : listKatalog){
            if(katalog.getKategori().getKategori().equalsIgnoreCase("catering")){
                listCatering.add(katalog);
            } else if(katalog.getKategori().getKategori().equalsIgnoreCase("wo")){
                listWo.add(katalog);
            } else if(katalog.getKategori().getKategori().equalsIgnoreCase("entertainment")){
                listEntertainment.add(katalog);
            } else if(katalog.getKategori().getKategori().equalsIgnoreCase("decoration")){
                listDecoration.add(katalog);
            } else if(katalog.getKategori().getKategori().equalsIgnoreCase("photography")){
                listPhotography.add(katalog);
            } else if(katalog.getKategori().getKategori().equalsIgnoreCase("attire")){
                listAttire.add(katalog);
            } else {
                listAllin.add(katalog);
            }
        }

        if (kategori.equalsIgnoreCase("catering")) {
            return listCatering;
        } else if (kategori.equalsIgnoreCase("wo")) {
            return listWo;
        } else if(kategori.equalsIgnoreCase("entertainment")) {
            return listEntertainment;
        } else if (kategori.equalsIgnoreCase("decoration")) {
            return listDecoration;
        } else if (kategori.equalsIgnoreCase("photography")) {
            return listPhotography;
        } else if (kategori.equalsIgnoreCase("attire")) {
            return listAttire;
        } else {
            return listAllin;
        }
    }

    @Override
    public KatalogModel getKatalogById(Long id) {
        Optional<KatalogModel> katalog = katalogDb.findById(id);
        if (katalog.isPresent()) {
            return katalog.get();
        } else {
            return null;
        }
    }

    @Override
    public void deleteKatalog(KatalogModel katalog) {
        katalogDb.delete(katalog);
    }

    @Override
    public void updateKatalog(MultipartFile file, KatalogModel katalog) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if(fileName.contains(".."))
        {
            System.out.println("not a a valid file");
        }
        try {
            katalog.setFoto(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        katalogDb.save(katalog);
    }


}
