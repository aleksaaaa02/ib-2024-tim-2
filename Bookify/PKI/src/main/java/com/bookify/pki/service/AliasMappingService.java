package com.bookify.pki.service;

import com.bookify.pki.model.CertificateAssociation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AliasMappingService {

    static final String ALIAS_MAP_FILENAME="/Users/borislavcelar/Documents/GitHub/ib-2024-tim-2/Bookify/PKI/certificates/alias_mapping.json";


    public String getCertificateAlias(Long certificateId){

        Map<Long, CertificateAssociation> certificates = convertJsonFileToHashMap(ALIAS_MAP_FILENAME);

        return certificates.get(certificateId).getAlias();

    }

    public List<Long> getSignedCertificateIds(Long issuerCertificateId){

        Map<Long, CertificateAssociation> certificates = convertJsonFileToHashMap(ALIAS_MAP_FILENAME);

        return certificates.get(issuerCertificateId).getSignedCertificates();

    }

    public void addSignedCertificate(Long issuerId,Long subjectId,String alias){

        HashMap<Long, CertificateAssociation> certificates = convertJsonFileToHashMap(ALIAS_MAP_FILENAME);
        certificates.put(subjectId,new CertificateAssociation(issuerId,alias,new ArrayList<>()));

        CertificateAssociation temp=certificates.get(issuerId);
        temp.getSignedCertificates().add(subjectId);

        serializeHashMapToJsonFile(certificates,ALIAS_MAP_FILENAME);

    }

    public static boolean serializeHashMapToJsonFile(HashMap<Long, CertificateAssociation> hashMap, String jsonFilePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Convert HashMap to JSON string
            // Write JSON string to file
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.writeValue(new File(jsonFilePath),hashMap);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static HashMap<Long, CertificateAssociation> convertJsonFileToHashMap(String jsonFilePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Read JSON content from file and convert to HashMap using TypeReference
            return objectMapper.readValue(new File(jsonFilePath), new TypeReference<HashMap<Long, CertificateAssociation>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
