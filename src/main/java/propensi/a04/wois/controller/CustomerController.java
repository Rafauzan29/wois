package propensi.a04.wois.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import propensi.a04.wois.model.CustomerModel;
import propensi.a04.wois.repository.CustomerDb;
import propensi.a04.wois.service.CustomerService;
import propensi.a04.wois.service.UserService;

@Controller
public class CustomerController {
    @Qualifier("customerServiceImpl")
    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerDb customerDb;

    @GetMapping("/signup")
    public String addCustomerFormPage(Model model) {
        var customer = new CustomerModel();
        String role = "Customer";
        model.addAttribute("customer", customer);
        model.addAttribute("role", role);
        return "user/form-add-customer";
    }

    @PostMapping(value="/signup", params = {"save"})
    public String addCustomerSubmit(@RequestParam("confirmedPass") String confirmedPass, @ModelAttribute CustomerModel user, Model model) {
        boolean confPass = customerService.checkConfirmedPassword(user.getPassword(), confirmedPass);
        if (confPass == true){
            userService.addUser(user);
            return "/login";
        } else {
            return "dang";
        }
    }
}
