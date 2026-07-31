package com.onurerkoc.miniwallet.entity;

// Bir cüzdan işleminin yalnızca para ekleme
// veya para harcama türünde olmasını sağlar.
public enum TransactionType {

    // Cüzdana para eklendiğini belirtir.
    DEPOSIT,

    // Cüzdandan para harcandığını belirtir.
    EXPENSE
}