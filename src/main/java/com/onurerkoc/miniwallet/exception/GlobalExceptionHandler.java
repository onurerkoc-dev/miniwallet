package com.onurerkoc.miniwallet.exception;

import com.onurerkoc.miniwallet.dto.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Uygulamadaki Controller sınıflarında oluşan hataları
// merkezi bir noktadan yakalamamızı sağlar.
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Bu metot yalnızca EmailAlreadyExistsException
    // türündeki hata oluştuğunda çalışır.
    @ExceptionHandler(EmailAlreadyExistsException.class)

    // Aynı e-posta mevcut kullanıcıyla çakıştığı için
    // HTTP 409 Conflict durum kodunu döndürüyoruz.
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrorResponse handleEmailAlreadyExists(
            EmailAlreadyExistsException exception
    ) {

        // Exception içerisindeki mesajı JSON response
        // nesnesine dönüştürüyoruz.
        return new ApiErrorResponse(exception.getMessage());
    }

    // Bu metot yalnızca UserNotFoundException
    // türündeki hata oluştuğunda çalışır.
    @ExceptionHandler(UserNotFoundException.class)

    // Aranan kullanıcı bulunamadığı için
    // HTTP 404 Not Found durum kodunu döndürüyoruz.
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleUserNotFound(
            UserNotFoundException exception
    ) {

        // Exception içerisindeki mesajı düzenli bir
        // JSON hata cevabına dönüştürüyoruz.
        return new ApiErrorResponse(exception.getMessage());
    }
}