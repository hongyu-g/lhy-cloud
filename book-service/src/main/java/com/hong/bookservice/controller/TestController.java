package com.hong.bookservice.controller;

import com.hong.bookservice.bean.Book;
import com.hong.bookservice.bean.EsEntity;
import com.hong.bookservice.service.EsCurdService;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/es")
public class TestController {

    @Autowired
    private EsCurdService esCurdService;

    @PostMapping("/createIndex")
    public boolean createIndex(@RequestParam String indexName) {
        return esCurdService.createIndex(indexName);
    }

    @PostMapping("/deleteIndex")
    public boolean deleteIndex(@RequestParam String indexName) {
        return esCurdService.deleteIndex(indexName);
    }

    @PostMapping("/existIndex")
    public boolean existIndex(@RequestParam String indexName) {
        return esCurdService.existIndex(indexName);
    }


    @PostMapping("/insert")
    public RestStatus insert(@RequestParam String indexName, @RequestBody EsEntity esEntity) {
        return esCurdService.insert(indexName, esEntity);
    }

    @PostMapping("/insertBatch")
    public void insertBatch(@RequestParam String indexName, @RequestBody List<EsEntity> list) {
        esCurdService.insertBatch(indexName, list);
    }


    @PostMapping("/update")
    public RestStatus update(@RequestParam String indexName, @RequestBody EsEntity esEntity) {
        return esCurdService.update(indexName, esEntity);
    }

    @PostMapping("/delete")
    public RestStatus delete(@RequestParam String indexName, @RequestParam String id) {
        return esCurdService.delete(indexName, id);
    }

    @PostMapping("/search")
    public List<Book> search(@RequestParam String indexName) {
        SearchSourceBuilder builder = new SearchSourceBuilder();
        return esCurdService.search(indexName, builder, Book.class);
    }

}
