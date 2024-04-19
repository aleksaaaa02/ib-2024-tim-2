package com.bookify.pki.dto;


import com.bookify.pki.enumerations.CertificateType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificateRequestDTO {
    private Long id;
    private String subjectName;
    private String locality;
    private String country;
    private String email;
    private CertificateType certificateType;

}
