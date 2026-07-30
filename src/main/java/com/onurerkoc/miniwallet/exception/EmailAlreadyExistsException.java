package com.onurerkoc.miniwallet.exception;

// Aynı e-posta adresiyle ikinci bir kullanıcı oluşturulmaya
// çalışıldığını belirtmek için kullandığımız özel hata sınıfıdır.
public class EmailAlreadyExistsException extends RuntimeException {

    // Hata oluşturulurken verilen mesajı RuntimeException
    // sınıfına gönderiyoruz.
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}