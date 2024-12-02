package org.t2t.prd.repository;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface OrderMapper {
    void purchase(Map<String, Object> map);

}
