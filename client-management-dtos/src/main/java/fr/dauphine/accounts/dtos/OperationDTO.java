/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.dauphine.accounts.dtos;

import java.sql.Timestamp;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author jihane and zouhair
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationDTO {

    @NotNull
    private Long idTransaction;

    @NotNull
    private Long amount;

    @NotNull
    private String transactionType;

    @NotNull
    private String ibanDestination;

    @NotNull
    private String ibanSource;

    @NotNull
    private Timestamp transactionDate;

}
