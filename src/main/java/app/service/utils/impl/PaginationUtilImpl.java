package app.service.utils.impl;


import app.dto.PaginationDTO;
import app.service.utils.PaginationUtil;

import java.util.ArrayList;
import java.util.List;


public class PaginationUtilImpl implements PaginationUtil {
    @Override
    public PaginationDTO paginate(int page, int recordsCount, int pageSize) {
        int pagesCount = recordsCount/pageSize;

        if (recordsCount%pageSize != 0) {
            pagesCount++;
        }

        if (page < 1) {
            page = 1;
        } else if (page > pagesCount) {
            page = pagesCount;
        }

        PaginationDTO pagination = new PaginationDTO();

        pagination.setRecordsCount(recordsCount);
        pagination.setPageNumber(page);
        pagination.setPagesCount(pagesCount);
        pagination.setPageSize(pageSize);

        if (page > 1) pagination.setPreviousPage(page-1);
        if (page < pagesCount) pagination.setNextPage(page+1);

        if (page > 2) pagination.setFirstPage(1);
        if (page < pagesCount-1) pagination.setLastPage(pagesCount);

        List<Integer> previousPages = new ArrayList<>();
        if (page > 4) previousPages.add(page-3);
        if (page > 3) previousPages.add(page-2);
        pagination.setPreviousPages(previousPages);

        List<Integer> nextPages = new ArrayList<>();
        if (page < pagesCount-2) nextPages.add(page+2);
        if (page < pagesCount-3) nextPages.add(page+3);
        pagination.setNextPages(nextPages);

        return pagination;
    }
}


