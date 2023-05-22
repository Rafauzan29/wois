package propensi.a04.wois.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import propensi.a04.wois.model.ReservasiModel;
import propensi.a04.wois.service.KatalogService;
import propensi.a04.wois.service.ReservasiService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ChartController {
    @Qualifier("reservasiServiceImpl")
    @Autowired
    private ReservasiService reservasiService;


    @GetMapping("/grafik")
    public String showGrafik(Model model){
        List<ReservasiModel> listReservasi = reservasiService.getListReservasi();
        List<ReservasiModel> listCompletedReservasi = reservasiService.getListCompletedReservasi();
        List<ReservasiModel> listSoftbookReservasi = reservasiService.getListSoftbookReservasi();
        List<ReservasiModel> listDP1Reservasi = reservasiService.getListDP1Reservasi();
        List<ReservasiModel> listDP2Reservasi = reservasiService.getListDP2Reservasi();

        Map<String, List<Integer>> mappingBulanTahun = new HashMap<>();
        for(ReservasiModel reservasi : listCompletedReservasi){
            if(mappingBulanTahun.containsKey(Integer.toString(reservasi.getTanggal().getYear()))){
                mappingBulanTahun.get(Integer.toString(reservasi.getTanggal().getYear())).add(reservasi.getTanggal().getMonthValue());
            } else {
                List<Integer> listMonth = new ArrayList<>();
                listMonth.add(reservasi.getTanggal().getMonthValue());
                mappingBulanTahun.put(Integer.toString(reservasi.getTanggal().getYear()), listMonth);
            }
        }

        Map<String, List<Integer>> finalMap = new HashMap<>();
        for (Map.Entry<String,List<Integer>> map : mappingBulanTahun.entrySet()){
            List<Integer> jumlahReservasiPerBulanDalamTahun = new ArrayList<Integer>(12);
            for (int i = 0; i < 12; i++) {
                jumlahReservasiPerBulanDalamTahun.add(0);
            }
            for (Integer bulan : map.getValue()){
                jumlahReservasiPerBulanDalamTahun.set(bulan-1, jumlahReservasiPerBulanDalamTahun.get(bulan-1) + 1);
            }
            finalMap.put(map.getKey(),jumlahReservasiPerBulanDalamTahun);
        }

        model.addAttribute("totalReservasi", listReservasi.size());
        model.addAttribute("totalReservasiPaid", listCompletedReservasi.size());
        model.addAttribute("totalReservasiSoftbook", listSoftbookReservasi.size());
        model.addAttribute("totalReservasiDP1", listDP1Reservasi.size());
        model.addAttribute("totalReservasiDP2", listDP2Reservasi.size());
        model.addAttribute("years", finalMap.keySet());
        model.addAttribute("data", finalMap);

        return "grafik/chart";

    }

}
