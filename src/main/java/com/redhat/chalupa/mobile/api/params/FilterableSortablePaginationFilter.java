package com.redhat.chalupa.mobile.api.params;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.Pattern;
import javax.ws.rs.QueryParam;

/**
 * {@link SortablePaginationFilter} with support for filtering.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FilterableSortablePaginationFilter extends SortablePaginationFilter {

    /**
     * Ordering of returned result set.
     */
    @Pattern(regexp = "[^,]+,.+")
    @QueryParam("filter")
    private String filter;
}
