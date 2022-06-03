package com.gameisland.services;

import com.google.gson.Gson;
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
        String url = "https://login.microsoftonline.com/3caeff68-27dd-4cf7-85c1-a587bfbd0b4f/oauth2/v2.0/token";
        ResponseEntity<String> response = rt.postForEntity(url, entity, String.class);
        String responseBody = response.getBody();

        Gson gson = new Gson();
        Token token = gson.fromJson(responseBody, Token.class);
        return token.access_token;
    }


    private class Token {
        private String token_type;
        private Long expires_in;
        private Long ext_expires_in;
        private String access_token;

        public Token() {
        }

        public Token(String token_type, Long expires_in, Long ext_expires_in, String access_token) {
            this.token_type = token_type;
            this.expires_in = expires_in;
            this.ext_expires_in = ext_expires_in;
            this.access_token = access_token;
        }

        public String getToken_type() {
            return token_type;
        }

        public void setToken_type(String token_type) {
            this.token_type = token_type;
        }

        public Long getExpires_in() {
            return expires_in;
        }

        public void setExpires_in(Long expires_in) {
            this.expires_in = expires_in;
        }

        public Long getExt_expires_in() {
            return ext_expires_in;
        }

        public void setExt_expires_in(Long ext_expires_in) {
            this.ext_expires_in = ext_expires_in;
        }

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }
    }
}
