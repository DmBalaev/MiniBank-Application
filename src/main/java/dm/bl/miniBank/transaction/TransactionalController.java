package dm.bl.miniBank.transaction;

import dm.bl.miniBank.client.Client;
import dm.bl.miniBank.payload.TransactionalRequest;
import dm.bl.miniBank.transaction.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactional")
@RequiredArgsConstructor
@Tag(name = "Transactional management", description = "Endpoints for managing transactions")
public class TransactionalController {
    private final TransactionService transactionService;

    @Operation(
            summary = "Transfer money",
            description = "Transfer money from the current authenticated client to the specified recipient",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Transfer request body",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionalRequest.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Transaction successful"),
                    @ApiResponse(responseCode = "400", description = "Invalid request body"),
                    @ApiResponse(responseCode = "404", description = "Recipient not found"),
                    @ApiResponse(responseCode = "422", description = "Insufficient funds")
            }
    )
    @PostMapping
    public ResponseEntity<Transaction> transfer(
            @AuthenticationPrincipal Client currentClient,
            @Valid @RequestBody TransactionalRequest transferRequest
    ) {
        return ResponseEntity.ok(transactionService.createTransactional(
                currentClient, transferRequest.recipientPhoneNumber(), transferRequest.amount()));
    }

    @Operation(
            summary = "Get my transactions",
            description = "Get a list of transactions performed by the current authenticated client",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully")
            }
    )
    @GetMapping
    public List<Transaction> getMyTransactional(@AuthenticationPrincipal Client currentClient){
        return transactionService.getMyTransactions(currentClient);
    }
}
