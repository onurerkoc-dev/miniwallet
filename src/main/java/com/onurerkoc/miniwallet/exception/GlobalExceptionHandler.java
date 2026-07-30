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
    // türündeki hatalar oluştuğunda çalışır.
    @ExceptionHandler(EmailAlreadyExistsException.class)

    // Aynı e-posta mevcut kullanıcıyla çakıştığı için
    // HTTP 409 Conflict durum kodunu döndürüyoruz.
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrorResponse handleEmailAlreadyExists(
            EmailAlreadyExistsException exception
    ) {

        // Exception oluşturulurken verdiğimiz hata mesajını alıp
        // düzenli bir JSON response nesnesine dönüştürüyoruz.
        return new ApiErrorResponse(exception.getMessage());
    }
}