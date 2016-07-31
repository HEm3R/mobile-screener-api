package com.redhat.chalupa.mobile.api.params;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.Pattern;
import javax.ws.rs.QueryParam;

/**
 * {@link PaginationFilter} with support for sorting..
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SortablePaginationFilter extends PaginationFilter {

    /**
     * Ordering of returned result set.
     */
    @Pattern(regexp = "[a-zA-Z0-9-_.,]*")
    @QueryParam("orderBy")
    private String orderBy;
}
