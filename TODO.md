# In-Class Demonstration Flow — Spring MVC with Thymeleaf

This repository has these branches:

- **`main`** — the in-class version. Some code is blanked out with `TODO` comments. We fill these in together during the demonstration.
- **[`complete`](https://github.com/cnacha-mfu/sample-boot-mvc/tree/complete)** — the finished code for every step. Use it to catch up or check your work.

**Start the application first** and restart it after each change:

```
mvn spring-boot:run
```

Follow the steps in order; each one adds a single new idea.

---

## Step 1: A page, not JSON — @Controller and a template

**Files:** `src/main/java/th/mfu/HelloController.java`, `src/main/resources/templates/greet.html` (both already complete — read together)

**Idea:** last time we returned JSON with `@RestController`. Today's `@Controller` returns a **view name** — Spring finds `templates/greet.html` and renders it. Data travels to the page through the `Model` (`model.addAttribute("message", ...)`), and Thymeleaf prints it with `th:text="${message}"`.

**Try it:** open <http://localhost:8080/hello>. View the page source — the `th:text` is gone; the server rendered real HTML.

---

## Step 2: Show a list — th:each

**Files:** `src/main/java/th/mfu/BookController.java`, `templates/list-book.html` (template already complete — read together)

**Idea:** put a collection in the model and the template repeats a table row per item with `th:each="book : ${books}"`. A static `HashMap` is today's stand-in for a database.

**Fill in together:** in `listBooks` — add `books.values()` to the model as `"books"`, return `"list-book"`.

**Try it:** open <http://localhost:8080/books> — an empty table (nothing registered yet), but the page renders.

**Solution:** [BookController](https://github.com/cnacha-mfu/sample-boot-mvc/blob/complete/src/main/java/th/mfu/BookController.java)

---

## Step 3: A form bound to an object — th:object / th:field

**Files:** `BookController.java`, `templates/add-book-form.html` (template already complete — read together)

**Idea:** the form binds to an object in the model: `th:object="${book}"` names it, each `th:field="*{title}"` fills one property. The controller just has to put a **blank** `Book` in the model for the form to bind to.

**Fill in together:** in `addABookForm` — add `new Book()` to the model as `"book"`, return `"add-book-form"`.

**Try it:** open <http://localhost:8080/add-book>. The form shows — but Save still does nothing useful. That's the next step.

**Solution:** [BookController](https://github.com/cnacha-mfu/sample-boot-mvc/blob/complete/src/main/java/th/mfu/BookController.java)

---

## Step 4: Save — @ModelAttribute and redirect

**File:** `BookController.java`

**Idea:** the form POSTs to `/books`; `@ModelAttribute Book book` rebuilds the object from the form fields. After saving we **redirect** (`return "redirect:/books"`) instead of rendering — so refreshing the list page never re-submits the form (the POST-redirect-GET pattern).

**Fill in together:** in `saveBook` — set `book.setId(nextId)`, put it in the map, `nextId++`, redirect to the list.

**Try it:** add two books through the form; they appear in the list with ids 1 and 2.

**Solution:** [BookController](https://github.com/cnacha-mfu/sample-boot-mvc/blob/complete/src/main/java/th/mfu/BookController.java)

---

## Step 5: Delete — @PathVariable, and delete all

**File:** `BookController.java`

**Idea:** the id travels in the URL (`/delete-book/2`) and `@PathVariable int id` catches it — same idea as in the REST demo, now driving page navigation.

**Fill in together:**
1. `deleteBook` — remove the id from the map, redirect to the list.
2. `removeAllBooks` — clear the map, reset `nextId` to 1, redirect to the list.

**Try it:** click *delete* on one row, then *Delete all*.

**Solution:** [BookController](https://github.com/cnacha-mfu/sample-boot-mvc/blob/complete/src/main/java/th/mfu/BookController.java)

---

## Step 6: MockMvc test — how the lab grades you

**File:** `src/test/java/th/mfu/BookControllerTest.java` (already complete — read it together)

**Idea:** `MockMvc` sends fake HTTP requests through the real Spring MVC machinery — no server, no browser — then asserts on the status, the **view name**, the **redirect URL**, and the **model contents**. This is exactly how the lab's `ConcertControllerTest` grades your work, on every push, via GitHub Actions.

**Try it:**

```
mvn test -Dtest=BookControllerTest
```

Run it **before** Step 2 (red — the TODOs return `""`) and **after** Step 5 (green). Red → implement → green is the rhythm of the lab.

**Solution:** [BookControllerTest](https://github.com/cnacha-mfu/sample-boot-mvc/blob/complete/src/test/java/th/mfu/BookControllerTest.java)

---

## After class

You are now ready for the graded lab: [lab-web-mvc](https://github.com/maefahluang-uni/lab-web-mvc) — a **concert management app** built with exactly these pieces:

| Lab requirement | Demo step |
|---|---|
| `Concert` model with attributes + getters/setters | Step 2 (`Book`) |
| `listConcerts` — model attribute + `list-concert` view | Step 2 |
| `addAConcertForm` — blank object for the form | Step 3 |
| `saveConcert` — `@ModelAttribute` + redirect | Step 4 |
| `deleteConcert` / `removeAllConcerts` — `@PathVariable`, clear | Step 5 |
| Graded by `ConcertControllerTest` (`mvn verify`, MockMvc) | Step 6 |

> **Tip:** only one program can listen on port 8080 at a time. If `mvn spring-boot:run` fails with `Port 8080 was already in use`, stop the other server first.
