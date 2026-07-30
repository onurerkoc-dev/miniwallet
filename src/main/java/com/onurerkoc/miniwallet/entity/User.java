package com.onurerkoc.miniwallet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Bu sınıfın normal bir Java sınıfı olmasının yanında JPA tarafından
// yönetilen bir veritabanı varlığı olduğunu belirtiyoruz.
@Entity

// User sınıfını MySQL'deki app_users tablosuyla eşleştiriyoruz.
// "user" bazı veritabanlarında özel anlam taşıyabildiği için tablo adını app_users seçtik.
@Table(name = "app_users")
public class User {

    // Bu alanın tablonun primary key değeri olduğunu belirtiyoruz.
    @Id

    // IDENTITY stratejisiyle id değerini Java'nın değil, MySQL'in üretmesini sağlıyoruz.
    // MySQL tarafında bu alan AUTO_INCREMENT olarak oluşturulur.
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    // Java'daki id alanını veritabanındaki id sütunuyla eşleştiriyoruz.
    @Column(name = "id")
    private Long id;

    // Kullanıcının adı zorunludur ve en fazla 50 karakter olabilir.
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    // Kullanıcının soyadı zorunludur ve en fazla 50 karakter olabilir.
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    // E-posta zorunlu ve benzersizdir.
    // unique = true aynı e-posta adresinin iki kullanıcıda bulunmasını engeller.
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    // JPA, veritabanından gelen satırı Java nesnesine dönüştürürken
    // parametresiz constructor kullanır. Bu nedenle bu constructor gereklidir.
    public User() {
    }

    // Yeni kullanıcı oluştururken id göndermiyoruz.
    // Çünkü id değerini @GeneratedValue sayesinde MySQL otomatik oluşturacak.
    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    // Getter metotları private alanların değerlerini sınıf dışından okumamızı sağlar.
    // JPA field access kullandığı için id için setter yazmamız zorunlu değildir.
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

    // Setter metotları kullanıcı bilgilerinin değiştirilmesini sağlar.
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