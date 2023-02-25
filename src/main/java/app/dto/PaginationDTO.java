package app.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * DTO that contains pagination information such as number of
 * current, previous, next, first, last page, size of page,
 * count of pages and count of records per one page. Used in
 * jsp tag to build pagination widget on html page
 **/
@Getter
@Setter
public class PaginationDTO {
    private Integer firstPage;
    private List<Integer> previousPages;
    private Integer previousPage;
    private Integer nextPage;
    private List<Integer> nextPages;
    private Integer lastPage;

    private Integer recordsCount;
    private Integer pagesCount;
    private Integer pageNumber;
    private Integer pageSize;
}
