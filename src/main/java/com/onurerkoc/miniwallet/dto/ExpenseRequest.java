package com.onurerkoc.miniwallet.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public class ExpenseRequest {
    // Harcama tutarının boş gönderilmesini engeller.
    @NotNull(message = "Harcama tutarı boş bırakılamaz")

// Harcama tutarının sıfırdan büyük olmasını zorunlu tutar.
// Böylece sıfır ve negatif harcamalar kabul edilmez.
    @Positive(message = "Harcama tutarı sıfırdan büyük olmalıdır")
    private BigDecimal amount;

    // Açıklamanın null, boş veya yalnızca boşluklardan
    // oluşmasını engeller.
    @NotBlank(message = "Açıklama boş bırakılamaz")

// Açıklamanın en fazla 255 karakter olmasını sağlar.
    @Size(max = 255, message = "Açıklama en fazla 255 karakter olabilir")
    private String description;
    // Jackson, gelen JSON'u ExpenseRequest nesnesine dönüştürürken önce bu parametresiz constructor ile boş bir nesne oluşturur.
    public ExpenseRequest() {
    }
    // Harcama tutarını okumamızı sağlar.
    public BigDecimal getAmount() {
        return amount;
    }

    // Jackson'ın JSON içerisindeki amount değerini
// bu nesnenin amount alanına yazmasını sağlar.
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    // Harcama açıklamasını okumamızı sağlar.
    public String getDescription() {
        return description;
    }

    // Jackson'ın JSON içerisindeki description değerini
// bu nesnenin description alanına yazmasını sağlar.
    public void setDescription(String description) {
        this.description = description;
    }
}
