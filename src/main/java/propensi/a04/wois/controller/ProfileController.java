package propensi.a04.wois.controller;

import org.apache.catalina.User;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.web.bind.annotation.*;
import propensi.a04.wois.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import propensi.a04.wois.service.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/profile")

public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PicService picService;

    @Autowired
    private VendorService vendorService;

    @Autowired
    private AdminService adminService;

    //View User Profile
    @GetMapping(value = "/")
    public String viewProfile(Principal principal, Model model){
       UserModel user= profileService.getUserByEmail(principal.getName());
       String nama = principal.getName();
       model.addAttribute("user", user);
        model.addAttribute("nama", nama);
       return "Fitur Profile/view-profile";
    }

    // Get Mapping Ubah Password Customer
    @GetMapping("/{id}/ubah-password-customer")
    public String updatePassword(@PathVariable("id") String idUser, Model model) {
        CustomerModel user = customerService.getCustomerByUuid(idUser);
        model.addAttribute("user", user);
        return "Fitur Profile/form-update-password";
    }

    // Post Mapping Ubah Password Customer
    @PostMapping("/{id}/ubah-password-customer")
    public String updatePasswordSubmitPage(@RequestParam("oldPassword") String oldPassword,@RequestParam("newPassword") String newPassword, @RequestParam("confirmPassword") String confirmPassword, @ModelAttribute CustomerModel user, Model model, RedirectAttributes redirect, Principal principal) {
        String nowPassword = customerService.getCustomerByEmail(principal.getName()).getPassword();
        boolean isMatched = profileService.matchPassword(oldPassword, nowPassword);

        if (isMatched == true){
            if (newPassword.equals(confirmPassword)) {
                if (oldPassword.equals(newPassword)) {
                    redirect.addFlashAttribute("errorMessage3", "Password lama tidak boleh sama dengan password baru. Silakan coba lagi");
                    return "redirect:/profile/{id}/ubah-password-customer";
                }
                else{
                    UserModel userModel = profileService.updatePassword(user, confirmPassword);
                    //profileService.encryptPassword(userModel);
                    redirect.addFlashAttribute("successMessage", "Password berhasil diperbaharui. Silakan login ulang menggunakan password yang baru");
                    return "redirect:/login";
                }
            }
            else {
                redirect.addFlashAttribute("errorMessage", "Password baru tidak sesuai dengan konfirmasi password baru");
                return "redirect:/profile/{id}/ubah-password-customer";
            }
        }

        else {
            redirect.addFlashAttribute("errorMessage2", "Password lama yang anda masukkan salah. Silakan coba lagi");
            return "redirect:/profile/{id}/ubah-password-customer";
        }




    }

    // Get Mapping Ubah Password PIC Organizer
    @GetMapping("/{id}/ubah-password-pic")
    public String updatePasswordPic(@PathVariable("id") String idUser, Model model) {
        PicModel user = picService.getPicByUuid(idUser);
        model.addAttribute("user", user);
        return "Fitur Profile/form-update-password-pic";
    }

    // Post Mapping Ubah Password PIC Organizer
    @PostMapping("/{id}/ubah-password-pic")
    public String updatePasswordPicSubmitPage(@RequestParam("oldPassword") String oldPassword,@RequestParam("newPassword") String newPassword, @RequestParam("confirmPassword") String confirmPassword, @ModelAttribute PicModel user, Model model, RedirectAttributes redirect, Principal principal) {
        String nowPasswordPic = picService.getPicByEmail(principal.getName()).getPassword();
        boolean isMatchedPic = profileService.matchPassword(oldPassword, nowPasswordPic);

        if (isMatchedPic == true){
            if (newPassword.equals(confirmPassword)) {
                if (oldPassword.equals(newPassword)) {
                    redirect.addFlashAttribute("errorMessage3", "Password lama tidak boleh sama dengan password baru. Silakan coba lagi");
                    return "redirect:/profile/{id}/ubah-password-pic";
                }
                else{
                    UserModel userModel = profileService.updatePassword(user, confirmPassword);
                    //profileService.encryptPassword(userModel);
                    redirect.addFlashAttribute("successMessage", "Password berhasil diperbaharui. Silakan login ulang menggunakan password yang baru");
                    return "redirect:/login";
                }
            }
            else {
                redirect.addFlashAttribute("errorMessage", "Password baru tidak sesuai dengan konfirmasi password baru");
                return "redirect:/profile/{id}/ubah-password-pic";
            }
        }

        else {
            redirect.addFlashAttribute("errorMessage2", "Password lama yang anda masukkan salah. Silakan coba lagi");
            return "redirect:/profile/{id}/ubah-password-pic";
        }

    }

    // Get Mapping Ubah Password Vendor
    @GetMapping("/{id}/ubah-password-vendor")
    public String updatePasswordVendor(@PathVariable("id") String idUser, Model model) {
        VendorModel user = vendorService.getVendorByUuid(idUser);
        model.addAttribute("user", user);
        return "Fitur Profile/form-update-password-vendor";
    }

    // Post Mapping Ubah Password Vendor
    @PostMapping("/{id}/ubah-password-vendor")
    public String updatePasswordVendorSubmitPage(@RequestParam("oldPassword") String oldPassword,@RequestParam("newPassword") String newPassword, @RequestParam("confirmPassword") String confirmPassword, @ModelAttribute VendorModel user, Model model, RedirectAttributes redirect, Principal principal) {
        String nowPasswordVendor = vendorService.getVendorByEmail(principal.getName()).getPassword();
        boolean isMatchedVendor = profileService.matchPassword(oldPassword, nowPasswordVendor);

        if (isMatchedVendor == true){
            if (newPassword.equals(confirmPassword)) {
                if (oldPassword.equals(newPassword)) {
                    redirect.addFlashAttribute("errorMessage3", "Password lama tidak boleh sama dengan password baru. Silakan coba lagi");
                    return "redirect:/profile/{id}/ubah-password-vendor";
                }
                else{
                    UserModel userModel = profileService.updatePassword(user, confirmPassword);
                    //profileService.encryptPassword(userModel);
                    redirect.addFlashAttribute("successMessage", "Password berhasil diperbaharui. Silakan login ulang menggunakan password yang baru");
                    return "redirect:/login";
                }
            }
            else {
                redirect.addFlashAttribute("errorMessage", "Password baru tidak sesuai dengan konfirmasi password baru");
                return "redirect:/profile/{id}/ubah-password-vendor";
            }
        }

        else {
            redirect.addFlashAttribute("errorMessage2", "Password lama yang anda masukkan salah. Silakan coba lagi");
            return "redirect:/profile/{id}/ubah-password-vendor";
        }

    }

    // Get Mapping Ubah Password Super Admin
    @GetMapping("/{id}/ubah-password-admin")
    public String updatePasswordAdmin(@PathVariable("id") String idUser, Model model) {
        AdminModel user = adminService.getAdminByUuid(idUser);
        model.addAttribute("user", user);
        return "Fitur Profile/form-update-password-admin";
    }

    // Post Mapping Ubah Password Super Admin
    @PostMapping("/{id}/ubah-password-admin")
    public String updatePasswordAdminSubmitPage(@RequestParam("oldPassword") String oldPassword,@RequestParam("newPassword") String newPassword, @RequestParam("confirmPassword") String confirmPassword, @ModelAttribute AdminModel user, Model model, RedirectAttributes redirect, Principal principal) {

        String nowPasswordAdmin = adminService.getAdminByEmail(principal.getName()).getPassword();
        boolean isMatchedAdmin = profileService.matchPassword(oldPassword, nowPasswordAdmin);

        if (isMatchedAdmin == true){
            if (newPassword.equals(confirmPassword)) {
                if (oldPassword.equals(newPassword)) {
                    redirect.addFlashAttribute("errorMessage3", "Password lama tidak boleh sama dengan password baru. Silakan coba lagi");
                    return "redirect:/profile/{id}/ubah-password-admin";
                }
                else{
                    UserModel userModel = profileService.updatePassword(user, confirmPassword);
                    //profileService.encryptPassword(userModel);
                    redirect.addFlashAttribute("successMessage", "Password berhasil diperbaharui. Silakan login ulang menggunakan password yang baru");
                    return "redirect:/login";
                }
            }
            else {
                redirect.addFlashAttribute("errorMessage", "Password baru tidak sesuai dengan konfirmasi password baru");
                return "redirect:/profile/{id}/ubah-password-admin";
            }
        }

        else {
            redirect.addFlashAttribute("errorMessage2", "Password lama yang anda masukkan salah. Silakan coba lagi");
            return "redirect:/profile/{id}/ubah-password-admin";
        }


    }

    // Get Mapping Ubah Profile Customer
    @GetMapping("/{id}/ubah-profile-customer")
    public String updateProfileCustomer(@PathVariable("id") String idUser, Model model) {
        CustomerModel user = customerService.getCustomerByUuid(idUser);
        model.addAttribute("user", user);

        return "Fitur Profile/form-update-profile";
    }

    // Post Mapping Ubah Profile Customer
    @PostMapping("/{id}/ubah-profile-customer")
    public String updateProfileCustomerSubmitPage(@ModelAttribute CustomerModel customerModel, Principal principal, Model model, RedirectAttributes redirect) {
        UserModel userModel = profileService.updateProfile(customerModel);
        if (principal.getName().equals(userModel.getEmail())){
            model.addAttribute("nama", userModel.getNama());
            return "Fitur Profile/success-update-profile";
        }
        else{
            redirect.addFlashAttribute("successEmail", "Email berhasil diperbaharui. Silakan login ulang dengan menggunakan email yang baru.");
            return "redirect:/login";
        }


    }

    // Get Mapping Ubah Profile PIC Organizer
    @GetMapping("/{id}/ubah-profile-pic")
    public String updateProfilePic(@PathVariable("id") String idUser, Model model) {
        PicModel user = picService.getPicByUuid(idUser);
        model.addAttribute("user", user);

        return "Fitur Profile/form-update-profile-pic";
    }

    // Post Mapping Ubah Profile PIC Organizer
    @PostMapping("/{id}/ubah-profile-pic")
    public String updateProfilePicSubmitPage(@ModelAttribute PicModel picModel, Principal principal, Model model, RedirectAttributes redirect) {
        UserModel userModel = profileService.updateProfile(picModel);
        if (principal.getName().equals(userModel.getEmail())){
            model.addAttribute("nama", userModel.getNama());
            return "Fitur Profile/success-update-profile";
        }
        else{
            redirect.addFlashAttribute("successEmail", "Email berhasil diperbaharui. Silakan login ulang dengan menggunakan email yang baru.");
            return "redirect:/login";
        }


    }

    // Get Mapping Ubah Profile Vendor
    @GetMapping("/{id}/ubah-profile-vendor")
    public String updateProfileVendor(@PathVariable("id") String idUser, Model model) {
        VendorModel user = vendorService.getVendorByUuid(idUser);
        model.addAttribute("user", user);

        return "Fitur Profile/form-update-profile-vendor";
    }

    // Post Mapping Ubah Profile Vendor
    @PostMapping("/{id}/ubah-profile-vendor")
    public String updateProfileVendorSubmitPage(@ModelAttribute VendorModel vendorModel, Principal principal, Model model, RedirectAttributes redirect) {
        UserModel userModel = profileService.updateProfile(vendorModel);
        if (principal.getName().equals(userModel.getEmail())){
            model.addAttribute("nama", userModel.getNama());
            return "Fitur Profile/success-update-profile";
        }
        else{
            redirect.addFlashAttribute("successEmail", "Email berhasil diperbaharui. Silakan login ulang dengan menggunakan email yang baru.");
            return "redirect:/login";
        }


    }

    // Get Mapping Ubah Profile Super Admin
    @GetMapping("/{id}/ubah-profile-admin")
    public String updateProfileAdmin(@PathVariable("id") String idUser, Model model) {
        AdminModel user = adminService.getAdminByUuid(idUser);
        System.out.println(user.getUuid());
        model.addAttribute("user", user);

        return "Fitur Profile/form-update-profile-admin";
    }

    // Post Mapping Ubah Profile Super Admin
    @PostMapping("/{id}/ubah-profile-admin")
    public String updateProfileAdminSubmitPage(@ModelAttribute AdminModel adminModel, Principal principal, Model model, RedirectAttributes redirect) {
        UserModel userModel = profileService.updateProfile(adminModel);
        if (principal.getName().equals(userModel.getEmail())){
            model.addAttribute("nama", userModel.getNama());
            return "Fitur Profile/success-update-profile";
        }
        else{
            redirect.addFlashAttribute("successEmail", "Email berhasil diperbaharui. Silakan login ulang dengan menggunakan email yang baru.");
            return "redirect:/login";
        }


    }

}
