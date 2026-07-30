package com.onurerkoc.miniwallet.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// Bu DTO, kullanıcı oluşturma isteğinde istemciden gelecek
// JSON verilerini Java nesnesi olarak karşılamak için kullanılır.
//
// Entity sınıfını doğrudan dışarıya açmak yerine DTO kullanarak
// API'nin hangi verileri kabul edeceğini kontrollü şekilde belirliyoruz.
public class CreateUserRequest {

    // @NotBlank, değerin null, boş veya yalnızca boşluklardan
    // oluşmasını engeller.
    // @Size ile gelen adın veritabanındaki varchar(50)
    // sınırını aşmasını engelliyoruz.
    @NotBlank(message = "Ad alanı boş bırakılamaz.")
    @Size(max = 50, message = "Ad en fazla 50 karakter olabilir.")
    private String firstName;

    // Kullanıcının soyadı zorunludur ve veritabanındaki
    // sütun sınırıyla uyumlu olarak en fazla 50 karakter olabilir.
    @NotBlank(message = "Soyad alanı boş bırakılamaz.")
    @Size(max = 50, message = "Soyad en fazla 50 karakter olabilir.")
    private String lastName;

    // E-posta alanının boş bırakılmasını engelliyoruz.
    @NotBlank(message = "E-posta alanı boş bırakılamaz.")

    // Gelen metnin geçerli bir e-posta biçiminde olup olmadığını kontrol ediyoruz.
    @Email(message = "Geçerli bir e-posta adresi girilmelidir.")

    // Entity sınıfındaki email sütunu en fazla 100 karakter olduğu için DTO üzerinde de aynı sınırı uyguluyoruz.
    @Size(max = 100, message = "E-posta en fazla 100 karakter olabilir.")
    private String email;

    // Jackson, gelen JSON verisini Java nesnesine dönüştürürken önce parametresiz constructor ile boş bir nesne oluşturur.
    public CreateUserRequest() {
    }

    // Getter metotları, Controller ve Service katmanlarının DTO içerisindeki değerleri okuyabilmesini sağlar.
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    // Setter metotları, Jackson'ın JSON'dan aldığı değerleri
    // oluşturduğu DTO nesnesinin alanlarına yerleştirmesini sağlar.
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}