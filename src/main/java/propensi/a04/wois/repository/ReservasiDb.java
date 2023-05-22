package propensi.a04.wois.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import propensi.a04.wois.model.ReservasiModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservasiDb extends JpaRepository<ReservasiModel, Long> {
//    ReservasiModel findByIdReservasi(String idReservasi);

//    @Query("select r from ReservasiModel r")
//    List<ReservasiModel> findAll();

    Optional<ReservasiModel> findReservasiByIdReservasi(Long idReservasi);

}
