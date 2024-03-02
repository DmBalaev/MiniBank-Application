package dm.bl.miniBank.client;

import dm.bl.miniBank.client.dto.ClientDto;
import dm.bl.miniBank.client.service.ClientService;
import dm.bl.miniBank.client.service.ClientUpdateService;
import dm.bl.miniBank.payload.ResponseApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static dm.bl.miniBank.constants.AppConstants.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
@Tag(name = "Client management", description = "Endpoints for finding clients")
public class ClientController {
    private final ClientService clientService;
    private final ClientUpdateService clientUpdateService;

    @Operation(
            summary = "Find clients by query",
            description = "Get a paginated list of clients based on specified search criteria",
            parameters = {
                    @Parameter(name = "pageNo", description = "Page number", in = ParameterIn.QUERY),
                    @Parameter(name = "pageSize", description = "Page size", in = ParameterIn.QUERY),
                    @Parameter(name = "sortBy", description = "Sort by field", in = ParameterIn.QUERY),
                    @Parameter(name = "sortDir", description = "Sort direction", in = ParameterIn.QUERY),
                    @Parameter(name = "birthDate", description = "Birth date", in = ParameterIn.QUERY),
                    @Parameter(name = "fullName", description = "Full name", in = ParameterIn.QUERY)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of clients returned successfully")
            }
    )
    @GetMapping("/search")
    public Page<ClientDto> findClientsByQuery(
            @RequestParam(value = "pageNo", defaultValue = DEFAULT_PAGE_NUMBER) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = DEFAULT_SORT_DIRECTION) String sortDir,
            @RequestParam(value = "birthDate", required = false) LocalDate birthDate,
            @RequestParam(value = "fullName", required = false) String fullName
    ){
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        return clientService.findBySearch(birthDate, fullName, pageable);
    }

    @Operation(
            summary = "Find client by phone",
            description = "Get a client by their phone number",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Client found successfully"),
                    @ApiResponse(responseCode = "404", description = "Client not found"),
                    @ApiResponse(responseCode = "400", description = "Wrong phone number"),

            }
    )
    @GetMapping("/search/by-phone")
    public ResponseEntity<ClientDto> findClientsByPhone(@RequestParam("phone") String phone) {
        return ResponseEntity.ok(clientService.findByPhone(phone));
    }

    @Operation(
            summary = "Find client by email",
            description = "Get a client by their email address",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Client found successfully"),
                    @ApiResponse(responseCode = "404", description = "Client not found"),
                    @ApiResponse(responseCode = "404", description = "Wrong email")
            }
    )
    @GetMapping("/search/by-email")
    public ResponseEntity<ClientDto> findClientsByEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(clientService.findByEmail(email));
    }

    @Operation(
            summary = "Add phone number to client",
            description = "Add a new phone number to the client's profile",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Phone number added successfully"),
                    @ApiResponse(responseCode = "400", description = "Phone number already exists or invalid")
            }
    )
    @PostMapping("/phone/{phone}")
    public ResponseEntity<ResponseApi> addPhone(
            @PathVariable("phone") String phone,
            @AuthenticationPrincipal UserDetails userDetails) {
        clientUpdateService.addPhone(userDetails, phone);
        return ResponseEntity.ok(new ResponseApi("Phone number added successfully"));
    }

    @Operation(
            summary = "Remove phone number from client",
            description = "Remove a phone number from the client's profile",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Phone number removed successfully"),
                    @ApiResponse(responseCode = "400", description = "Phone number not found or can't delete last phone number")
            }
    )
    @DeleteMapping("/phone/{phone}")
    public ResponseEntity<ResponseApi> removePhone(
            @PathVariable("phone") String phone,
            @AuthenticationPrincipal UserDetails userDetails) {
        clientUpdateService.removePhone(userDetails, phone);
        return ResponseEntity.ok(new ResponseApi("Phone number removed successfully"));
    }

    @Operation(
            summary = "Add email to client",
            description = "Add a new email address to the client's profile",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Email added successfully"),
                    @ApiResponse(responseCode = "400", description = "Email already exists or invalid")
            }
    )
    @PostMapping("/email/{email}")
    public ResponseEntity<ResponseApi> addEmail(
            @PathVariable("email") String email,
            @AuthenticationPrincipal UserDetails userDetails) {
        clientUpdateService.addEmail(userDetails, email);
        return ResponseEntity.ok(new ResponseApi("Email added successfully"));
    }

    @Operation(
            summary = "Remove email from client",
            description = "Remove an email address from the client's profile",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Email removed successfully"),
                    @ApiResponse(responseCode = "400", description = "Email not found or can't delete last email")
            }
    )
    @DeleteMapping("/emails/{email}")
    public ResponseEntity<ResponseApi> removeEmail(
            @PathVariable("email") String email,
            @AuthenticationPrincipal UserDetails userDetails) {
        clientUpdateService.removeEmail(userDetails, email);
        return ResponseEntity.ok(new ResponseApi("Email removed successfully"));
    }
}
