package com.onurerkoc.miniwallet.repository;
import com.onurerkoc.miniwallet.entity.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// WalletTransaction entity'si için veritabanı
// işlemlerini gerçekleştiren Repository katmanıdır.
public interface WalletTransactionRepository
        extends JpaRepository<WalletTransaction, Long> {
    // Verilen walletId değerine ait işlem kayıtlarını bulur
// ve createdAt alanına göre en yeniden en eskiye sıralar.
    List<WalletTransaction> findByWalletIdOrderByCreatedAtDesc(
            Long walletId
    );
}