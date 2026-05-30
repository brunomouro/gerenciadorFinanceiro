package br.projetos.gerenciadorFinanceiro.service.files.exporters.xlsx;

import java.util.List;

import org.springframework.core.io.Resource;

import br.projetos.gerenciadorFinanceiro.dto.LancamentoDTO;
import br.projetos.gerenciadorFinanceiro.service.files.FileType;
import br.projetos.gerenciadorFinanceiro.service.files.exporters.FileExporter;

public class LancamentoXlsxExporter implements FileExporter<LancamentoDTO>{
	
//    @Override
//    public Resource exportFile(List<PersonDTO> people) throws Exception {
//        try (Workbook workbook = new XSSFWorkbook()){
//            Sheet sheet = workbook.createSheet("People");
//
//            Row headerRow = sheet.createRow(0);
//            String[] headers = {"ID", "First Name", "Last Name", "Address", "Gender", "Enabled"};
//            for (int i = 0; i < headers.length; i++) {
//                Cell cell = headerRow.createCell(i);
//                cell.setCellValue(headers[i]);
//                cell.setCellStyle(createHeaderCellStyle(workbook));
//            }
//
//            int rowIndex = 1;
//            for (PersonDTO person : people) {
//                Row row = sheet.createRow(rowIndex++);
//                row.createCell(0).setCellValue(person.getId());
//                row.createCell(1).setCellValue(person.getFirstName());
//                row.createCell(2).setCellValue(person.getLastName());
//                row.createCell(3).setCellValue(person.getAddress());
//                row.createCell(4).setCellValue(person.getGender());
//                row.createCell(5).setCellValue(
//                    person.getEnabled() != null && person.getEnabled() ? "Yes" : "No");
//            }
//
//            for (int i = 0; i < headers.length; i++) {
//                sheet.autoSizeColumn(i);
//            }
//
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            workbook.write(outputStream);
//
//            return new ByteArrayResource(outputStream.toByteArray());
//        }
//    }
//
//    private CellStyle createHeaderCellStyle(Workbook workbook) {
//        CellStyle style = workbook.createCellStyle();
//        Font font = workbook.createFont();
//        font.setBold(true);
//        style.setFont(font);
//        style.setAlignment(HorizontalAlignment.CENTER);
//        return style;
//    }

	@Override
	public FileType supportsFileType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class supportsDomain() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resource exportFile(List list) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
