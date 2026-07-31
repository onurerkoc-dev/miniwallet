package com.onurerkoc.miniwallet.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.onurerkoc.miniwallet.service.WalletService;
import com.onurerkoc.miniwallet.dto.WalletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.onurerkoc.miniwallet.dto.DepositRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


// Bu sınıfın HTTP isteklerini karşılayan bir REST Controller
// olduğunu Spring'e bildirir.
@RestController

// Bu Controller içerisindeki bütün endpoint'lerin
// ortak başlangıç adresini belirler.
@RequestMapping("/api/wallets")
public class WalletController {
    // Controller'a gelen cüzdan isteklerini işlemek için
    // WalletService nesnesini kullanacağız.
    private final WalletService walletService;
    // Spring tarafından oluşturulan WalletService nesnesini
    // constructor üzerinden Controller sınıfına alıyoruz.
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }
    // URL içerisinden gelen walletId değerine göre
// cüzdan bilgilerini döndürür.
// Bu metot GET /api/wallets/{walletId} isteğini karşılar.
// Örneğin /api/wallets/3 adresindeki 3 değeri cüzdan ID'sidir.
    @GetMapping("/{walletId}")
    public WalletResponse getWalletById(

            // @GetMapping içerisindeki {walletId} değerini alır
            // ve aşağıdaki Long walletId değişkenine aktarır.
            @PathVariable("walletId") Long walletId
    )
{


        // URL'den aldığımız cüzdan ID'sini Service katmanına göndeririz.
        WalletResponse walletResponse =
                walletService.getWalletById(walletId);

        // Service katmanından gelen cevabı API kullanıcısına döndürürüz.
        return walletResponse;
    }
    // Bu metot POST /api/wallets/{walletId}/deposit
// isteğini karşılar.
    @PostMapping("/{walletId}/deposit")
    public WalletResponse deposit(

            // URL içerisindeki {walletId} değerini açık şekilde
            // Long walletId değişkenine aktarır.
            @PathVariable("walletId") Long walletId,

            // JSON isteğini DepositRequest nesnesine dönüştürür.
            // @Valid ise DTO içerisindeki validation kurallarını çalıştırır.
            @Valid @RequestBody DepositRequest request
    ) {

        // Cüzdan ID'sini ve para ekleme isteğini
        // Service katmanına gönderiyoruz.
        WalletResponse walletResponse =
                walletService.deposit(walletId, request);

        // Güncellenmiş cüzdan bilgilerini API cevabı olarak döndürüyoruz.
        return walletResponse;
    }
}
