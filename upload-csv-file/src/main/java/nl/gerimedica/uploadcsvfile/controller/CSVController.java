package nl.gerimedica.uploadcsvfile.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import nl.gerimedica.uploadcsvfile.helper.CSVFileHelper;
import nl.gerimedica.uploadcsvfile.message.ResponseMessage;
import nl.gerimedica.uploadcsvfile.model.FileData;
import nl.gerimedica.uploadcsvfile.service.CSVFileService;

@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("/api/csv")
public class CSVController {

	@Autowired
	CSVFileService csvFileService;

	@PostMapping("/upload")
	public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {

		String message = "";

		if (CSVFileHelper.validateCSVFormat(file)) {
			try {

				csvFileService.save(file);

				message = "Uploaded the file successfully: " + file.getOriginalFilename();
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
			} catch (Exception e) {
				message = "Could not upload the file: " + file.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
			}
		}

		message = "Please upload a csv file!";

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));

	}

	@GetMapping("/all")
	public ResponseEntity<List<FileData>> getAllFileDatas() {
		try {
			List<FileData> fileDatas = csvFileService.getAllFileDatas();

			if (fileDatas.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(fileDatas, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/code")
	public ResponseEntity<FileData> getByCode(@RequestParam("code")String code) {
		try {
			FileData fileData = csvFileService.getByCode(code);

			if (fileData != null) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(fileData, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/deleteAll")
	public ResponseEntity<ResponseMessage> deleteAll() {
		try {

			csvFileService.deleteAll();

			return new ResponseEntity<>(new ResponseMessage("Successfully Deleted!"), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/download")
	public ResponseEntity<Resource> getFile() {
		String filename = "fileDatas.csv";
		InputStreamResource file = new InputStreamResource(csvFileService.load());

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/csv"))
				.body(file);
	}

}
