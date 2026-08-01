package com.onurerkoc.miniwallet.service;

import com.onurerkoc.miniwallet.dto.DepositRequest;
import com.onurerkoc.miniwallet.dto.WalletResponse;
import com.onurerkoc.miniwallet.entity.Wallet;
import com.onurerkoc.miniwallet.exception.WalletNotFoundException;
import com.onurerkoc.miniwallet.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.onurerkoc.miniwallet.dto.ExpenseRequest;
import com.onurerkoc.miniwallet.exception.InsufficientBalanceException;
import com.onurerkoc.miniwallet.entity.TransactionType;
import com.onurerkoc.miniwallet.entity.WalletTransaction;
import com.onurerkoc.miniwallet.repository.WalletTransactionRepository;
import com.onurerkoc.miniwallet.dto.TransactionResponse;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.util.Optional;

// Cüzdanla ilgili iş kurallarını ve veritabanı
// işlemlerini yöneten Service katmanıdır.
@Service
public class WalletService {

    // Cüzdan bilgilerini MySQL veritabanından okuyabilmek
    // ve güncelleyebilmek için WalletRepository kullanıyoruz.
    private final WalletRepository walletRepository;
    // Para ekleme ve harcama işlemlerini
    // wallet_transactions tablosuna kaydetmek için kullanılır.
    private final WalletTransactionRepository walletTransactionRepository;

    // Spring tarafından oluşturulan WalletRepository ve
    // WalletTransactionRepository nesnelerini Service sınıfına alıyoruz.
    public WalletService(
            WalletRepository walletRepository,
            WalletTransactionRepository walletTransactionRepository
    ) {

        // Cüzdan bakiyesini okumak ve güncellemek için kullanılır.
        this.walletRepository = walletRepository;

        // Para ekleme ve harcama geçmişini kaydetmek için kullanılır.
        this.walletTransactionRepository = walletTransactionRepository;
    }

    // Verilen cüzdan ID'sine sahip cüzdanı veritabanında arar.
    public WalletResponse getWalletById(Long walletId) {

        // findById metodu JpaRepository tarafından hazır olarak sağlanır.
        // Cüzdan bulunabileceği veya bulunamayacağı için Optional döndürür.
        Optional<Wallet> optionalWallet =
                walletRepository.findById(walletId);

        // Optional boşsa bu ID'ye sahip bir cüzdan bulunmamıştır.
        if (optionalWallet.isEmpty()) {
            throw new WalletNotFoundException(
                    "Cüzdan bulunamadı: " + walletId
            );
        }

        // Cüzdan bulunduğu için Optional içerisindeki
        // Wallet nesnesini alıyoruz.
        Wallet wallet = optionalWallet.get();

        // Entity içerisindeki bilgileri API cevabında kullanacağımız
        // WalletResponse DTO'suna dönüştürüyoruz.
        WalletResponse walletResponse = new WalletResponse(
                wallet.getId(),
                wallet.getBalance(),
                wallet.getUserId()
        );

        // Hazırlanan cüzdan cevabını Controller katmanına döndürüyoruz.
        return walletResponse;
    }

    // Cüzdana para ekleme işlemini tek bir veritabanı
    // işlemi içerisinde gerçekleştirir.
    @Transactional
    public WalletResponse deposit(
            Long walletId,
            DepositRequest request
    ) {

        // URL'den gelen walletId değerine sahip cüzdanı
        // veritabanında arıyoruz.
        Optional<Wallet> optionalWallet =
                walletRepository.findById(walletId);

        // Cüzdan bulunamazsa para ekleme işlemine devam etmiyoruz.
        if (optionalWallet.isEmpty()) {
            throw new WalletNotFoundException(
                    "Cüzdan bulunamadı: " + walletId
            );
        }

        // Optional içerisindeki mevcut cüzdan nesnesini alıyoruz.
        Wallet wallet = optionalWallet.get();

        // Cüzdanın işlem öncesindeki bakiyesini alıyoruz.
        BigDecimal currentBalance = wallet.getBalance();

        // Postman'dan gelen para ekleme tutarını DTO'dan alıyoruz.
        BigDecimal depositAmount = request.getAmount();

        // BigDecimal değerleri + operatörüyle toplanamaz.
        // add() metodu mevcut bakiyeye yeni tutarı ekler.
        BigDecimal updatedBalance =
                currentBalance.add(depositAmount);

        // Hesaplanan yeni bakiyeyi Wallet nesnesine yazıyoruz.
        wallet.setBalance(updatedBalance);

        // Güncellenen cüzdanı MySQL veritabanına kaydediyoruz.
        Wallet savedWallet = walletRepository.save(wallet);
        // Başarılı para ekleme işlemini geçmişe kaydetmek için
        // DEPOSIT türünde yeni bir WalletTransaction oluşturuyoruz.
        WalletTransaction transaction = new WalletTransaction(
                TransactionType.DEPOSIT,
                request.getAmount(),
                request.getDescription(),
                savedWallet.getId()
        );

// Oluşturduğumuz işlem kaydını
// wallet_transactions tablosuna kaydediyoruz.
        walletTransactionRepository.save(transaction);

        // Güncellenmiş cüzdan bilgilerini API cevabı olacak
        // WalletResponse nesnesine dönüştürüyoruz.
        WalletResponse walletResponse = new WalletResponse(
                savedWallet.getId(),
                savedWallet.getBalance(),
                savedWallet.getUserId()
        );

        // Güncellenmiş cüzdan cevabını Controller katmanına döndürüyoruz.
        return walletResponse;
    }
    // Cüzdandan para harcama işlemini tek bir veritabanı
// işlemi içerisinde gerçekleştirir.
    @Transactional
    public WalletResponse expense(
            Long walletId,
            ExpenseRequest request
    ) {

        // URL'den gelen walletId değerine sahip cüzdanı
        // veritabanında arıyoruz.
        Optional<Wallet> optionalWallet =
                walletRepository.findById(walletId);

        // Cüzdan bulunamazsa harcama işlemine devam etmiyoruz.
        if (optionalWallet.isEmpty()) {
            throw new WalletNotFoundException(
                    "Cüzdan bulunamadı: " + walletId
            );
        }

        // Optional içerisindeki mevcut cüzdan nesnesini alıyoruz.
        Wallet wallet = optionalWallet.get();

        // Cüzdanın mevcut bakiyesini alıyoruz.
        BigDecimal currentBalance = wallet.getBalance();

        // Postman'dan gönderilen harcama tutarını alıyoruz.
        BigDecimal expenseAmount = request.getAmount();

        // Mevcut bakiye ile harcama tutarını karşılaştırıyoruz.
        int comparisonResult =
                currentBalance.compareTo(expenseAmount);

        // Karşılaştırma sonucu negatifse mevcut bakiye,
        // harcama tutarından küçüktür.
        if (comparisonResult < 0) {
            throw new InsufficientBalanceException(
                    "Yetersiz bakiye"
            );
        }

        // Bakiye yeterliyse harcama tutarını
        // mevcut bakiyeden çıkarıyoruz.
        BigDecimal updatedBalance =
                currentBalance.subtract(expenseAmount);

        // Hesaplanan yeni bakiyeyi Wallet nesnesine yazıyoruz.
        wallet.setBalance(updatedBalance);

        // Güncellenen cüzdanı MySQL veritabanına kaydediyoruz.
        Wallet savedWallet = walletRepository.save(wallet);
        // Başarılı para harcama işlemini geçmişe kaydetmek için
        // EXPENSE türünde yeni bir WalletTransaction oluşturuyoruz.
        WalletTransaction transaction = new WalletTransaction(
                TransactionType.EXPENSE,
                request.getAmount(),
                request.getDescription(),
                savedWallet.getId()
        );

// Oluşturduğumuz işlem kaydını
// wallet_transactions tablosuna kaydediyoruz.
        walletTransactionRepository.save(transaction);
        // Güncellenmiş cüzdan bilgilerini API cevabında
        // kullanacağımız WalletResponse nesnesine dönüştürüyoruz.
        WalletResponse walletResponse = new WalletResponse(
                savedWallet.getId(),
                savedWallet.getBalance(),
                savedWallet.getUserId()
        );

        // Güncellenmiş cüzdan cevabını Controller katmanına döndürüyoruz.
        return walletResponse;
    }
    // Verilen cüzdan ID'sine ait işlem geçmişini
// en yeni işlemden en eski işleme doğru döndürür.
    public List<TransactionResponse> getTransactionsByWalletId(
            Long walletId
    ) {

        // Önce istenen cüzdanın gerçekten var olup
        // olmadığını kontrol ediyoruz.
        Optional<Wallet> optionalWallet =
                walletRepository.findById(walletId);

        // Cüzdan bulunamazsa işlem geçmişini aramaya
        // devam etmiyoruz.
        if (optionalWallet.isEmpty()) {
            throw new WalletNotFoundException(
                    "Cüzdan bulunamadı: " + walletId
            );
        }

        // Repository'den bu cüzdana ait işlem entity'lerini
        // en yeniden en eskiye sıralanmış şekilde alıyoruz.
        List<WalletTransaction> transactions =
                walletTransactionRepository
                        .findByWalletIdOrderByCreatedAtDesc(walletId);

        // API cevabında döndüreceğimiz DTO'ları saklamak için
        // başlangıçta boş bir liste oluşturuyoruz.
        List<TransactionResponse> responses =
                new ArrayList<>();

        // Entity listesindeki her işlem kaydını sırayla geziyoruz.
        for (WalletTransaction transaction : transactions) {

            // Mevcut WalletTransaction entity'sini
            // TransactionResponse DTO'suna dönüştürüyoruz.
            TransactionResponse response = new TransactionResponse(
                    transaction.getId(),
                    transaction.getType(),
                    transaction.getAmount(),
                    transaction.getDescription(),
                    transaction.getCreatedAt()
            );

            // Oluşturduğumuz DTO'yu cevap listesine ekliyoruz.
            responses.add(response);
        }

        // Hazırlanan DTO listesini Controller katmanına döndürüyoruz.
        return responses;
    }
}