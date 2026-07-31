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
import java.math.BigDecimal;
import java.util.Optional;

// Cüzdanla ilgili iş kurallarını ve veritabanı
// işlemlerini yöneten Service katmanıdır.
@Service
public class WalletService {

    // Cüzdan bilgilerini MySQL veritabanından okuyabilmek
    // ve güncelleyebilmek için WalletRepository kullanıyoruz.
    private final WalletRepository walletRepository;

    // Spring tarafından oluşturulan WalletRepository nesnesini
    // constructor üzerinden bu Service sınıfına alıyoruz.
    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
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
}