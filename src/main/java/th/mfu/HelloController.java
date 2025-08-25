package th.mfu;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HelloController {

    @GetMapping("/hello")
    public String hello(Model model) {
        // Add data to the model
        model.addAttribute("msg", "Welcome to Spring MVC!");

        // Return the view name (located in the "src/main/resources/templates" directory)
        return "greet";
    }

    @GetMapping("/login")
    public String loginForm(Model model){
        Person newperson = new Person();
        model.addAttribute("person",newperson);

        return "login";
    }

    @PostMapping("/login")
    public String loginAction(@ModelAttribute("person") Person person, Model model){
        if ("1234".equals(person.getPassword())){
            model.addAttribute("whois", person.getName());
            return "home";
        } else{
            model.addAttribute("message", "You have entered wrong password");
            return "login";
        }
        
    }
   
}
