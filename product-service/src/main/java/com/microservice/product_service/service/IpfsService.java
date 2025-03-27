package com.microservice.product_service.service;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class IpfsService {

    @Value("${pinata.api.key}")
    private String pinataApiKey;

    @Value("${pinata.api.secret}")
    private String pinataApiSecret;

    private static final String PINATA_URL = "https://api.pinata.cloud/pinning/pinFileToIPFS";

    public String uploadToIpfs(MultipartFile file) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(PINATA_URL);
            post.setHeader("pinata_api_key", pinataApiKey);
            post.setHeader("pinata_secret_api_key", pinataApiSecret);

            var entity = MultipartEntityBuilder.create()
                    .addBinaryBody("file", file.getInputStream(),
                            org.apache.hc.core5.http.ContentType.MULTIPART_FORM_DATA, file.getOriginalFilename())
                    .build();
            post.setEntity(entity);

            try (CloseableHttpResponse response = httpClient.execute(post)) {
                String json = EntityUtils.toString(response.getEntity());
                JSONObject jsonResponse = new JSONObject(json);
                return "https://gateway.pinata.cloud/ipfs/" + jsonResponse.getString("IpfsHash");
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }
}