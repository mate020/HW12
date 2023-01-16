package de.uni.koeln.se.bookstore.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.uni.koeln.se.bookstore.datamodel.Book;
import de.uni.koeln.se.bookstore.service.BookService;

@RequestMapping("/bookStore")
@RestController
public class BookController {

	@Autowired
	BookService bookSer;

	@GetMapping
	public ResponseEntity<List<Book>> getAllbooks() {
		List<Book> books = new ArrayList<Book>();
		books = bookSer.findBooks();
		return new ResponseEntity<>(books, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable int id) {
		return new ResponseEntity<>(bookSer.fetchBook(id).get(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Book> addBookt(@RequestBody Book book) {
		bookSer.addBook(book);
		return new ResponseEntity<>(book, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Book> removeBookById(@PathVariable int id) {

		Book book = bookSer.fetchBook(id).get();

		if (bookSer.deleteBook(id)) {
			return new ResponseEntity<>(book, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(book, HttpStatus.BAD_REQUEST);
		}
	}


	@GetMapping("/old")
	public ResponseEntity<Book> oldestBook() {
		List<Book> books = new ArrayList<Book>();
		books= bookSer.findBooks();
		int index = 0;
		int old = Integer.MAX_VALUE;
		for (Book b : books) {
			if (b.getPublishYear() < old) {
				old = b.getPublishYear();
				index = b.getId();
			}

		}
		return new ResponseEntity<>(bookSer.fetchBook(index).get(), HttpStatus.OK);

	}

	@GetMapping("/latest")
	public ResponseEntity<Book> latestBook() {
		List<Book> books = new ArrayList<Book>();
		books= bookSer.findBooks();
		int min = 0;
		int minIndex = 0;
		for (Book b : books) {
			if (b.getPublishYear() > min) {
				min = b.getPublishYear();
				minIndex = b.getId();
			}

		}

		return new ResponseEntity<>(bookSer.fetchBook(minIndex).get(), HttpStatus.OK);
	}
}