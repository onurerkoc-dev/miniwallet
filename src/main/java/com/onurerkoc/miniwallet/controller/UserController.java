package com.onurerkoc.miniwallet.controller;

import com.onurerkoc.miniwallet.dto.CreateUserRequest;
import com.onurerkoc.miniwallet.dto.UserResponse;
import com.onurerkoc.miniwallet.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

// Bu sınıfın HTTP isteklerini karşılayan bir REST Controller
// olduğunu Spring'e bildiriyoruz.
@RestController

// Bu Controller içerisindeki bütün endpointlerin
// /api/users adresiyle başlamasını sağlıyoruz.
@RequestMapping("/api/users")
public class UserController {

    // Controller veritabanıyla doğrudan konuşmaz.
    // Kullanıcı oluşturma işlemini gerçekleştirmesi için
    // UserService nesnesine ihtiyaç duyar.
    private final UserService userService;

    // Spring, oluşturduğu UserService nesnesini constructor
    // üzerinden Controller sınıfına verir.
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Bu metot POST /api/users isteğini karşılar.
    @PostMapping

    // Kullanıcı başarıyla oluşturulduğunda HTTP 201 Created
    // durum kodunun döndürülmesini sağlarız.
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(

            // @Valid, CreateUserRequest içerisindeki @NotBlank,
            // @Email ve @Size kontrollerini çalıştırır.
            @Valid

            // @RequestBody, Postman'dan gelen JSON verisini
            // CreateUserRequest nesnesine dönüştürür.
            @RequestBody CreateUserRequest request
    ) {

        // Controller iş kuralı uygulamaz.
        // Gelen isteği Service'e gönderir ve Service'in
        // hazırladığı cevabı istemciye döndürür.
        return userService.createUser(request);
    }
}