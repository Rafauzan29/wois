package propensi.a04.wois.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.a04.wois.model.CustomerModel;
import propensi.a04.wois.model.ReservasiModel;
import propensi.a04.wois.model.ReviewModel;
import propensi.a04.wois.repository.ReservasiDb;
import propensi.a04.wois.repository.ReviewDb;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewDb reviewDb;

    @Override
    public ReviewModel getReviewByIdReview(Long idReview) {
        return reviewDb.findReviewByIdReview(idReview);
    }

    @Override
    public List<ReviewModel> getListReview() {
        return reviewDb.findAll();
    }

    @Override
    public void updateStatusReview(ReviewModel review) {
        reviewDb.save(review);
    }

    @Override
    public void addReview(ReviewModel review) {
        reviewDb.save(review);
    }


    @Override
    public void ubahReview(ReviewModel review) {
        reviewDb.save(review);
    }

    @Override
    public void deleteReview(Long idReview) {
        reviewDb.deleteById(idReview);
    }

    @Override
    public ReviewModel getReviewByReservasi(ReservasiModel reservasi) {
        return reviewDb.findByReservasi(reservasi);
    }

    public HashMap<Long, ReservasiModel> mappingReservasi(List<ReviewModel> listReview){
        HashMap<Long, ReservasiModel> listID = new HashMap<Long, ReservasiModel>();
        for (ReviewModel review: listReview){
            listID.put(review.getIdReview(), review.getReservasi());
        }
        return listID;
    }

}
