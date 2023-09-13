package th.mfu;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import th.mfu.domain.Employee;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAddAndListEmployees() throws Exception {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("John", "Doe"));
        employees.add(new Employee("Jane", "Smith"));

        for (Employee employee : employees) {
            mockMvc.perform(post("/employees")
                    .param("firstName", employee.getFirstName())
                    .param("lastName", employee.getLastName()))
                    .andExpect(redirectedUrl("/employees"));
        }

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(view().name("list-employee"))
                .andExpect(model().attribute("employees", hasSize(2)));
    }

    @AfterEach
    public void resetDb() throws Exception{
        mockMvc.perform(get("/delete-employee"));
    }

    @Test
    public void testAddAndDeleteEmployees() throws Exception {

        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Martin", "Doe"));
        employees.add(new Employee("Sarah", "Smith"));

        for (Employee employee : employees) {
            mockMvc.perform(post("/employees")
                    .param("firstName", employee.getFirstName())
                    .param("lastName", employee.getLastName()))
                    .andExpect(redirectedUrl("/employees"));
        }

        mockMvc.perform(get("/delete-employee/1"))
                .andExpect(view().name("redirect:/employees"));

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(view().name("list-employee"))
                .andExpect(model().attribute("employees", hasSize(1)));

    }

}
