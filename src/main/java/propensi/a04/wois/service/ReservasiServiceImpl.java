package propensi.a04.wois.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.a04.wois.model.CustomerModel;
import propensi.a04.wois.model.ReservasiModel;
import propensi.a04.wois.repository.ReservasiDb;

import java.util.*;

@Service
public class ReservasiServiceImpl implements ReservasiService {
    @Autowired
    private ReservasiDb reservasiDb;

//    @Override
//    public ReservasiModel getReservasiByIdReservasi(String idReservasi) {
//        return reservasiDb.findByIdReservasi(idReservasi);
//    }

    @Override
    public void addReservasi(ReservasiModel reservasi) {
        reservasiDb.save(reservasi);
    }

    @Override
    public List<ReservasiModel> getListReservasi() {
        return reservasiDb.findAll();
    }

    @Override
    public List<ReservasiModel> getListCompletedReservasi() {
        List<ReservasiModel> listReservasi = reservasiDb.findAll();
        List<ReservasiModel> listCompletedReservasi = new ArrayList<>();
        for (ReservasiModel reservasi : listReservasi){
            if (reservasi.getStatus() == 4){
                listCompletedReservasi.add(reservasi);
            }
        }
        return listCompletedReservasi;
    }

    @Override
    public List<ReservasiModel> getListSoftbookReservasi() {
        List<ReservasiModel> listReservasi = reservasiDb.findAll();
        List<ReservasiModel> listSoftbookReservasi = new ArrayList<>();
        for (ReservasiModel reservasi : listReservasi){
            if (reservasi.getStatus() == 1){
                listSoftbookReservasi.add(reservasi);
            }
        }
        return listSoftbookReservasi;
    }

    @Override
    public List<ReservasiModel> getListDP1Reservasi() {
        List<ReservasiModel> listReservasi = reservasiDb.findAll();
        List<ReservasiModel> listDP1Reservasi = new ArrayList<>();
        for (ReservasiModel reservasi : listReservasi){
            if (reservasi.getStatus() == 2){
                listDP1Reservasi.add(reservasi);
            }
        }
        return listDP1Reservasi;
    }

    @Override
    public List<ReservasiModel> getListDP2Reservasi() {
        List<ReservasiModel> listReservasi = reservasiDb.findAll();
        List<ReservasiModel> listDP2Reservasi = new ArrayList<>();
        for (ReservasiModel reservasi : listReservasi){
            if (reservasi.getStatus() == 3){
                listDP2Reservasi.add(reservasi);
            }
        }
        return listDP2Reservasi;
    }
    @Override
    public void ubahReservasi(ReservasiModel reservasi) {
        reservasiDb.save(reservasi);
    }

    @Override
    public void hapusReservasi(Long idReservasi) {
        reservasiDb.deleteById(idReservasi);
    }

    public ReservasiModel getReservasiByIdReservasi(Long idReservasi) {
        Optional<ReservasiModel> reservasi = reservasiDb.findById(idReservasi);
        if (reservasi.isPresent()) {
            return reservasi.get();
        } else return null;
    }

    public HashMap<Long, CustomerModel> mappingHash(List<ReservasiModel> listReservasi){
        HashMap<Long, CustomerModel> listID = new HashMap<Long, CustomerModel>();
        for (ReservasiModel reservasi: listReservasi){
            listID.put(reservasi.getIdReservasi(), reservasi.getCustomer());
        }
        return listID;
    }
}
