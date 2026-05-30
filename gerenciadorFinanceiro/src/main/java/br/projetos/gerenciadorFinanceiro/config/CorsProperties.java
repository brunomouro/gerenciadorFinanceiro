package br.projetos.gerenciadorFinanceiro.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "cors")
public class CorsProperties {

    private List<String> originPatterns = new ArrayList<>();

    public List<String> getOriginPatterns() {
        return originPatterns;
    }

    public void setOriginPatterns(List<String> originPatterns) {
        this.originPatterns = originPatterns;
    }
}