package com.aliaksandr.rahavoi.university.shared.exception.rating;

public class RatingAttributesIsNullException extends RuntimeException {
    public RatingAttributesIsNullException(String parameters) {
        super(parameters + "Some of provided parameters are null, but expected that all parameters are non-null");
    }
}
