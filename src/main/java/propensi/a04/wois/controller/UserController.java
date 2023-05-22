package propensi.a04.wois.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import propensi.a04.wois.model.KategoriModel;
import propensi.a04.wois.model.PicModel;
import propensi.a04.wois.model.UserModel;
import propensi.a04.wois.model.VendorModel;
import propensi.a04.wois.service.KategoriService;
import propensi.a04.wois.service.PicService;
import propensi.a04.wois.service.UserService;
import propensi.a04.wois.service.VendorService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {
    @Qualifier("userServiceImpl")
    @Autowired
    private UserService userService;

    @Qualifier("picServiceImpl")
    @Autowired
    private PicService picService;

    @Qualifier("vendorServiceImpl")
    @Autowired
    private VendorService vendorService;

    @Qualifier("kategoriServiceImpl")
    @Autowired
    private KategoriService kategoriService;

    @GetMapping("/user")
    public String listUser(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_Super Admin"))) {
            List<UserModel> listUser = userService.getListUser();
            model.addAttribute("listUser", listUser);
            return "user/viewall-user";
        }
        else {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN
            );
        }
    }

    @GetMapping(value = "/user/vendor/tambah")
    public String addVendorForm(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_Super Admin"))) {
            var vendor = new VendorModel();
            String role = "Vendor";
            List<KategoriModel> listKategori = kategoriService.getListKategori();
            model.addAttribute("vendor", vendor);
            model.addAttribute("role", role);
            model.addAttribute("listKategori", listKategori);
            return "user/form-add-vendor-user";
        }
        else {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN
            );
        }
    }

    @PostMapping(value = "/user/vendor/tambah", params = {"save"})
    public String addVendorSubmit(@ModelAttribute VendorModel vendor, RedirectAttributes redirect, BindingResult result, Model model) {
        UserModel existUser = userService.getUserByEmail(vendor.getEmail());

        if(existUser != null && existUser.getEmail() != null && !existUser.getEmail().isEmpty()) {
            result.rejectValue("email", null, "Email sudah terpakai");
        }

        if(result.hasErrors()){
            model.addAttribute("existUser", existUser);
            model.addAttribute("vendor", vendor);
            return "user/form-add-vendor-user";
        }

        userService.addVendorUser(vendor);
        redirect.addFlashAttribute("success", String.format("User bernama " + vendor.getNama() + " dengan role \'" + vendor.getRole() + "\' berhasil ditambahkan"));
        return "redirect:/user";
    }

    @GetMapping(value = "/user/pic/tambah")
    public String addPicForm(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_Super Admin"))) {
            var pic = new PicModel();
            String role = "PIC Organizer";
            model.addAttribute("pic", pic);
            model.addAttribute("role", role);
            return "user/form-add-pic-user";
        }
        else {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN
            );
        }
    }

    @PostMapping(value = "/user/pic/tambah", params = {"save"})
    public String addPicSubmit(@ModelAttribute PicModel pic, RedirectAttributes redirect, BindingResult result, Model model) {
        UserModel existUser = userService.getUserByEmail(pic.getEmail());

        if(existUser != null && existUser.getEmail() != null && !existUser.getEmail().isEmpty()) {
            result.rejectValue("email", null, "Email sudah terpakai");
        }

        if(result.hasErrors()){
            model.addAttribute("existUser", existUser);
            model.addAttribute("pic", pic);
            return "user/form-add-pic-user";
        }

        userService.addPicUser(pic);
        redirect.addFlashAttribute("success", String.format("User bernama " + pic.getNama() + " dengan role \'" + pic.getRole() + "\' berhasil ditambahkan"));
        return "redirect:/user";
    }

    @GetMapping("/user/hapus/{uuid}")
    public String deleteUserSubmit(@PathVariable String uuid, Model model, RedirectAttributes redirect) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_Super Admin"))) {
            UserModel user = userService.getUserByUuid(uuid);
            userService.deleteUser(uuid);
            redirect.addFlashAttribute("success", String.format("User bernama " + user.getNama() + " dengan role \'" + user.getRole() + "\' berhasil dihapus"));
            return "redirect:/user";
        }
        else {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN
            );
        }
    }

//    @GetMapping("/user/hapus/{uuid}")
//    public String deleteUserConfirm(@PathVariable String uuid, Model model) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_Super Admin"))) {
//            UserModel user = userService.getUserByUuid(uuid);
//            model.addAttribute("user", user);
//            return "user/confirm-delete-user";
//        }
//        else {
//            throw new ResponseStatusException(
//                    HttpStatus.FORBIDDEN
//            );
//        }
//    }
//
//    @PostMapping("/user/hapus/{uuid}")
//    public String deleteUser(@PathVariable String uuid, Model model, RedirectAttributes redirect) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_Super Admin"))) {
//            UserModel user = userService.getUserByUuid(uuid);
//            userService.deleteUser(uuid);
//            redirect.addFlashAttribute("success", String.format("User bernama " + user.getNama() + " dengan role \'" + user.getRole() + "\' berhasil dihapus"));
//            return "redirect:/user";
//        }
//        else {
//            throw new ResponseStatusException(
//                    HttpStatus.FORBIDDEN
//            );
//        }
//    }

}
