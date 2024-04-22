package com.adriandev.orders.model.dtos;

public record BaseResponse(String[] errorMessages) {
    public boolean HasErrors(){
        return errorMessages != null && errorMessages.length > 0;
    }
}
