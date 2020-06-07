package com.vijay.sfcp.obrs.error;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 30 May 2020
*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ErrorResponse {
    private Map<String, String> parameters = new HashMap<>();
    private List<String> additionalMessages = new ArrayList<>();

    public ErrorResponse() {
    }

    public ErrorResponse(Map<String, String> parameters){
        this.parameters = parameters;
    }

    public ErrorResponse(String errorMessage){
        this.additionalMessages.add(errorMessage);
    }

    public ErrorResponse(Map<String, String> parameters, List<String> additionalMessages) {
        this.parameters = parameters;
        this.additionalMessages = additionalMessages;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public List<String> getAdditionalMessages() {
        return additionalMessages;
    }

    public void setAdditionalMessages(List<String> additionalMessages) {
        this.additionalMessages = additionalMessages;
    }
}
