package cn.zs.middleware.sdk.infrastructure.openai.impl;

import cn.zs.middleware.sdk.infrastructure.openai.IOpenAI;
import cn.zs.middleware.sdk.domain.model.ChatCompletionRequestDTO;
import cn.zs.middleware.sdk.domain.model.ChatCompletionSyncResponseDTO;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import cn.zs.middleware.sdk.type.utils.BearerTokenUtils;
import com.alibaba.fastjson2.JSON;
public class ChatGLM implements IOpenAI {

    private final String apiHost;
    private final String apiKeySecret;

    public ChatGLM(String apiHost, String apiKeySecret) {
        this.apiHost = apiHost;
        this.apiKeySecret = apiKeySecret;
    }

    @Override
    public ChatCompletionSyncResponseDTO completions(ChatCompletionRequestDTO requestDTO) throws Exception {
      String token = BearerTokenUtils.getToken(apiKeySecret);

        URL url = new URL(apiHost);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + token);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = JSON.toJSONString(requestDTO).getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        connection.disconnect();

        return JSON.parseObject(content.toString(), ChatCompletionSyncResponseDTO.class);
    }
}
