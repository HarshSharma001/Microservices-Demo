package com.example.students;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.students.models.StudentResponseModel;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaRepositories
@EnableScheduling
public class StudentsMicroserviceApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(StudentsMicroserviceApplication.class, args);
	}

	/**
	 * method - to return an instance of BCryptPasswordEncoder
	 * @return a new BCryptPasswordEncoder instance
	 */
	@Bean
	public BCryptPasswordEncoder getbCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	
	/**
	 * method - to return an instance of JedisConnectionFactory
	 * @return a new JedisConnectionFactory instance
	 */
	@Bean
	public JedisConnectionFactory getJedisConnectionFactory() {
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
		configuration.setHostName("localhost");
		configuration.setPort(6379);
		return new JedisConnectionFactory(configuration);
	}

	/**
	 * method - to return an instance of RedisTemplate
	 * @return a new RedisTemplate instance
	 */
	@Bean
	public RedisTemplate<String, StudentResponseModel> getRedisTemplate() {
		RedisTemplate<String, StudentResponseModel> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(getJedisConnectionFactory());
		return redisTemplate;
	}
}
