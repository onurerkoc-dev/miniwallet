package com.onurerkoc.miniwallet.dto;
import java.math.BigDecimal;



public class WalletResponse {

    // API cevabında cüzdanın kimliğini göstereceğiz.
    private Long id;

    // Para değerlerinde hassasiyet kaybı yaşamamak için
// bakiye bilgisini BigDecimal olarak tutuyoruz.
    private BigDecimal balance;

    // Cüzdanın hangi kullanıcıya ait olduğunu göstereceğiz.
    private Long userId;

    // Service katmanındaki cüzdan bilgilerini API cevabına
    // dönüştürürken bu constructor kullanılacak.
    public WalletResponse(Long id, BigDecimal balance, Long userId) {
        this.id = id;
        this.balance = balance;
        this.userId = userId;
    }
    // Cüzdan kimliğini döndürür.
    public Long getId() {
        return id;
    }

    // Güncel cüzdan bakiyesini döndürür.
    public BigDecimal getBalance() {
        return balance;
    }

    // Cüzdanın sahibi olan kullanıcının kimliğini döndürür.
    public Long getUserId() {
        return userId;
    }
}
