package com.onurerkoc.miniwallet.exception;


// İstenen cüzdan veritabanında bulunamadığında
// fırlatacağımız uygulamaya özel hata sınıfıdır.
public class WalletNotFoundException extends RuntimeException {
    public WalletNotFoundException(String message) {
        super(message);
    }
}
