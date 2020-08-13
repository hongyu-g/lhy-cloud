package com.hong.bookservice.service;

import com.hong.bookservice.bean.EsEntity;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.List;

public interface EsCurdService {

    /**
     * 创建索引
     *
     * @param indexName
     * @return
     */
    boolean createIndex(String indexName);

    /**
     * 删除索引
     *
     * @param indexName
     */
    boolean deleteIndex(String indexName);

    /**
     * 判断索引是否存在
     *
     * @param indexName
     * @return
     */
    boolean existIndex(String indexName);

    /**
     * 插入数据
     *
     * @param indexName
     * @param esEntity
     * @return
     */
    RestStatus insert(String indexName, EsEntity esEntity);

    /**
     * 批量插入数据
     * @param index
     * @param list
     */
    void insertBatch(String index, List<EsEntity> list);

    /**
     * 查找数据
     *
     * @param indexName
     * @return
     */
    <T> List<T> search(String indexName, SearchSourceBuilder builder, Class<T> c);

    /**
     * 更新数据
     *
     * @param indexName
     * @param esEntity
     * @return
     */
    RestStatus update(String indexName, EsEntity esEntity);

    /**
     * 删除id
     *
     * @param id
     * @return
     */
    RestStatus delete(String indexName, String id);
}
