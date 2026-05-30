package br.projetos.gerenciadorFinanceiro.service.files.importers.xlsx;


import java.io.InputStream;
import java.util.List;

import org.springframework.stereotype.Component;

import br.projetos.gerenciadorFinanceiro.dto.LancamentoDTO;
import br.projetos.gerenciadorFinanceiro.service.files.FileType;
import br.projetos.gerenciadorFinanceiro.service.files.importers.FileImporter;

@Component
public class LancamentoXlsxImporter implements FileImporter {

	@Override
	public List importFile(InputStream inputStream) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public FileType supportsFileType() {
		return FileType.XLSX;
	}

	@Override
	public Class supportsDomain() {
		return LancamentoDTO.class;
	}
//
//    @Override
//    public List<PersonDTO> importFile(InputStream inputStream) throws Exception {
//
//        try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
//            XSSFSheet sheet = workbook.getSheetAt(0);
//            Iterator<Row> rowIterator = sheet.iterator();
//
//            if (rowIterator.hasNext()) rowIterator.next();
//
//            return parseRowsToPersonDtoList(rowIterator);
//
//        }
//    }
//
//    private List<PersonDTO> parseRowsToPersonDtoList(Iterator<Row> rowIterator) {
//        List<PersonDTO> people = new ArrayList<>();
//
//        while (rowIterator.hasNext()) {
//            Row row = rowIterator.next();
//            if (isRowValid(row)) {
//               people.add(parseRowToPersonDto(row));
//            }
//        }
//        return people;
//    }
//
//    private PersonDTO parseRowToPersonDto(Row row) {
//        PersonDTO person = new PersonDTO();
//        person.setFirstName(row.getCell(0).getStringCellValue());
//        person.setLastName(row.getCell(1).getStringCellValue());
//        person.setAddress(row.getCell(2).getStringCellValue());
//        person.setGender(row.getCell(3).getStringCellValue());
//        person.setEnabled(true);
//        return person;
//    }
//
//    private static boolean isRowValid(Row row) {
//        return row.getCell(0) != null && row.getCell(0).getCellType() != CellType.BLANK;
//    }

}
