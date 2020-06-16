package com.aliaksandr.rahavoi.university;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Данный класс является стартовой точкой приложения.
 * Внутри метода {@link SpringApplication#run(java.lang.Class, java.lang.String...)}
 * происходит инциализация приложения, включая инициализацию Elasticsearch.
 */
@SpringBootApplication
public class UniversityApplication {

    public static void main(String[] args) {
        SpringApplication.run(UniversityApplication.class, args);
    }
}
