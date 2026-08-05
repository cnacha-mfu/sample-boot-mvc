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
        // TODO: add books.values() to the model as attribute "books"
        // TODO: return the "list-book" template
        return "";
    }

    @GetMapping("/add-book")
    public String addABookForm(Model model) {
        // TODO: add a blank new Book() to the model as attribute "book"
        // TODO: return the "add-book-form" template
        return "";
    }

    @PostMapping("/books")
    public String saveBook(@ModelAttribute Book book) {
        // TODO: set the book's id to nextId, put it in the map, increment nextId
        // TODO: redirect to the book list  (return "redirect:/books")
        return "";
    }

    @GetMapping("/delete-book/{id}")
    public String deleteBook(@PathVariable int id) {
        // TODO: remove the book with this id from the map
        // TODO: redirect to the book list
        return "";
    }

    @GetMapping("/delete-book")
    public String removeAllBooks() {
        // TODO: clear the map and reset nextId to 1
        // TODO: redirect to the book list
        return "";
    }

}
