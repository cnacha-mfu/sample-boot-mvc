package th.mfu;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EmployeeController {
      private List<Employee> employees = new ArrayList<>(); // In-memory storage for simplicity

    @GetMapping("/employees")
    public String listEmployees(Model model) {
        model.addAttribute("employees", employees);
        return "list-employee";
    }

    @GetMapping("/add-employee")
    public String showAddEmployeeForm(Model model) {
        // pass blank employee to a form
        model.addAttribute("employee", new Employee());
        return "add-employee-form";
    }

    @PostMapping("/employees")
    public String saveEmployee(@ModelAttribute Employee employee) {
        // In a real application, you would save the employee to a database or other storage
        employees.add(employee);
        return "redirect:/employees";
    }
}
