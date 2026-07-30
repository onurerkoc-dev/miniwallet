package com.onurerkoc.miniwallet.dto;

// Bu DTO, kullanıcı oluşturulduktan veya görüntülendikten sonra
// API'nin istemciye hangi kullanıcı bilgilerini döndüreceğini belirler.
//
// Entity sınıfını doğrudan JSON olarak döndürmek yerine response DTO
// kullanarak API çıktısını kontrollü ve güvenli hâle getiriyoruz.
public class UserResponse {

    // Veritabanı tarafından otomatik oluşturulan kullanıcı kimliği.
    private Long id;

    // API cevabında gösterilecek kullanıcının adı.
    private String firstName;

    // API cevabında gösterilecek kullanıcının soyadı.
    private String lastName;

    // API cevabında gösterilecek kullanıcının e-posta adresi.
    private String email;

    // Bu nesneyi Service katmanında oluşturacağız.
    // Entity içerisindeki değerleri bu constructor'a göndererek
    // API'de döndüreceğimiz response nesnesini hazırlayacağız.
    public UserResponse(Long id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    // Jackson, getter metotlarını kullanarak bu Java nesnesini
    // JSON formatındaki API cevabına dönüştürür.
    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}