package com.nttdata.bc.models;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accountId")
    private Integer accountId;

    @NotNull(message = "El campo clientId es requerido.")
    @Column(name = "clientId")
    private Integer clientId;

    @ManyToOne
    @JoinColumn(name = "debitCardId")
    private DebitCard debitCard;

    @DecimalMin(value = "0.0", message = "El campo amount debe tener un valor mínimo de '0.0'.")
    @Digits(integer = 10, fraction = 3, message = "El campo amount tiene un formato no válido (#####.000).")
    @NotNull(message = "El campo amount es requerido.")
    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "isMain")
    private Boolean isMain;

    @Column(name = "isActive")
    private Boolean isActive;

    @Column(name = "createdAt")
    private Instant createdAt;

    @Column(name = "updateddAt")
    private Instant updateddAt;
}
