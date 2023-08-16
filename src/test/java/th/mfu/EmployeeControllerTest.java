package th.mfu;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

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

}
