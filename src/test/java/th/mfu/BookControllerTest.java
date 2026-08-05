package th.mfu;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

// This test is already complete — read it together, then make it pass.
// MockMvc sends fake HTTP requests through the real Spring MVC machinery:
// no server, no browser. The lab's ConcertControllerTest grades you this way.
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void resetStore() {
        BookController.books.clear();
        BookController.nextId = 1;
    }

    @Test
    public void testAddAndListBooks() throws Exception {
        mockMvc.perform(post("/books")
                .param("title", "Clean Code")
                .param("author", "Robert C. Martin"))
                .andExpect(redirectedUrl("/books"));

        mockMvc.perform(post("/books")
                .param("title", "The Pragmatic Programmer")
                .param("author", "Hunt and Thomas"))
                .andExpect(redirectedUrl("/books"));

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(view().name("list-book"))
                .andExpect(model().attribute("books", hasSize(2)));
    }

    @Test
    public void testDeleteBook() throws Exception {
        mockMvc.perform(post("/books")
                .param("title", "Clean Code")
                .param("author", "Robert C. Martin"));

        mockMvc.perform(get("/delete-book/1"))
                .andExpect(view().name("redirect:/books"));

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("books", hasSize(0)));
    }
}
