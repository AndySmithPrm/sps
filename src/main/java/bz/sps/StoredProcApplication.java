package bz.sps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StoredProcApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoredProcApplication.class, args);
	}

	// регистрация фильтра, который логирует тело ответов
	@Bean
	public FilterRegistrationBean<StoredProcLoggingFilter> contextFilterRegistrationBean() {
		FilterRegistrationBean<StoredProcLoggingFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new StoredProcLoggingFilter());
		registrationBean.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE);
		return registrationBean;
	}
}
