package com.prisma.challengeppi;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import java.time.Duration;

@SpringBootApplication
@Controller
@EnableJpaRepositories("com.prisma.challengeppi.repository")
public class ChallengePPI
{

    public static ApplicationContext appContext;
    public static  Environment environment;

    public static void main(String[] args)
    {
        SpringApplication.run(ChallengePPI.class, args);
        String puertoServer = environment.getProperty("local.server.port");
        System.out.println("CORRIENDO CHALLENGE PPI EN http://localhost:" + puertoServer);
    }

    @GetMapping(value = "/")
    public static RedirectView swagger()
    {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/swagger-ui.html");

        return redirectView;
    }


//    @Bean
//    public RestTemplate restTemplate(RestTemplateBuilder builder) {
//        return builder
//                .setConnectTimeout(Duration.ofMillis(3000))
//                .setReadTimeout(Duration.ofMillis(3000))
//                .build();
//    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("springshop-public")
                .pathsToMatch("/api/**")
                .build();
    }

    // NECESARY:
    @Autowired
    public void setearEnvironment(Environment environment) {
        ChallengePPI.environment = environment;
    }

    public static ApplicationContext dameAppContext() {
        return appContext;
    }

}
