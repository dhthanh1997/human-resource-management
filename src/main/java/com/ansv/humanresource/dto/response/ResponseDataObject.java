package com.ansv.humanresource.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ResponseDataObject<T> {
    public String error;
    public String message;
    public Integer status;
    public LocalDateTime timestamp;
    public List<T> listData;
    public T data;
    public Page<T> pagingData;

    public ResponseDataObject() {
        this.timestamp = LocalDateTime.now();
    }

    public void success() {
        this.message = "Successful";
        this.status = HttpStatus.OK.value();
    }

    public void initData(T data) {
        this.data = data;
        this.success();
    }

    public void initListData(List<T> listData) {
        this.listData = listData;
        this.success();
    }
}
