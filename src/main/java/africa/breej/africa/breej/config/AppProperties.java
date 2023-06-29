package africa.breej.africa.breej.config;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "app")
@Getter
@Setter
@Component
public class AppProperties {
    private final Auth auth = new Auth();
    private final OAuth2 oauth2 = new OAuth2();
    private final MyServiceAgent myServiceAgent = new MyServiceAgent();
    private final OtpConfig otpConfig = new OtpConfig();
    private final AmazonConfig amazonConfig = new AmazonConfig();
    private String forbiddenExceptionMessage;

    public static class Auth {
        private String tokenSecret;
        private long tokenExpirationMsec;

        public String getTokenSecret() {
            return tokenSecret;
        }

        public void setTokenSecret(String tokenSecret) {
            this.tokenSecret = tokenSecret;
        }

        public long getTokenExpirationMsec() {
            return tokenExpirationMsec;
        }

        public void setTokenExpirationMsec(long tokenExpirationMsec) {
            this.tokenExpirationMsec = tokenExpirationMsec;
        }
    }

    public static final class OAuth2 {
        private List<String> authorizedRedirectUris = new ArrayList<>();

        public List<String> getAuthorizedRedirectUris() {
            return authorizedRedirectUris;
        }

        public OAuth2 authorizedRedirectUris(List<String> authorizedRedirectUris) {
            this.authorizedRedirectUris = authorizedRedirectUris;
            return this;
        }
    }

    @Getter
    @Setter
    public static final class MyServiceAgent {
        private String smsOtpUrl;
        private String voiceOtpUrl;
        private String verifyOtpUrl;
        private String sendSmsUrl;
        private String apiKey;
    }

    @Getter
    @Setter
    public static final class OtpConfig {
        private long length;
        private long expiresIn;
    }

    @Getter
    @Setter
    public static final class AmazonConfig {
        private String accessKey;
        private String secretKey;
        private String imageBucket;
        private String accessImageUrl;
    }


}
