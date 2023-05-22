package propensi.a04.wois.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import propensi.a04.wois.model.KatalogModel;
import propensi.a04.wois.model.KategoriModel;
import propensi.a04.wois.model.VendorModel;
import propensi.a04.wois.service.KatalogService;
import propensi.a04.wois.service.KategoriService;
import propensi.a04.wois.service.VendorService;

import java.util.List;

@Controller
public class KatalogController {
    @Qualifier("katalogServiceImpl")
    @Autowired
    private KatalogService katalogService;
    @Qualifier("vendorServiceImpl")
    @Autowired
    private VendorService vendorService;

    @Qualifier("kategoriServiceImpl")
    @Autowired
    private KategoriService kategoriService;

    @GetMapping("/katalog/add")
    public String addKatalogFormPage(Model model) {
        var katalog = new KatalogModel();

        List<VendorModel> listVendor = vendorService.getListVendor();
        List<KategoriModel> listKategori = kategoriService.getListKategori();

        model.addAttribute("katalog", katalog);
        model.addAttribute("listVendor",listVendor);
        model.addAttribute("listKategori",listKategori);

        return "katalog/form-add-katalog";
    }

    @PostMapping("/katalog/add")
    public String addKatalogSubmitPage(@RequestParam("file") MultipartFile file, KatalogModel katalog,
                                       BindingResult result, RedirectAttributes redirectAttrs)
    {

        katalogService.addKatalog(file,katalog);
        if (result.hasErrors()) {
            redirectAttrs.addFlashAttribute("error", "The error occurred.");
            return "redirect:/katalog/add";
        }

        redirectAttrs.addFlashAttribute("success",
                String.format("Katalog dengan Vendor %s berhasil ditambahkan ", katalog.getVendor().getNama()));


        return "redirect:/katalog/add";
    }


    @GetMapping("/katalog-{kategori}")
    public String listKatalog(@PathVariable String kategori, Model model){
        List<KatalogModel> finalKatalog = katalogService.getListKatalog(kategori);

        model.addAttribute("kategori",kategori);
        model.addAttribute("finalKatalog",finalKatalog);
        model.addAttribute("jumlahKatalog", finalKatalog.size());
        return "katalog/viewall-katalog";
    }

    @GetMapping("/katalog/detail/{id}")
    public String detailKatalog(@PathVariable Long id, Model model){
        KatalogModel katalog = katalogService.getKatalogById(id);

        model.addAttribute("katalog", katalog);
        model.addAttribute("kategori", katalog.getKategori().getKategori());

        return "katalog/detail-katalog";
    }

    @GetMapping("/katalog/delete/{id}")
    public String deleteKatalog(@PathVariable Long id, Model model){
        KatalogModel katalog = katalogService.getKatalogById(id);
        model.addAttribute("katalog", katalog);
        katalogService.deleteKatalog(katalog);

        return "katalog/delete-katalog";
    }

    @GetMapping("/katalog/update/{id}")
    public String updateKatalogFormPage(@PathVariable Long id, Model model){
        KatalogModel updateKatalog = katalogService.getKatalogById(id);

        List<VendorModel> listVendor = vendorService.getListVendorUpdate(id);
        List<KategoriModel> listKategori = kategoriService.getListKategori();

        model.addAttribute("listVendor",listVendor);
        model.addAttribute("listKategori",listKategori);
        model.addAttribute("updateKatalog", updateKatalog);
        model.addAttribute("kategori", updateKatalog.getKategori().getKategori());

        return "katalog/form-update-katalog";
    }

    @PostMapping("/katalog/update/{id}")
    public String updateKatalogSubmitPage(@RequestParam("file") MultipartFile file, KatalogModel katalog,
                                       BindingResult result, RedirectAttributes redirectAttrs)
    {

        katalogService.updateKatalog(file, katalog);

        if (result.hasErrors()) {
            redirectAttrs.addFlashAttribute("error", "The error occurred.");
            return "redirect:/katalog/add";
        }

        redirectAttrs.addFlashAttribute("success",
                String.format("Katalog dengan Vendor %s berhasil diubah ", katalog.getVendor().getNama()));

        return "redirect:/katalog/add";
    }
}

