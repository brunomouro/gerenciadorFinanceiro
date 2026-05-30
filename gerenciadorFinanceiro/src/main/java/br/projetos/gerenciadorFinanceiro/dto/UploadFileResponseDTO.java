package br.projetos.gerenciadorFinanceiro.dto;

public record UploadFileResponseDTO(String fileName, String fileDownloadUri, String fileType, long size) {

}
