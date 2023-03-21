package com.madityafr.room.roomservice.dto;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaginateDTO<T> implements Serializable {
    private T data;
    private Long totalOfItems;
    private Integer currentPage;
    private Integer totalOfPages;

}
