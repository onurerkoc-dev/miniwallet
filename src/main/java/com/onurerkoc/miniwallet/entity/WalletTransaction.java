package com.onurerkoc.miniwallet.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDateTime;

// Bu sınıfın MySQL veritabanında saklanacak
// bir JPA entity'si olduğunu belirtir.
@Entity

// Bu entity'nin wallet_transactions
// tablosuyla eşleşmesini sağlar.
@Table(name = "wallet_transactions")
public class WalletTransaction {
    // Bu alanın tablonun primary key değeri olduğunu belirtir.
    @Id

    // ID değerinin MySQL tarafından otomatik
// oluşturulmasını sağlar.
    @GeneratedValue(strategy = GenerationType.IDENTITY)

// Java'daki id alanını tablodaki id sütunuyla eşleştirir.
    @Column(name = "id")
    private Long id;

    // TransactionType enum değerinin veritabanında
// DEPOSIT veya EXPENSE metni olarak saklanmasını sağlar.
    @Enumerated(EnumType.STRING)

// Java'daki type alanını tablodaki type sütunuyla eşleştirir.
// İşlem türü boş bırakılamaz.
    @Column(name = "type", nullable = false, length = 20)
    private TransactionType type;

    // Para değerlerinde hassasiyet kaybı yaşanmaması için
// işlem tutarını BigDecimal olarak saklıyoruz.
    @Column(
            name = "amount",
            nullable = false,
            precision = 19,
            scale = 2
    )
    private BigDecimal amount;

    // Para ekleme veya harcama işleminin açıklamasını saklar.
// Örneğin: "Aylık harçlık" veya "Market alışverişi".
    @Column(
            name = "description",
            nullable = false,
            length = 255
    )
    private String description;

    // Para ekleme veya harcama işleminin gerçekleştiği
// tarih ve saat bilgisini saklar.
    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    // İşlemin hangi cüzdana ait olduğunu belirtir.
// Karmaşık JPA ilişkisi yerine şimdilik cüzdan ID'sini
// doğrudan Long olarak saklıyoruz.
    @Column(
            name = "wallet_id",
            nullable = false
    )
    private Long walletId;

    // JPA, veritabanından gelen satırı WalletTransaction
// nesnesine dönüştürürken bu constructor'ı kullanır.
    public WalletTransaction() {
    }
    // Yeni bir para ekleme veya harcama işlemi
// oluşturmak için kullanılan constructor'dır.
    public WalletTransaction(TransactionType type, BigDecimal amount, String description, LocalDateTime createdAt, Long walletId) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.createdAt = createdAt;
        this.walletId = walletId;
    }

    // İşlemin veritabanı kimliğini döndürür.
    public Long getId() {
        return id;
    }

    // İşlemin DEPOSIT veya EXPENSE türünü döndürür.
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

    // İşlemin ait olduğu cüzdan kimliğini döndürür.
    public Long getWalletId() {
        return walletId;
    }
}
