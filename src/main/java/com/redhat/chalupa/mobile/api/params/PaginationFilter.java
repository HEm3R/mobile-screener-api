package com.redhat.chalupa.mobile.api.params;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.QueryParam;

/**
 * Pagination (limit, offset) filter wrapper class.
 */
@Data
public class PaginationFilter {

    private static final int MAX_LIMIT = 10;
    private static final int DEFAULT_LIMIT = 5;
    private static final int DEFAULT_OFFSET = 0;

    @Min(1)
    @Max(MAX_LIMIT)
    @QueryParam("limit")
    private Integer limit;

    /**
     * Offset of records returned in results set.
     */
    @Min(0)
    @QueryParam("offset")
    private Integer offset;

    /**
     * @return limit set by query param or {@link #DEFAULT_LIMIT} if limit is null
     */
    public int getLimit() {
        return limit != null ? limit : DEFAULT_LIMIT;
    }

    /**
     * @return offset set by query param or default offset
     */
    public int getOffset() {
        return offset != null ? offset : DEFAULT_OFFSET;
    }
}
