package th.mfu;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BookController {

    // the "database" for today: key = book id, value = the book
    public static HashMap<Integer, Book> books = new HashMap<Integer, Book>();
    public static int nextId = 1;

    @GetMapping("/books")
    public String listBooks(Model model) {
        model.addAttribute("books", books.values());
        return "list-book";
    }

    @GetMapping("/add-book")
    public String addABookForm(Model model) {
        model.addAttribute("book", new Book());
        return "add-book-form";
    }

    @PostMapping("/books")
    public String saveBook(@ModelAttribute Book book) {
        book.setId(nextId);
        books.put(nextId, book);
        nextId++;
        return "redirect:/books";
    }

    @GetMapping("/delete-book/{id}")
    public String deleteBook(@PathVariable int id) {
        books.remove(id);
        return "redirect:/books";
    }

    @GetMapping("/delete-book")
    public String removeAllBooks() {
        books.clear();
        nextId = 1;
        return "redirect:/books";
    }

}
