package br.projetos.gerenciadorFinanceiro.service.files;

import org.springframework.http.MediaType;

public enum FileType {

    CSV(".csv", "text/csv"),
    XLSX(".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

    private final String extension;
    private final String mimeType;

    FileType(String extension, String mimeType) {
        this.extension = extension;
        this.mimeType = mimeType;
    }

    public String getExtension() {
        return extension;
    }

    public static FileType fromFileName(String fileName) {
        return java.util.Arrays.stream(values())
                			   .filter(type -> fileName != null && fileName.toLowerCase().endsWith(type.extension))
                			   .findFirst()
                			   .orElseThrow(() -> new IllegalArgumentException("Unsupported file type"));
    }

    public MediaType getMediaType() {
        return MediaType.parseMediaType(mimeType);
    }
}
