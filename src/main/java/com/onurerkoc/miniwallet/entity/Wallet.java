package com.onurerkoc.miniwallet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

// Bu sınıfın JPA tarafından yönetilen bir
// veritabanı varlığı olduğunu belirtiyoruz.
@Entity

// Wallet sınıfını MySQL'deki wallets tablosuyla eşleştiriyoruz.
@Table(name = "wallets")
public class Wallet {

    // Bu alanın wallets tablosunun primary key değeri
    // olduğunu belirtiyoruz.
    @Id

    // Cüzdan ID'sini MySQL'in otomatik oluşturmasını sağlıyoruz.
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    // Java'daki id alanını MySQL'deki id sütunuyla eşleştiriyoruz.
    @Column(name = "id")
    private Long id;

    // Cüzdanın mevcut bakiyesini tutuyoruz.
    //
    // precision = 19 toplam basamak sayısını,
    // scale = 2 ise virgülden sonraki basamak sayısını belirler.
    //
    // Örneğin 1250.75 değeri iki kuruş basamağıyla saklanabilir.
    @Column(name = "balance", nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;

    // Cüzdanın hangi kullanıcıya ait olduğunu kullanıcının ID'siyle tutuyoruz.
    //
    // unique = true sayesinde aynı kullanıcı ID'siyle
    // ikinci bir cüzdan oluşturulamaz.
    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;
    // JPA, veritabanından gelen cüzdan kaydını Java nesnesine
// dönüştürürken parametresiz constructor kullanır.
    public Wallet() {
    }

    // Yeni cüzdan oluştururken yalnızca cüzdanın sahibi olan
// kullanıcının ID değerini gönderiyoruz.
    public Wallet(Long userId) {

        // Yeni oluşturulan her cüzdanın başlangıç bakiyesini
        // kesin olarak 0 olarak belirliyoruz.
        this.balance = BigDecimal.ZERO;

        // Constructor'a gelen kullanıcı ID'sini
        // Wallet nesnesinin userId alanına yerleştiriyoruz.
        this.userId = userId;
    }
    // Cüzdanın MySQL tarafından oluşturulan ID değerini döndürür.
    public Long getId() {
        return id;
    }

    // Cüzdanın mevcut bakiyesini döndürür.
    public BigDecimal getBalance() {
        return balance;
    }

    // Cüzdanın hangi kullanıcıya ait olduğunu belirten
// kullanıcı ID değerini döndürür.
    public Long getUserId() {
        return userId;
    }

    // Para ekleme ve harcama işlemlerinden sonra
// cüzdan bakiyesini güncellememizi sağlar.
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}