package cn.zs.middleware.sdk.infrastructure.openai;

import cn.zs.middleware.sdk.domain.model.ChatCompletionRequestDTO;
import cn.zs.middleware.sdk.domain.model.ChatCompletionSyncResponseDTO;

public interface IOpenAI {
    ChatCompletionSyncResponseDTO completions(ChatCompletionRequestDTO requestDTO) throws Exception;
}
