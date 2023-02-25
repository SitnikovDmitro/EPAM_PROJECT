package app.service.utils;

import app.dto.PaginationDTO;

import java.util.List;

/**
 * Provides pagination utilities
 **/
public interface PaginationUtil {
    /**
     * Provides pagination
     * @param page current page number
     * @param recordsCount total number of records
     * @param pageSize number of records per one page
     * @return pagination object containing information about pages count,
     * current page and available pages from current page
     **/
    PaginationDTO paginate(int page, int recordsCount, int pageSize);
}
