package com.vsr.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id_pk")
    private Long id;

    // Direct relationship to Order
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // Direct relationship to User
    @Column(name = "user_id")
    private Long userId;

    private String paymentTransactionId; // Gateway ID (e.g. Razorpay payment ID)
    private String modeOfPayment;
    private String paymentStatus; // PENDING, PAID, FAILED
    private Double amount;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getPaymentTransactionId() { return paymentTransactionId; }
    public void setPaymentTransactionId(String paymentTransactionId) { this.paymentTransactionId = paymentTransactionId; }

    public String getModeOfPayment() { return modeOfPayment; }
    public void setModeOfPayment(String modeOfPayment) { this.modeOfPayment = modeOfPayment; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
}