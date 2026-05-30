package br.projetos.gerenciadorFinanceiro.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{

    private final CorsProperties corsProperties;

    public WebConfig(CorsProperties corsProperties) {
        this.corsProperties = corsProperties;
    }
    
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			    .allowedOriginPatterns(corsProperties.getOriginPatterns().toArray(String[]::new))
			    .allowedMethods("*");
	}
			
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.favorParameter( false )
				  .ignoreAcceptHeader( false )
				  .useRegisteredExtensionsOnly( false )
				  .defaultContentType(MediaType.APPLICATION_JSON)
				  		.mediaType( "json", MediaType.APPLICATION_JSON)
				  		.mediaType( "xml", MediaType.APPLICATION_XML);
	}
}
