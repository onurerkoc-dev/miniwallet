package com.onurerkoc.miniwallet.service;

import com.onurerkoc.miniwallet.dto.CreateUserRequest;
import com.onurerkoc.miniwallet.dto.UserResponse;
import com.onurerkoc.miniwallet.entity.User;
import com.onurerkoc.miniwallet.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Spring'e bu sınıfın iş kurallarını yöneten Service katmanı
// olduğunu bildiriyoruz.
@Service
public class UserService {

    // Kullanıcıyı veritabanına kaydetmek için
    // UserRepository nesnesine ihtiyacımız var.
    private final UserRepository userRepository;

    // Spring, oluşturduğu UserRepository nesnesini
    // constructor üzerinden bize verir.
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Kullanıcı kaydetme işlemini bir veritabanı işlemi
    // yani transaction olarak çalıştırıyoruz.
    @Transactional
    public UserResponse createUser(CreateUserRequest request) {

        // Postman'dan gelen DTO bilgileriyle
        // veritabanına kaydedilecek User nesnesini oluşturuyoruz.
        User user = new User(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail()
        );

        // User nesnesini MySQL'e kaydediyoruz.
        // MySQL'in oluşturduğu id değeri savedUser içerisine gelir.
        User savedUser = userRepository.save(user);

        // Kaydedilen kullanıcı bilgilerini API cevabı olacak
        // UserResponse nesnesine dönüştürüyoruz.
        return new UserResponse(
                savedUser.getId(),
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getEmail()
        );
    }
}