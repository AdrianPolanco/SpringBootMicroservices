package com.adriandev.inventory.model.dtos;

public record BaseResponse(String[] errorMessages) {
    public boolean HasErrors(){
        return errorMessages != null && errorMessages.length > 0;
    }
}
