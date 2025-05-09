package cn.zs.middleware.sdk;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.zs.middleware.sdk.infrastructure.weixin.WeiXin;
import cn.zs.middleware.sdk.infrastructure.openai.IOpenAI;
import cn.zs.middleware.sdk.infrastructure.openai.impl.ChatGLM;
import cn.zs.middleware.sdk.infrastructure.git.GitCommand;
import cn.zs.middleware.sdk.service.impl.OpenAiCodeReviewService;


/**
 * @Description:
 * @Auther: zs
 * @Date: 2025/05/07/14:40
 */
public class OpenAiCodeReview {
   
    private static final Logger logger = LoggerFactory.getLogger(OpenAiCodeReview.class);

    // 配置配置
    private String weixin_appid = "wxe276793c5cd09647";
    private String weixin_secret = "138e296ff10272684b89b33221f63eef";
    private String weixin_touser = "oiSS47T-R9hvfGpMHX78ubl2OTU8";
    private String weixin_template_id = "WR-q1ddHT5z6HGSox9I1H4yC1eztWDw70RfanMA-eCg";

    // ChatGLM 配置
    private String chatglm_apiHost = "https://open.bigmodel.cn/api/paas/v4/chat/completions";
    private String chatglm_apiKeySecret = "";

    // Github 配置
    private String github_review_log_uri;
    private String github_token;

    // 工程配置 - 自动获取
    private String github_project;
    private String github_branch;
    private String github_author;

    public static void main(String[] args) throws Exception {
        GitCommand gitCommand = new GitCommand(
                getEnv("GITHUB_REVIEW_LOG_URI"),
                getEnv("GITHUB_TOKEN"),
                getEnv("COMMIT_PROJECT"),
                getEnv("COMMIT_BRANCH"),
                getEnv("COMMIT_AUTHOR"),
                getEnv("COMMIT_MESSAGE")
        );

        /**
         * 项目：{{repo_name.DATA}} 分支：{{branch_name.DATA}} 作者：{{commit_author.DATA}} 说明：{{commit_message.DATA}}
         */
        WeiXin weiXin = new WeiXin(
                getEnv("WEIXIN_APPID"),
                getEnv("WEIXIN_SECRET"),
                getEnv("WEIXIN_TOUSER"),
                getEnv("WEIXIN_TEMPLATE_ID")
        );



        IOpenAI openAI = new ChatGLM(getEnv("CHATGLM_APIHOST"), getEnv("CHATGLM_APIKEYSECRET"));

        OpenAiCodeReviewService openAiCodeReviewService = new OpenAiCodeReviewService(gitCommand, openAI, weiXin);
        openAiCodeReviewService.exec();

        logger.info("openai-code-review done!");
    }

    private static String getEnv(String key) {
        String value = System.getenv(key);
        if (null == value || value.isEmpty()) {
            throw new RuntimeException("value is null");
        }
        return value;
    }
}
