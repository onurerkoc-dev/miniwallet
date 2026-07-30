package com.onurerkoc.miniwallet.repository;

import com.onurerkoc.miniwallet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

// Bu interface, User entity'si için veritabanı işlemlerini gerçekleştirir.
//
// JpaRepository sayesinde save, findById, findAll ve deleteById gibi
// temel veritabanı metotları Spring Data JPA tarafından otomatik hazırlanır.
//
// İlk parametre olan User, hangi entity üzerinde çalışacağımızı belirtir.
// İkinci parametre olan Long ise User entity'sinin id alanının veri tipidir.
public interface UserRepository extends JpaRepository<User, Long> {

    // Spring Data JPA, metodun isminden otomatik olarak sorgu oluşturur.
    // existsByEmail ifadesi, verilen e-posta adresine sahip bir kullanıcı
    // olup olmadığını kontrol eder ve true veya false döndürür.
    // Spring Data JPA’nın method name query veya derived query denilen bir özelliği var. Spring metodun ismini kelimelere ayırıyor
    boolean existsByEmail(String email);
}