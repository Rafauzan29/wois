package propensi.a04.wois.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import propensi.a04.wois.model.*;
import propensi.a04.wois.repository.ReservasiDb;
import propensi.a04.wois.service.ReservasiService;
import propensi.a04.wois.service.UserService;
import propensi.a04.wois.service.*;

import java.security.Principal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ReviewController {
    @Qualifier("reservasiServiceImpl")
    @Autowired
    private ReservasiService reservasiService;

    @Qualifier("profileServiceImpl")
    @Autowired
    private ProfileService profileService;

    @Qualifier("userServiceImpl")
    @Autowired
    private UserService userService;

    @Qualifier("vendorServiceImpl")
    @Autowired
    private VendorService vendorService;

    @Qualifier("reviewServiceImpl")
    @Autowired
    private ReviewService reviewService;

    @Qualifier("katalogServiceImpl")
    @Autowired
    private KatalogService katalogService;

    @Qualifier("customerServiceImpl")
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ReservasiDb reservasiDb;


    @GetMapping("/review")
    public String listReview(Model model, Authentication authentication, Principal principal) {
        //cek role user
        String role = "";
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PIC Organizer"))) {
            role = "PIC Organizer";
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_Customer"))) {
            role = "Customer";
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_Super Admin"))) {
            role = "Super Admin";
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_Vendor"))) {
            role = "Vendor";
        }

        //pisahkan listReservasi sesuai dengan role user
        if (role.equals("PIC Organizer")) {
            DecimalFormat df = new DecimalFormat("0.0");
            List<ReviewModel> listActiveReview = new ArrayList<>();
            float rating = 0;
            float activeReviewCount = 0;

            List<ReviewModel> listReview = reviewService.getListReview();

            for (ReviewModel review : listReview) {
                if (review.isActive()) {
                    listActiveReview.add(review);
                    rating += review.getRating();
                    activeReviewCount++;
                }
            }

            if (activeReviewCount != 0) {
                float averageRating = rating / activeReviewCount;
                model.addAttribute("averageRating", df.format(averageRating));
            } else {
                int averageRating = 0;
                model.addAttribute("averageRating", averageRating);
            }

            model.addAttribute("listReview", listActiveReview);

            return "review/viewall-review-pic";
        }
        else if (role.equals("Super Admin")) {
            DecimalFormat df = new DecimalFormat("0.0");
            List<ReviewModel> listActiveReviewadmin = new ArrayList<ReviewModel>();
            float ratingAdmin = 0;
            List<ReviewModel> listReview = reviewService.getListReview();
            for (ReviewModel review: listReview) {
                if (review.isActive()== true){
                    listActiveReviewadmin.add(review);
                    ratingAdmin += review.getRating();
                }
            }
            if (listActiveReviewadmin.size() != 0){
                float averageRating = ratingAdmin / listActiveReviewadmin.size();
                model.addAttribute("averageRating", df.format(averageRating));
            }
            else{
                int averageRating = 0;
                model.addAttribute("averageRating", averageRating);
            }
            model.addAttribute("listReview", listActiveReviewadmin);
            return "review/viewall-review-admin";
        }
        else if (role.equals("Customer")) {
            DecimalFormat df = new DecimalFormat("0.0");
            List<ReviewModel> listActiveReviewCustomer = new ArrayList<ReviewModel>();
            List<ReviewModel> reviewMilikCustomer = new ArrayList<ReviewModel>();
            float ratingCustomer = 0;
            List<ReviewModel> listReview = reviewService.getListReview();
            for (ReviewModel review: listReview) {
                if (review.isActive()== true && !review.getReservasi().getCustomer().getEmail().equals(principal.getName())){
                    listActiveReviewCustomer.add(review);
                    ratingCustomer += review.getRating();
                }
            }
            for (ReviewModel review: listReview) {
                if (review.getReservasi().getCustomer().getEmail().equals(principal.getName())){
                    reviewMilikCustomer.add(review);
                    ratingCustomer += review.getRating();
                }
            }
            if (listActiveReviewCustomer.size() != 0 || reviewMilikCustomer.size() != 0){
                float averageRating = ratingCustomer / (listActiveReviewCustomer.size()+reviewMilikCustomer.size());
                model.addAttribute("averageRating", df.format(averageRating));
            }
            else{
                int averageRating = 0;
                model.addAttribute("averageRating", averageRating);
            }

            model.addAttribute("listReviewAll", listActiveReviewCustomer);
            model.addAttribute("reviewCustomer", reviewMilikCustomer);

            return "review/viewall-review-customer";
        }
        else {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN
            );
        }
    }

    @GetMapping("/review/delete-admin/{idReview}")
    public String hapusReviewForm(@PathVariable Long idReview, Model model, RedirectAttributes redirect) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_Super Admin"))) {
            ReviewModel review = reviewService.getReviewByIdReview(idReview);
            review.setActive(false);
            reviewService.updateStatusReview(review);
            redirect.addFlashAttribute("success", String.format("Review dengan ID " + review.getIdReview() + " atas nama " + review.getReservasi().getCustomer().getNama() + " berhasil dihapus"));
            return "redirect:/review";
        } else {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN
            );
        }
    }

//    @PostMapping("/review/delete-admin/{idReview}")
//    public String submitFormHapus(@PathVariable Long idReview, Model model, RedirectAttributes redirect) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_Super Admin"))) {
//            ReviewModel review = reviewService.getReviewByIdReview(idReview);
//            review.setActive(false);
//            reviewService.updateStatusReview(review);
//            redirect.addFlashAttribute("success", String.format("Review dengan ID " + review.getIdReview() + " atas nama " + review.getReservasi().getCustomer().getNama() + " berhasil dihapus"));
//            return "redirect:/review";
//        } else {
//            throw new ResponseStatusException(
//                    HttpStatus.FORBIDDEN
//            );
//        }
//    }

    @GetMapping("/review/add")
    public String addReviewFormPage(Principal principal, Model model) {
        ReviewModel review = new ReviewModel();
        CustomerModel user = customerService.getCustomerByEmail(principal.getName());

        model.addAttribute("review", review);
        model.addAttribute("user", user);

        return "review/form-add-ulasan";

    }

    @PostMapping(value = "/review/add")
    public String addReviewSubmitPage(@ModelAttribute ReviewModel review, BindingResult result, Principal principal,
                                      RedirectAttributes redirectAttrs) {
        CustomerModel user = customerService.getCustomerByEmail(principal.getName());
        review.setActive(true);
        review.setKomentar(null);

        List<ReservasiModel> daftarReservasi = user.getDaftarReservasi();
        ReservasiModel reservasi = null;

        // Check if there is a reservation with status 4 and a unique reservation ID
        for (ReservasiModel res : daftarReservasi) {
            if (res.getStatus() == 4 && reviewService.getReviewByReservasi(res) == null) {
                reservasi = res;
                break;
            }
        }

        if (reservasi != null) {
            review.setReservasi(reservasi);
            reviewService.addReview(review);
            redirectAttrs.addFlashAttribute("success",
                    String.format("Ulasan dengan ID " + review.getIdReview() + " berhasil ditambahkan"));
            redirectAttrs.addAttribute("idReview", review.getIdReview()); // Add idReservasi to the model
        } else {
            boolean hasfinished = false;
            for (ReservasiModel res : daftarReservasi) {
                if (res.getStatus() == 4) {
                    hasfinished = true;
                    break;
                }
            }

            if (hasfinished) {
                redirectAttrs.addFlashAttribute("error", "Anda tidak bisa menambahkan ulasan.  Anda sudah pernah memberikan ulasan sebelumnya atau status reservasi terbaru anda belum selesai");
            } else {
                redirectAttrs.addFlashAttribute("error", "Anda harus melakukan reservasi dengan status selesai terlebih dahulu.");
            }

            return "redirect:/review/add";
        }

        if (result.hasErrors()) {
            redirectAttrs.addFlashAttribute("error", "The error occurred.");
            return "redirect:/review/add";
        }

        return "redirect:/review";
    }

    @GetMapping("/review/update/{idReview}")
    public String updateReviewForm(@PathVariable("idReview") Long idReview, Model model, Principal principal) {
        // ReviewModel review = reviewService.getReviewById(idReview);
        CustomerModel user = customerService.getCustomerByEmail(principal.getName());
        ReviewModel review = reviewService.getReviewByIdReview(idReview);

        model.addAttribute("review", review);
        model.addAttribute("user", user);

        return "review/form-update-ulasan";
    }

    @PostMapping(value = "/review/update/{idReview}")
    public String updateReviewSubmitPage(@PathVariable("idReview") Long idReview,
                                         @ModelAttribute ReviewModel review,
                                         BindingResult result,
                                         RedirectAttributes redirectAttrs) {
//        ReviewModel review = reviewService.getReviewByIdReview(idReview);

        // Update the review properties
//        review.setReview(updatedReview.getReview());
//        review.setRating(updatedReview.getRating());
        // Update other properties as needed
        List<ReviewModel> listReview = reviewService.getListReview();
        ReservasiModel idReservasi = reviewService.mappingReservasi(listReview).get(idReview);
        // Save the updated review
        review.setReservasi(idReservasi);
        idReservasi.setReview(review);
        review.setActive(true);
        reviewService.ubahReview(review);

        if (result.hasErrors()) {
            redirectAttrs.addFlashAttribute("error", "An error occurred.");
            return "redirect:/review/update/" + idReview;
        }

        redirectAttrs.addFlashAttribute("success", "Ulasan dengan ID " + review.getIdReview() + " berhasil diperbarui");
        redirectAttrs.addAttribute("idReview", review.getIdReview());

        return "redirect:/review";
    }

    @GetMapping(value = "/review/delete/{idReview}")
    public String deleteReview(@PathVariable("idReview") Long idReview, RedirectAttributes redirectAttrs) {
        // Retrieve the review by its id
        ReviewModel review = reviewService.getReviewByIdReview(idReview);

        // Check if the review exists
        if (review != null) {
            // Delete the review
            reviewService.deleteReview(idReview);

            redirectAttrs.addFlashAttribute("success", "Ulasan dengan ID " + idReview + " berhasil dihapus");
        } else {
            redirectAttrs.addFlashAttribute("error", "Ulasan dengan ID " + idReview + " tidak ditemukan");
        }

        return "redirect:/review";
    }

}

