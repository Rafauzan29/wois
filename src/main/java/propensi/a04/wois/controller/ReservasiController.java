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
import propensi.a04.wois.model.ReservasiModel;
import propensi.a04.wois.model.UserModel;
import propensi.a04.wois.model.VendorModel;
import propensi.a04.wois.repository.ReservasiDb;
import propensi.a04.wois.service.ReservasiService;
import propensi.a04.wois.service.UserService;
import propensi.a04.wois.model.CustomerModel;
import propensi.a04.wois.service.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ReservasiController {
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

    @Qualifier("katalogServiceImpl")
    @Autowired
    private KatalogService katalogService;

    @Qualifier("customerServiceImpl")
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ReservasiDb reservasiDb;

    @GetMapping("/reservasi")
    public String listReservasi(Model model, Authentication authentication, Principal principal) {
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
            List<ReservasiModel> listReservasi = reservasiService.getListReservasi();
            model.addAttribute("listReservasi", listReservasi);
            model.addAttribute("role", role);
            return "reservasi/viewall-reservasi-pic";
        }
        else if (role.equals("Customer")) {
            List<ReservasiModel> listReservasi = reservasiService.getListReservasi();
            CustomerModel user = customerService.getCustomerByEmail(principal.getName());
            List<ReservasiModel> listReservasiCustomer = new ArrayList<>();
            for(ReservasiModel r : listReservasi) {
                if (r.getCustomer().getUuid().equals(user.getUuid())) {
                    listReservasiCustomer.add(r);
                }
            }
            model.addAttribute("listReservasiCustomer", listReservasiCustomer);
            model.addAttribute("role", role);
            return "reservasi/viewall-reservasi-customer";
        }
        else {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN
            );
        }
    }

    @GetMapping("/reservasi/{idReservasi}")
    public String detailReservasi(@PathVariable Long idReservasi, Model model, Authentication authentication) {
        //cek role user
        String role = "";
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PIC Organizer"))) {
            role = "PIC Organizer";
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_Customer"))) {
            role = "Customer";
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_Super Admin"))) {
            role = "Super Admin";
        }

        ReservasiModel reservasi = reservasiService.getReservasiByIdReservasi(idReservasi);
        if (role.equals("PIC Organizer")) {
            //cari nama vendor
            for (VendorModel v : reservasi.getListVendorReservasi()) {
                if (v.getKategori().getKategori().equals("Catering")) {
                    model.addAttribute("vendorCatering", v.getNama());
                } else if (v.getKategori().getKategori().equals("Entertainment")) {
                    model.addAttribute("vendorEntertainment", v.getNama());
                } else if (v.getKategori().getKategori().equals("Photography")) {
                    model.addAttribute("vendorPhotography", v.getNama());
                } else if (v.getKategori().getKategori().equals("Attire")) {
                    model.addAttribute("vendorAttire", v.getNama());
                } else if (v.getKategori().getKategori().equals("Decoration")) {
                    model.addAttribute("vendorDecoration", v.getNama());
                } else if (v.getKategori().getKategori().equals("Wedding Organizer")) {
                    model.addAttribute("vendorWO", v.getNama());
                }
            }
            model.addAttribute("reservasi", reservasi);
            model.addAttribute("role", role);
            return "reservasi/view-reservasi-pic";
        }
        else if (role.equals("Customer")) {
            //cari nama vendor
            for (VendorModel v : reservasi.getListVendorReservasi()) {
                if (v.getKategori().getKategori().equals("Catering")) {
                    model.addAttribute("vendorCatering", v.getNama());
                } else if (v.getKategori().getKategori().equals("Entertainment")) {
                    model.addAttribute("vendorEntertainment", v.getNama());
                } else if (v.getKategori().getKategori().equals("Photography")) {
                    model.addAttribute("vendorPhotography", v.getNama());
                } else if (v.getKategori().getKategori().equals("Attire")) {
                    model.addAttribute("vendorAttire", v.getNama());
                } else if (v.getKategori().getKategori().equals("Decoration")) {
                    model.addAttribute("vendorDecoration", v.getNama());
                } else if (v.getKategori().getKategori().equals("Wedding Organizer")) {
                    model.addAttribute("vendorWO", v.getNama());
                }
            }
            model.addAttribute("reservasi", reservasi);
            model.addAttribute("role", role);
            return "reservasi/view-reservasi-customer";
        }
        else {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN
            );
        }
    }

    @GetMapping("/reservasi/update/{idReservasi}")
    public String updateReservasiFormPage(@PathVariable Long idReservasi, Model model){

        ReservasiModel updatedReservasi = reservasiService.getReservasiByIdReservasi(idReservasi);
//        CustomerModel customer = updatedReservasi.getCustomer();
////        customer.setReservasi(updatedReservasi);
        System.out.println("get: " + updatedReservasi.getListVendorReservasi().get(1).getNama());

        List<VendorModel> listVendor = vendorService.getListVendorAll();
//        CustomerModel cust = updatedReservasi.getCustomer();
//        System.out.println("get: " + cust.getEmail());
//        System.out.println("get: " + cust.toString());
//        CustomerModel customer = updatedReservasi.getCustomer();
//        System.out.println("get: " + customer.getEmail());
//        System.out.println("cust: " + updatedReservasi.getCustomer().getNama());

//        String checkvendor = updatedReservasi.getListVendorReservasi().get(0).getNama();
//        System.out.println(checkvendor);
//        List<VendorModel> listVendorEx = updatedReservasi.getListVendorReservasi();
//        for (VendorModel vendor: listVendorEx){
//            System.out.println(vendor.getNama());
//        }
//        System.out.println("get: " + updatedReservasi.getCustomer().getUuid());
//        System.out.println(listVendorExisting.size());
//        for (VendorModel vendor: listVendorExisting){
//            System.out.println(vendor.getKategori().getKategori() + " " + vendor.getNama());
//        }


        model.addAttribute("listVendor",listVendor);
        model.addAttribute("updatedReservasi", updatedReservasi);
//        model.addAttribute("customer", customer);

        return "reservasi/form-update-reservasi";
    }

    @PostMapping(value = "/reservasi/update/{idReservasi}")
    public String updateReservasiSubmitPage(@PathVariable Long idReservasi, @ModelAttribute ReservasiModel reservasi, BindingResult result,
                                            RedirectAttributes redirectAttrs){
        List<ReservasiModel> listReservasi = reservasiService.getListReservasi();
        CustomerModel idCustomer = reservasiService.mappingHash(listReservasi).get(idReservasi);
//        CustomerModel customer = reservasi.getCustomer();
        idCustomer.getDaftarReservasi().add(reservasi);
        reservasi.setCustomer(idCustomer);
//        System.out.println(reservasi);
//        System.out.println("post status: " + reservasi.getStatus());

//        CustomerModel custPost = reservasi.getCustomer();
//        custPost.setReservasi(reservasi);
//        System.out.println(reservasi.getIdReservasi());
//        System.out.println("post: " + reservasi.getCustomer().getEmail());

//        System.out.println("post: " + reservasi.getCustomer());
//        for (VendorModel vendor :
//                reservasi.getListVendorReservasi()) {
//            vendor.setListReservasi();
//        }
//        for (VendorModel vendor :
//                reservasi.getListVendorReservasi()) {
//            vendor.getListReservasi().add(reservasi);
//        }
        List<VendorModel> lstVendor = reservasi.getListVendorReservasi();
        for (int i=5; i<lstVendor.size(); i++){
            lstVendor.get(i).getListReservasi().add(reservasi);
        }
        System.out.println(lstVendor.size());

        reservasiService.ubahReservasi(reservasi);

        if (result.hasErrors()) {
            redirectAttrs.addFlashAttribute("error", "The error occurred.");
            return "redirect:/reservasi";
        }

        redirectAttrs.addFlashAttribute("success",
                String.format("Reservasi dengan Id %s berhasil diubah ", reservasi.getIdReservasi()));

        return "redirect:/reservasi";
    }
    
    @GetMapping("/reservasi/hapus/{idReservasi}")
    public String hapusReservasiConfirm(@PathVariable Long idReservasi, Model model, RedirectAttributes redirect) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_PIC Organizer"))) {
            ReservasiModel reservasi = reservasiService.getReservasiByIdReservasi(idReservasi);
            reservasiService.hapusReservasi(idReservasi);
            redirect.addFlashAttribute("success", String.format("Reservasi dengan ID " + reservasi.getIdReservasi() + " atas nama " + reservasi.getCustomer().getNama() + " berhasil dihapus"));
            return "redirect:/reservasi";
        } else {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN
            );
        }
    }

//    @GetMapping("/reservasi/hapus/{idReservasi}")
//    public String hapusReservasiConfirm(@PathVariable Long idReservasi, Model model) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_PIC Organizer"))) {
//            ReservasiModel reservasi = reservasiService.getReservasiByIdReservasi(idReservasi);
//            model.addAttribute("reservasi", reservasi);
//            return "reservasi/confirm-delete-reservasi-pic";
//        } else {
//            throw new ResponseStatusException(
//                    HttpStatus.FORBIDDEN
//            );
//        }
//    }

//    @PostMapping("/reservasi/hapus/{idReservasi}")
//    public String hapusReservasi(@PathVariable Long idReservasi, Model model, RedirectAttributes redirect) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_PIC Organizer"))) {
//            ReservasiModel reservasi = reservasiService.getReservasiByIdReservasi(idReservasi);
//            reservasiService.hapusReservasi(idReservasi);
//            redirect.addFlashAttribute("success", String.format("Reservasi dengan ID " + reservasi.getIdReservasi() + " atas nama " + reservasi.getCustomer().getNama() + " berhasil dihapus"));
//            return "redirect:/reservasi";
//        }
//        else {
//            throw new ResponseStatusException(
//                    HttpStatus.FORBIDDEN
//            );
//        }
//    }

    @GetMapping("/reservasi/add")
    public String addReservasiFormPage(Principal principal, Model model) {
        ReservasiModel reservasi = new ReservasiModel();
        // UserModel user = userService.getUserByEmail(principal.getName());
        CustomerModel user = customerService.getCustomerByEmail(principal.getName());
        // System.out.println("hasil : " + user.getNama());

        // List<KatalogModel> listAllKatalog = katalogService.getListKatalog(kategori);

        List<VendorModel> listVendor = vendorService.getListVendorAll();

        model.addAttribute("reservasi", reservasi);
        model.addAttribute("user", user);
        model.addAttribute("listVendor", listVendor);
        // model.addAttribute("listAllKatalog",listAllKatalog);
        // model.addAttribute("finalKatalog",finalKatalog.getKategori().getKategori());

        return "reservasi/form-add-reservasi";
    }

    @PostMapping(value = "/reservasi/add")
    public String addReservasiSubmitPage(@ModelAttribute ReservasiModel reservasi, Principal principal, BindingResult result,
                                         RedirectAttributes redirectAttrs) {

        CustomerModel user = customerService.getCustomerByEmail(principal.getName());
        reservasi.setStatus(1);
        user.getDaftarReservasi().add(reservasi);
        // System.out.println("hasil2 : " + user.getNama());
        reservasi.setCustomer(user);
        for (VendorModel vendor :
                reservasi.getListVendorReservasi()) {
            vendor.getListReservasi().add(reservasi);
        }
        // vendorCatering.getListReservasi().add(reservasi);
        reservasiService.addReservasi(reservasi);

        if (result.hasErrors()) {
            redirectAttrs.addFlashAttribute("error", "The error occurred.");
            return "redirect:/reservasi/add";
        }

        redirectAttrs.addFlashAttribute("success",
        String.format("Reservasi dengan ID " + reservasi.getIdReservasi() + " atas nama " + reservasi.getCustomer().getNama() + " berhasil ditambahkan"));
        redirectAttrs.addAttribute("idReservasi", reservasi.getIdReservasi()); // Add idReservasi to the model

        return "redirect:/reservasi/{idReservasi}";
    }

    @GetMapping("/booking")
    public String listReservasi(Principal principal, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_Vendor"))) {
            UserModel user = profileService.getUserByEmail(principal.getName());
            // coba pakai nya vendor model ngaruh apa ngga untuk ambil user
            List<ReservasiModel> listReservasi = reservasiService.getListReservasi();
            List<ReservasiModel> listReservasiVendor = new ArrayList<ReservasiModel>();

            for (ReservasiModel reservasi : listReservasi) {
                if (reservasi.getListVendorReservasi().contains(user)) {
                    listReservasiVendor.add(reservasi);
                }
            }
            model.addAttribute("listReservasiVendor", listReservasiVendor);
            return "reservasi/viewall-reservasi-vendor";
        } else {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN
            );
        }
    }

    @GetMapping("/reservasi/reservasi-for-vendor")
    public String listReservasiVendor(Principal principal, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_Vendor"))) {
            UserModel user= profileService.getUserByEmail(principal.getName());
            // coba pakai nya vendor model ngaruh apa ngga untuk ambil user
            List<ReservasiModel> listReservasi = reservasiService.getListReservasi();
            List<ReservasiModel> listReservasiVendor = new ArrayList<ReservasiModel>();

            for (ReservasiModel reservasi : listReservasi){
                if (reservasi.getListVendorReservasi().contains(user)){
                    listReservasiVendor.add(reservasi);
                }
            }
            model.addAttribute("listReservasiVendor", listReservasiVendor);
            return "reservasi/viewall-reservasi-vendor";
        }
        else {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN
            );
        }
    }

    @GetMapping("/reservasi/reservasi-for-vendor/{idReservasi}")
    public String viewDetailReservasi(@PathVariable(value = "idReservasi") Long id, Model model){
        ReservasiModel reservasi = reservasiService.getReservasiByIdReservasi(id);
        for (VendorModel v : reservasi.getListVendorReservasi()) {
            if (v.getKategori().getKategori().equals("Catering")) {
                model.addAttribute("vendorCatering", v.getNama());
            } else if (v.getKategori().getKategori().equals("Entertainment")) {
                model.addAttribute("vendorEntertainment", v.getNama());
            } else if (v.getKategori().getKategori().equals("Photography")) {
                model.addAttribute("vendorPhotography", v.getNama());
            } else if (v.getKategori().getKategori().equals("Attire")) {
                model.addAttribute("vendorAttire", v.getNama());
            } else if (v.getKategori().getKategori().equals("Decoration")) {
                model.addAttribute("vendorDecoration", v.getNama());
//                } else if (v.getKategori().getKategori().equals("Wedding Organizer")) {
//                    model.addAttribute("vendorWO", v.getNama());
            }
        }
        model.addAttribute("reservasi", reservasi);
        return "reservasi/view-detail-reservasi";
    }

}

