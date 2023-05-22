package propensi.a04.wois.service;

import org.springframework.web.multipart.MultipartFile;
import propensi.a04.wois.model.KatalogModel;
import propensi.a04.wois.model.KategoriModel;

import java.util.List;

public interface KatalogService {
    void addKatalog(MultipartFile file, KatalogModel katalog);
    List<KatalogModel> getListKatalog(String kategori);
    KatalogModel getKatalogById(Long id);
    void deleteKatalog(KatalogModel katalog);

    void updateKatalog(MultipartFile file, KatalogModel katalog);

}
