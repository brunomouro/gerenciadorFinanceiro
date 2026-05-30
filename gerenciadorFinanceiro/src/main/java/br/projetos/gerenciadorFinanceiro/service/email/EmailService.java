package br.projetos.gerenciadorFinanceiro.service.email;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.projetos.gerenciadorFinanceiro.config.EmailConfig;
import br.projetos.gerenciadorFinanceiro.dto.EmailRequestDTO;

@Service
public class EmailService {
	
	private EmailSender emailSender;
	private EmailConfig emailConfig;
	
	public EmailService(EmailSender emailSender, EmailConfig emailConfig) {
		this.emailSender = emailSender;
		this.emailConfig = emailConfig;
	}
	
    public void sendSimpleEmail(EmailRequestDTO emailRequest) {
        emailSender
            .to(emailRequest.to())
            .withSubject(emailRequest.subject())
            .withMessage(emailRequest.body())
            .send(emailConfig);
    }

    public void setEmailWithAttachment(String emailRequestJson, MultipartFile attachment) {
        File tempFile = null;
        try {
            EmailRequestDTO emailRequest = new ObjectMapper().readValue(emailRequestJson, EmailRequestDTO.class);
            tempFile = File.createTempFile("attachment", attachment.getOriginalFilename());
            attachment.transferTo(tempFile);

            emailSender
                .to(emailRequest.to())
                .withSubject(emailRequest.subject())
                .withMessage(emailRequest.body())
                .attach(tempFile.getAbsolutePath())
                .send(emailConfig);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing email request JSON!", e);
        } catch (IOException e) {
            throw new RuntimeException("Error processing the attachment!", e);
        } finally {
            if (tempFile != null && tempFile.exists()) tempFile.delete();
        }

    }

}
