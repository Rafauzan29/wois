package propensi.a04.wois.controller;

// import apap.tutorial.belajarbelajar.security.xml.Attributes;
// import apap.tutorial.belajarbelajar.security.xml.ServiceResponse;
// import apap.tutorial.belajarbelajar.service.PenyelenggaraService;
// import apap.tutorial.belajarbelajar.service.RoleService;
// import apap.tutorial.belajarbelajar.Setting.Setting;
// import apap.tutorial.belajarbelajar.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import propensi.a04.wois.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;
import propensi.a04.wois.repository.UserDb;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class PageController {


    @Autowired
    ServerProperties serverProperties;

    @RequestMapping("/")
    public String home(){
        return "home";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

//    public String loginSuccessful(Principal principal){
////        String nama = userDb.findByEmail(principal.getName()).getNama();
////        System.out.println("halalala" + nama);
////        model.addAttribute("nama", nama);
//        try {
//            if (principal.getName() != null) {
//                return "home-after-login";
//            }
//            else {
//                return "";
//            }
//        }
//        catch (NullPointerException e) {
//            throw new ResponseStatusException(
//                    HttpStatus.BAD_REQUEST
//            );
//        }
//>>>>>>> b403e0ec2e5cc374de63126d1d55639250da6b35
    }

