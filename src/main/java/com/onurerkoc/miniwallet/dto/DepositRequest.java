package com.onurerkoc.miniwallet.dto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class DepositRequest {
    // Para ekleme tutarının boş gönderilmesini engeller.
    @NotNull(message = "Para ekleme tutarı boş bırakılamaz")

// Tutarın sıfırdan büyük olmasını zorunlu tutar.
// Böylece sıfır ve negatif tutarlar kabul edilmez.
    @Positive(message = "Para ekleme tutarı sıfırdan büyük olmalıdır")
    private BigDecimal amount;

    // Açıklamanın null, boş veya yalnızca boşluklardan
    // oluşmasını engeller.
    @NotBlank(message = "Açıklama boş bırakılamaz")

// Veritabanında gereksiz büyüklükte açıklamalar
// saklanmasını engellemek için sınır koyuyoruz.
    @Size(max = 255, message = "Açıklama en fazla 255 karakter olabilir")
    private String description;

    // Jackson, gelen JSON'u Java nesnesine dönüştürürken
    // önce bu parametresiz constructor ile boş bir nesne oluşturur.
    public DepositRequest() {
    }
    // Para ekleme tutarını okumamızı sağlar.
    public BigDecimal getAmount() {
        return amount;
    }

    // Jackson'ın JSON içerisindeki amount değerini
// bu nesnenin amount alanına yazmasını sağlar.
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    // Para ekleme açıklamasını okumamızı sağlar.
    public String getDescription() {
        return description;
    }

    // Jackson'ın JSON içerisindeki description değerini
// bu nesnenin description alanına yazmasını sağlar.
    public void setDescription(String description) {
        this.description = description;
    }
}
