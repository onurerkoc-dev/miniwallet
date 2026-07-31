package com.onurerkoc.miniwallet.exception;

// Verilen ID değerine sahip kullanıcı veritabanında
// bulunamadığında fırlatacağımız özel hata sınıfıdır.
public class UserNotFoundException extends RuntimeException {

    // Hata mesajını RuntimeException sınıfına gönderiyoruz.
    public UserNotFoundException(String message) {
        super(message);
    }
}