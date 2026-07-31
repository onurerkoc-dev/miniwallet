package com.onurerkoc.miniwallet.service;
import org.springframework.stereotype.Service;
import com.onurerkoc.miniwallet.repository.WalletRepository;
import com.onurerkoc.miniwallet.dto.WalletResponse;
import com.onurerkoc.miniwallet.entity.Wallet;
import com.onurerkoc.miniwallet.exception.WalletNotFoundException;

import java.util.Optional;

@Service
public class WalletService {
    // Cüzdan bilgilerini MySQL veritabanından okuyabilmek için
    // WalletRepository nesnesini kullanacağız.
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
        Optional<Wallet> optionalWallet = walletRepository.findById(walletId);

        // Optional boşsa bu ID'ye sahip bir cüzdan bulunmamıştır.
        if (optionalWallet.isEmpty()) {
            throw new WalletNotFoundException(
                    "Cüzdan bulunamadı: " + walletId
            );
        }

        // Cüzdan bulunduğu için Optional içerisindeki Wallet nesnesini alıyoruz.
        Wallet wallet = optionalWallet.get();

        // Entity içerisindeki bilgileri API cevabında kullanacağımız
        // WalletResponse DTO'suna dönüştürüyoruz.
        return new WalletResponse(
                wallet.getId(),
                wallet.getBalance(),
                wallet.getUserId()
        );
    }
}
