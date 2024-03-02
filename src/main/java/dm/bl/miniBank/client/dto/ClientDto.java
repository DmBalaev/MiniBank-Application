package dm.bl.miniBank.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String sureName;
    private List<String> phonesNumbers;
    private List<String> emails;
}
