package microservices.example.quoteservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = {"https://red-grass-0510a3e03.1.azurestaticapps.net", "http://localhost:3000"})
public class QuoteController {
    private List<Quote> quoteList = new ArrayList<> (Arrays.asList(new Quote(1, "John", "my first quote"),
            new Quote(2, "Davitt" ,"Azure is easy!"),
            new Quote(3, "Charlie" ,"Microservices are cool!")));

    //@HystrixCommand(fallbackMethod = "quotesFallback")
    @GetMapping("/quotes")
    public ResponseEntity<List<Quote>> getQuoteList() {
        if (!quoteList.isEmpty()) {
            return new ResponseEntity(quoteList, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    // fall back method
    public ResponseEntity<List<Quote>> quotesFallback() {
        // fault tolerance
        System.out.println("Something went wrong with quote service");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @GetMapping("/quote")
    public ResponseEntity<List<Quote>> getQuote(@RequestParam(required = true, defaultValue = "") int quoteId) {
        Optional<Quote> quote = quoteList.stream().filter(q -> q.getId() == quoteId).findFirst();

        if (quote.isPresent()) {
            return new ResponseEntity(quote.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addQuote")
    public ResponseEntity addQuote(@RequestBody Quote quote) {
        Optional<Quote> latestQuote = quoteList.stream().max(Comparator.comparing(Quote::getId));

        if (latestQuote.isPresent()) {
            quote.setId(latestQuote.get().getId() + 1);
        } else {
            quote.setId(1);
        }

        quoteList.add(quote);

        return new ResponseEntity(quote, HttpStatus.OK);
    }
}