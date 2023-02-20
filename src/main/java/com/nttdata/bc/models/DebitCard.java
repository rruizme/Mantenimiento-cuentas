package com.nttdata.bc.models;

import java.time.Instant;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DebitCards")
public class DebitCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "debitCardId")
    private Integer debitCardId;

    @NotNull(message = "El campo accountId es requerido.")
    private Integer accountId;

    @Column(name = "cardNumber", nullable = false, length = 40)
    private String cardNumber; // número de la tarjeta

    @Column(name = "pin", nullable = false, length = 4)
    private String pin;

    @Column(name = "expirationDate", nullable = false, length = 5)
    private String expirationDate; // fecha de vencimiento

    @Column(name = "cardValidationCode", nullable = false, length = 3)
    private String cardValidationCode; // código de validación de la tarjeta

    @Column(name = "isActive")
    private Boolean isActive;

    @Column(name = "createdAt")
    private Instant createdAt;

    @Column(name = "updateddAt")
    private Instant updateddAt;

    @OneToMany
    private List<Account> accounts;
}
