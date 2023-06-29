//package africa.breej.africa.breej.amazon;
//
//import africa.breej.africa.breej.config.AppProperties;
//import com.amazonaws.auth.AWSCredentials;
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3ClientBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class AmazonConfig {
//
//    private AppProperties appProperties;
//
//    public AmazonConfig(AppProperties appProperties) {
//        this.appProperties = appProperties;
//    }
//
//    @Bean
//    public AmazonS3 s3() {
//        AWSCredentials awsCredentials =
//                new BasicAWSCredentials(appProperties.getAmazonConfig().getAccessKey(), appProperties.getAmazonConfig().getSecretKey());
//        return AmazonS3ClientBuilder
//                .standard()
//                .withRegion("us-east-1")
//                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
//                .build();
//
//    }
//}
