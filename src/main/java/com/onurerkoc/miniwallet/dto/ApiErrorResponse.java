package com.onurerkoc.miniwallet.dto;

// API içerisinde oluşan hataları istemciye düzenli bir
// JSON cevabı olarak göndermek için kullandığımız DTO'dur.
public class ApiErrorResponse {

    // Kullanıcıya hatanın nedenini açıklayan mesajdır.
    private String message;

    // Hata yakalandığında response nesnesini verilen
    // hata mesajıyla oluşturuyoruz.
    public ApiErrorResponse(String message) {
        this.message = message;
    }

    // Jackson bu getter metodunu kullanarak message alanını
    // JSON cevabına dönüştürür.
    public String getMessage() {
        return message;
    }
}