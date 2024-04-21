package com.bookify.pki.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewCertificateRequestDTO {
    private Long userId;
    private String givenName;
    private String surname;
    private String locality;
    private String country;
    private String email;

}
