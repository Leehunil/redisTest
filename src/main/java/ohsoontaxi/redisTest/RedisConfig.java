package ohsoontaxi.redisTest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.time.Duration;

@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        //RedisStandaloneConfiguration은 Redis 서버의 호스트 이름, 포트 번호, Redis 인스턴스에 대한 인증 정보 등을 설정할 수 있습니다.
        RedisStandaloneConfiguration redisConfig =
                new RedisStandaloneConfiguration(redisHost, redisPort);

        // Netty 라이브러리를 사용하여 Redis 서버와의 연결을 설정합니다. LettuceConnectionFactory는 비동기식으로 동작하며,
        // 멀티 스레드 환경에서 안정적으로 동작합니다.
        LettuceClientConfiguration clientConfig =
                LettuceClientConfiguration.builder()
                        .commandTimeout(Duration.ofSeconds(1)) //Redis서버로 부터 응답을 기다리는 최대 시간
                        .shutdownTimeout(Duration.ZERO) //Redis 클라이언트가 Redis 서버에 대한 연결을 종료하는데 소요되는 최대 시간
                        .build();
        return new LettuceConnectionFactory(redisConfig, clientConfig);
    }
}
