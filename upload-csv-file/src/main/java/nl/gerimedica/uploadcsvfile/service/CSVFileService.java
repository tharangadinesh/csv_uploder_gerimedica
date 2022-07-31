package nl.gerimedica.uploadcsvfile.service;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import nl.gerimedica.uploadcsvfile.model.FileData;

public interface CSVFileService {

	void save(MultipartFile file);

	ByteArrayInputStream load();

	List<FileData> getAllFileDatas();

	FileData getByCode(String code);

	void deleteAll();

}
