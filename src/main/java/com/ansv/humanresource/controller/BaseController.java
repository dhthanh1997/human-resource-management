package com.ansv.humanresource.controller;

import com.ansv.humanresource.util.DataUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.ansv.humanresource.util.DataUtils.camelToSnake;

public class BaseController {

    public PageRequest pageRequest(List<String> sort, Integer page, Integer size) {
        if(CollectionUtils.isEmpty(sort)) {
            return PageRequest.of(page, size);
        }
        return PageRequest.of(page, size, sort(sort));
    }

    @SuppressWarnings("java:S3776")
    public Sort sort(List<String> sort) {
        if (CollectionUtils.isEmpty(sort)) {
            return null;
        }

        List<Sort.Order> orderList = new ArrayList<>();
        if (sort.get(0).contains("_")) {
            String[] strArray;
            for (String str : sort) {
                strArray = str.split("_");
                if (strArray.length > 1) {
                    if ("asc".equalsIgnoreCase(strArray[1])) {
                        orderList.add(Sort.Order.asc(camelToSnake(strArray[0])));
                    } else {
                        orderList.add(Sort.Order.desc(camelToSnake(strArray[0])));
                    }
                } else {
                    orderList.add(Sort.Order.asc(camelToSnake(strArray[0])));
                }
            }
        } else {
            for(String s: sort) {
                orderList.add(Sort.Order.asc(camelToSnake(s)));
            }
        }
        return Sort.by(orderList);
    }

    public List<String> getSortParam(String sort) {
        if (DataUtils.isNullOrEmpty(sort)) {
            return new ArrayList<>();
        }
        return Arrays.asList(sort.split(";"));
    }
}
