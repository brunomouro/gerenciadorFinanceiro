package br.projetos.gerenciadorFinanceiro.service.files.exporters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;

import br.projetos.gerenciadorFinanceiro.service.files.FileType;

@Component
public class FileExporterFactory {

    private final Map<String, FileExporter<?>> registry = new HashMap<>();

    public FileExporterFactory(List<FileExporter<?>> exporters) {
        for (FileExporter<?> exporter : exporters) {
            String key = buildKey(exporter.supportsDomain(), exporter.supportsFileType());
            registry.put(key, exporter);
        }
    }
    
    public <T> FileExporter<T> getExporter(Class<T> domain, FileType fileType) throws BadRequestException {
        String key = buildKey(domain, fileType);

        FileExporter<?> exporter = registry.get(key);

        if (exporter == null) {
            throw new BadRequestException("Unsupported export type");
        }

        return (FileExporter<T>) exporter;
    }

    private String buildKey(Class<?> domain, FileType type) {
        return domain.getSimpleName() + "_" + type;
    }

}