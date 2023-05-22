package propensi.a04.wois.service;
import propensi.a04.wois.model.CustomerModel;
import propensi.a04.wois.model.ReservasiModel;
import propensi.a04.wois.model.ReviewModel;


import java.util.HashMap;
import java.util.List;

import propensi.a04.wois.model.ReservasiModel;

import java.util.List;

public interface ReviewService {

    List<ReviewModel> getListReview();
    ReviewModel getReviewByIdReview(Long idReview);

    void updateStatusReview (ReviewModel review);

    ReviewModel getReviewByReservasi(ReservasiModel reservasi);
    void addReview(ReviewModel review);
    void deleteReview(Long idReview);
    void ubahReview(ReviewModel review);

    HashMap<Long, ReservasiModel> mappingReservasi(List<ReviewModel> listReview);

}
