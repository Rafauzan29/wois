package propensi.a04.wois.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import propensi.a04.wois.model.ReservasiModel;
import propensi.a04.wois.model.ReviewModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewDb extends JpaRepository<ReviewModel, Long>  {
    ReviewModel findReviewByIdReview(Long idReview);
//    Optional<ReservasiModel> findReviewByIdReview(Long idReview);
    ReviewModel findByReservasi(ReservasiModel reservasi);
    
}
