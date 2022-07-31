package nl.gerimedica.uploadcsvfile.helper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.springframework.web.multipart.MultipartFile;

import nl.gerimedica.uploadcsvfile.model.FileData;

public class CSVFileHelper {

	public static String TYPE = "text/csv";

	static String[] HEADERs = { "source", "codeListCode", "code", "displayValue", "longDescription", "fromDate", "toDate", "sortingPriority" };

	public static boolean validateCSVFormat(MultipartFile file) {

		if (!TYPE.equals(file.getContentType())) {
			return false;
		}

		return true;
	}

	public static List<FileData> csvToFileData(InputStream is) {

		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

				CSVParser csvParser = new CSVParser(bufferedReader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

			List<FileData> fileDatas = new ArrayList<FileData>();

			Iterable<CSVRecord> csvRecords = csvParser.getRecords();

			for (CSVRecord csvRecord : csvRecords) {
				FileData fileData = new FileData(
						csvRecord.get("source"),
						csvRecord.get("codeListCode"),
						csvRecord.get("code"),
						csvRecord.get("displayValue"),
						csvRecord.get("longDescription"),
						csvRecord.get("fromDate"),
						csvRecord.get("toDate"),
						csvRecord.get("sortingPriority")
						);

				fileDatas.add(fileData);
			}

			return fileDatas;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		}
	}

	public static ByteArrayInputStream fileDataToCSV(List<FileData> fileDatas) {

		final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

		try (ByteArrayOutputStream out = new ByteArrayOutputStream();

				CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {

			for (FileData fileData : fileDatas) {
				List<String> data = Arrays.asList(
						fileData.getSource(),
						fileData.getCodeListCode(),
						fileData.getCode(),
						fileData.getDisplayValue(),
						fileData.getLongDescription(),
						fileData.getFromDate(),
						fileData.getToDate(),
						fileData.getSortingPriority()
						);

				csvPrinter.printRecord(data);
			}

			csvPrinter.flush();
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
		}
	}

}
