package fr.dauphine.remotes;

import fr.dauphine.accounts.dtos.OperationDTO;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientProperties.FeignClientConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jihane and zouhair
 */
@FeignClient(name = "SERVICE-TRANSACTION", configuration = FeignClientConfiguration.class)
public interface RemoteTransactionAPI {
    
    @GetMapping("/transactions/find_transactions_by_iban")
    public ResponseEntity<List<OperationDTO>> findAllTransactionsByIban(
            @RequestParam(name = "iban", required = true) String iban);
    
    
}
