package pro.sky.telegram_bot_pets_shelter.service.imp;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.Message;
import pro.sky.telegram_bot_pets_shelter.exception_handling.UploadFileEXception;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class FileServiceImp {
    @Value("${TELEGRAM_BOT_TOKEN_TEST}")
    private String token;
    @Value("${service.file_info.uri}")
    private String fileInfoUri;
    @Value("${service.file_storage.uri}")
    private String fileStorage;

    public FileServiceImp() {
    }

    public byte[] processPhoto(Message telegramMessage) {
        String fileId = telegramMessage.getPhoto().get(0).getFileId();
        ResponseEntity<String> response = getFilePath(fileId);
        byte[] fileByte = new byte[0];
        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject jsonObject = new JSONObject(response.getBody());
            String filePath = String.valueOf(jsonObject
                    .getJSONObject("result")
                    .getString("file_path"));
            fileByte = downLoadFile(filePath);
        }
        return fileByte;
    }

    private ResponseEntity<String> getFilePath(String fileId) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        return restTemplate.exchange(
                fileInfoUri,
                HttpMethod.GET,
                request,
                String.class,
                token, fileId
        );
    }

    private byte[] downLoadFile(String filePath) {
        String fullUri = fileInfoUri.replace("{token}", token)
                .replace("{filePath}", filePath);
        URL url;
        try {
            url = new URL(fullUri);
        } catch (MalformedURLException e) {
            throw new UploadFileEXception(e);
        }
        try (InputStream is = url.openStream()) {
            return is.readAllBytes();
        } catch (IOException e) {
            throw new UploadFileEXception(url.toExternalForm(), e);
        }
    }
}
