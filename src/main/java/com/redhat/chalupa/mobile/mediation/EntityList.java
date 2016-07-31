package com.redhat.chalupa.mobile.mediation;

import lombok.Value;

import java.util.List;

/**
 * Holder for list responses. Contains the resulting list of entities and information of total number of record which
 * enables pagination for clients.
 */
@Value
public class EntityList<E> {

    /**
     * Entities to be returned to user.
     */
    List<E> entities;

    /**
     * Number used to enable pagination for clients.
     */
    long totalCount;
}
