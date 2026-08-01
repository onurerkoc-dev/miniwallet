package com.onurerkoc.miniwallet.dto;
import com.onurerkoc.miniwallet.entity.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponse {
    // İşlem kaydının veritabanı kimliğini taşır.
    private Long id;

    // İşlemin DEPOSIT veya EXPENSE türünü taşır.
    private TransactionType type;

    // Para ekleme veya harcama tutarını taşır.
    private BigDecimal amount;

    // İşlemin açıklamasını taşır.
    private String description;

    // İşlemin gerçekleştirildiği tarih ve saati taşır.
    private LocalDateTime createdAt;

    // WalletTransaction entity bilgilerini API cevabına
// dönüştürürken bu constructor kullanılacak.
    public TransactionResponse(
            Long id,
            TransactionType type,
            BigDecimal amount,
            String description,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.createdAt = createdAt;
    }

    // İşlem kimliğini döndürür.
    public Long getId() {
        return id;
    }

    // İşlem türünü döndürür.
    public TransactionType getType() {
        return type;
    }

    // İşlem tutarını döndürür.
    public BigDecimal getAmount() {
        return amount;
    }

    // İşlem açıklamasını döndürür.
    public String getDescription() {
        return description;
    }

    // İşlemin oluşturulduğu tarih ve saati döndürür.
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
