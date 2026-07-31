package com.onurerkoc.miniwallet.service;

import com.onurerkoc.miniwallet.dto.CreateUserRequest;
import com.onurerkoc.miniwallet.dto.UserResponse;
import com.onurerkoc.miniwallet.entity.User;
import com.onurerkoc.miniwallet.exception.EmailAlreadyExistsException;
import com.onurerkoc.miniwallet.exception.UserNotFoundException;
import com.onurerkoc.miniwallet.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.onurerkoc.miniwallet.entity.Wallet;
import com.onurerkoc.miniwallet.repository.WalletRepository;


import java.util.Optional;

// Spring'e bu sınıfın iş kurallarını yöneten Service katmanı
// olduğunu bildiriyoruz.
@Service
public class UserService {

    // Kullanıcıları sorgulamak ve veritabanına kaydetmek için
    // UserRepository nesnesine ihtiyacımız var.
    private final UserRepository userRepository;
    // Yeni oluşturulan cüzdanı veritabanına kaydedebilmek için
    // WalletRepository nesnesini kullanacağız.
    private final WalletRepository walletRepository;

    // Spring, UserRepository ve WalletRepository nesnelerini oluşturur
    // ve constructor üzerinden UserService sınıfına verir.
    public UserService(
            UserRepository userRepository,
            WalletRepository walletRepository
    ) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
    }

    // Kullanıcı ve cüzdan kayıtlarının tek bir işlem olarak çalışmasını sağlar.
    // Cüzdan kaydedilemezse kullanıcı kaydı da geri alınır.
    @Transactional
    public UserResponse createUser(CreateUserRequest request) {

        // Aynı e-posta adresine sahip bir kullanıcı olup olmadığını
        // veritabanından kontrol ediyoruz.
        if (userRepository.existsByEmail(request.getEmail())) {

            // E-posta zaten kullanılıyorsa kayıt işlemini durduruyoruz.
            throw new EmailAlreadyExistsException(
                    "Bu e-posta adresi zaten kullanılıyor."
            );
        }

        // Request DTO içerisinden gelen bilgilerle
        // veritabanına kaydedilecek User entity'sini oluşturuyoruz.
        //
        // ID göndermiyoruz çünkü MySQL bu değeri otomatik oluşturacak.
        User user = new User(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail()
        );

        // save metodu JpaRepository'den hazır gelir.
        // Kullanıcı MySQL'e kaydedilir ve oluşturulan ID
        // dönen User nesnesinin içerisine yerleştirilir.
        User savedUser = userRepository.save(user);
        // Kullanıcı kaydedildikten sonra MySQL tarafından oluşturulan ID'yi
        // kullanarak başlangıç bakiyesi sıfır olan bir cüzdan oluşturuyoruz.
        Wallet wallet = new Wallet(savedUser.getId());

        // Oluşturduğumuz cüzdanı wallets tablosuna kaydediyoruz.
        walletRepository.save(wallet);

        // Kaydedilen User entity'sini doğrudan dışarıya vermiyoruz.
        // API cevabı olarak UserResponse DTO'suna dönüştürüyoruz.
        return new UserResponse(
                savedUser.getId(),
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getEmail()
        );
    }

    // Bu metotta veritabanına yeni veri yazmıyoruz.
    // Yalnızca kullanıcı okuyacağımız için transaction'ı
    // readOnly olarak işaretliyoruz.
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long userId) {

        // findById metodu JpaRepository'den hazır gelir.
        //
        // Kullanıcı bulunabileceği veya bulunamayacağı için
        // Spring sonucu Optional<User> olarak döndürür.
        Optional<User> optionalUser = userRepository.findById(userId);

        // Optional kutusu boşsa verilen ID değerine sahip
        // bir kullanıcı bulunamamış demektir.
        if (optionalUser.isEmpty()) {

            // İşlemi durdurup bu duruma özel exception fırlatıyoruz.
            throw new UserNotFoundException(
                    "ID değeri " + userId + " olan kullanıcı bulunamadı."
            );
        }

        // Bu noktaya geldiysek Optional kutusunun içinde
        // bir User nesnesi bulunduğunu biliyoruz.
        User user = optionalUser.get();

        // Bulduğumuz User entity'sini API'de döndüreceğimiz
        // UserResponse DTO'suna dönüştürüyoruz.
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
    }
}