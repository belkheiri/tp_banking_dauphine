/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.dauphine.accounts.dtos;

import fr.dauphine.accounts.enums.AccountType;
import java.sql.Timestamp;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author jihane and zouhair
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {

    
    @NotNull
    @NotEmpty
    private String iban;

    @NotNull
    private AccountType accountType;

    @NotNull
    private Long balance;

    @NotNull
    private Long accountFee;
    
    @NotNull
    private Long interest;
    
    @NotNull
    private Timestamp creationDate;

}
