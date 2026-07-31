package com.onurerkoc.miniwallet.repository;


import com.onurerkoc.miniwallet.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Bu interface, Wallet entity'si için veritabanı
// işlemlerini gerçekleştirmemizi sağlar.
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    // Verilen kullanıcı ID'sine ait cüzdanı arar.
    //
    // Kullanıcının cüzdanı bulunabileceği veya bulunamayacağı için
    // sonuç Optional<Wallet> olarak döner.
    Optional<Wallet> findByUserId(Long userId);
}