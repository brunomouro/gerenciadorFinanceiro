package br.projetos.gerenciadorFinanceiro.service.files.importers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;

import br.projetos.gerenciadorFinanceiro.service.files.FileType;

@Component
public class FileImporterFactory {

    private final Map<String, FileImporter<?>> registry = new HashMap<>();

    public FileImporterFactory(List<FileImporter<?>> importers) {
        for (FileImporter<?> importer : importers) {
            String key = buildKey(importer.supportsDomain(), importer.supportsFileType());
            registry.put(key, importer);
        }
    }

    public <T> FileImporter<T> getImporter(Class<T> domain, FileType fileType) throws BadRequestException {
        String key = buildKey(domain, fileType);

        FileImporter<?> importer = registry.get(key);

        if (importer == null) {
            throw new BadRequestException("Unsupported import type");
        }

        return (FileImporter<T>) importer;
    }

    private String buildKey(Class<?> domain, FileType type) {
        return domain.getSimpleName() + "_" + type;
    }
}