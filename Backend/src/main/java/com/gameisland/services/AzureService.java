package com.gameisland.services;

import com.gameisland.enums.StaticStrings;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class AzureService {
    public String GetJWTFromAzure() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("client_id", System.getenv("AZURE_CLIENT_ID"));
        form.add("client_secret", System.getenv("AZURE_CLIENT_SECRET"));
        form.add("grant_type", System.getenv("AZURE_GRANT_TYPE"));
        form.add("scope", System.getenv("AZURE_SCOPE"));

        //build
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(form, headers);

        RestTemplate rt = new RestTemplate();
        String url = StaticStrings.AZURE_TOKEN_URL.getUrl();
        ResponseEntity<String> response = rt.postForEntity(url, entity, String.class);
        String responseBody = response.getBody();

        Gson gson = new Gson();
        Token token = gson.fromJson(responseBody, Token.class);
        return token.access_token;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private class Token {
        private String token_type;
        private Long expires_in;
        private Long ext_expires_in;
        private String access_token;
    }
}
