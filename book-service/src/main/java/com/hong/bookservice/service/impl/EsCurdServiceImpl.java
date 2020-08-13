package com.hong.bookservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hong.bookservice.bean.EsEntity;
import com.hong.bookservice.service.EsCurdService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EsCurdServiceImpl implements EsCurdService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public boolean createIndex(String indexName) {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
        try {
            AcknowledgedResponse acknowledgedResponse = restHighLevelClient.indices()
                    .create(createIndexRequest, RequestOptions.DEFAULT);
            return acknowledgedResponse.isAcknowledged();
        } catch (Exception e) {
            log.error(e.toString(), e);
        }
        return false;
    }

    @Override
    public boolean deleteIndex(String indexName) {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(indexName);
        try {
            AcknowledgedResponse acknowledgedResponse = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
            return acknowledgedResponse.isAcknowledged();
        } catch (Exception e) {
            log.error(e.toString(), e);
        }
        return false;
    }

    @Override
    public boolean existIndex(String indexName) {
        GetIndexRequest getIndexRequest = new GetIndexRequest(indexName);
        try {
            return restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error(e.toString(), e);
        }
        return false;
    }

    @Override
    public RestStatus insert(String indexName, EsEntity esEntity) {
        IndexRequest indexRequest = new IndexRequest(indexName);
        indexRequest.id(esEntity.getId()).source(JSONObject.toJSONString(esEntity.getData()), XContentType.JSON);
        try {
            IndexResponse response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            return response.status();
        } catch (Exception e) {
            log.error(e.toString(), e);
        }
        return null;
    }

    @Override
    public void insertBatch(String index, List<EsEntity> list) {
        BulkRequest request = new BulkRequest();
        list.forEach(item -> request.add(new IndexRequest(index).id(item.getId())
                .source(JSON.toJSONString(item.getData()), XContentType.JSON)));
        try {
            restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> List<T> search(String indexName, SearchSourceBuilder builder, Class<T> c) {
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.source(builder);
        try {
            SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHit[] searchHits = response.getHits().getHits();
            List<T> res = new ArrayList<>(searchHits.length);
            for (SearchHit hit : searchHits) {
                res.add(JSONObject.parseObject(hit.getSourceAsString(), c));
            }
            return res;
        } catch (Exception e) {
            log.error(e.toString(), e);
        }
        return null;
    }

    @Override
    public RestStatus update(String indexName, EsEntity esEntity) {
        UpdateRequest updateRequest = new UpdateRequest(indexName, esEntity.getId());
        updateRequest.doc(JSONObject.toJSONString(esEntity.getData()), XContentType.JSON);
        try {
            UpdateResponse response = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
            return response.status();
        } catch (Exception e) {
            log.error(e.toString(), e);
        }
        return null;
    }

    @Override
    public RestStatus delete(String indexName, String id) {
        DeleteRequest deleteRequest = new DeleteRequest(indexName, id);
        try {
            DeleteResponse response = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
            return response.status();
        } catch (Exception e) {
            log.error(e.toString(), e);
        }
        return null;
    }
}
