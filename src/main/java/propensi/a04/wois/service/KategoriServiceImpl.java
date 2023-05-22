package propensi.a04.wois.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.a04.wois.model.KategoriModel;
import propensi.a04.wois.repository.KategoriDb;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class KategoriServiceImpl implements KategoriService{
    @Autowired
    KategoriDb kategoriDb;

    @Override
    public List<KategoriModel> getListKategori() {
        return kategoriDb.findAll();
    }
}
