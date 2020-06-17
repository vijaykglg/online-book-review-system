package com.vijay.sfcp.obrs.book.specification;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 15 June 2020
*/
/*
*
* key — Represents the entity field name, i.e. title, genre, etc.
* value — Represents the parameter value, i.e. Troy, Action, etc.
* operation — Indicates the search operation, i.e. equality, match, comparison, etc.
*
* */
public class SearchCriteria {
    private String key;
    private Object value;
    private SearchOperation operation;

    public SearchCriteria() {
    }

    public SearchCriteria(String key, Object value, SearchOperation operation) {
        this.key = key;
        this.value = value;
        this.operation = operation;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public SearchOperation getOperation() {
        return operation;
    }

    public void setOperation(SearchOperation operation) {
        this.operation = operation;
    }
}
