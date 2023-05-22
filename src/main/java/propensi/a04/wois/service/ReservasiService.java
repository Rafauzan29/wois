package propensi.a04.wois.service;
import propensi.a04.wois.model.CustomerModel;
import propensi.a04.wois.model.ReservasiModel;

import java.util.HashMap;
import java.util.List;

import propensi.a04.wois.model.ReservasiModel;

import java.util.List;
import java.util.Map;

public interface ReservasiService {
//    ReservasiModel getReservasiByIdReservasi(String idReservasi);
    List<ReservasiModel> getListReservasi();
    List<ReservasiModel> getListCompletedReservasi();
    List<ReservasiModel> getListSoftbookReservasi();
    List<ReservasiModel> getListDP1Reservasi();
    List<ReservasiModel> getListDP2Reservasi();
    void ubahReservasi(ReservasiModel reservasi);
    void hapusReservasi(Long idReservasi);
    void addReservasi(ReservasiModel reservasi);
    ReservasiModel getReservasiByIdReservasi(Long idReservasi);
    HashMap<Long, CustomerModel> mappingHash(List<ReservasiModel> listReservasi);
}
