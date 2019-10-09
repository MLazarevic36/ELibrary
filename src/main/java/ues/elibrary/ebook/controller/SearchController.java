package ues.elibrary.ebook.controller;


import org.apache.lucene.search.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ues.elibrary.ebook.dto.AdvancedQueryDTO;
import ues.elibrary.ebook.dto.BookDTO;
import ues.elibrary.ebook.dto.SimpleQueryDTO;
import ues.elibrary.ebook.lucene.search.QueryBuilder;
import ues.elibrary.ebook.lucene.search.ResultRetriever;

import java.util.List;

@RestController
@RequestMapping(value = "api/search")
public class SearchController {

    @PostMapping(value = "/simple")
    public ResponseEntity<List<BookDTO>> simpleSearch(@RequestBody SimpleQueryDTO simpleQueryDTO) {
        Query query = QueryBuilder.buildSimpleQuery(simpleQueryDTO);
        ResultRetriever rr = new ResultRetriever();
        List<BookDTO> bookDTOS = rr.returnResults(query);

        return new ResponseEntity<List<BookDTO>>(bookDTOS, HttpStatus.OK);
    }

    @PostMapping(value = "/advanced")
    public ResponseEntity<List<BookDTO>> advancedSearch(@RequestBody AdvancedQueryDTO advancedQueryDTO) {
        Query query = QueryBuilder.buildAdvancedQuery(advancedQueryDTO);
        ResultRetriever rr = new ResultRetriever();
        List<BookDTO> bookDTOS = rr.returnResults(query);

        return new ResponseEntity<List<BookDTO>>(bookDTOS, HttpStatus.OK);
    }


}
