package nl.gerimedica.uploadcsvfile.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import nl.gerimedica.uploadcsvfile.helper.CSVFileHelper;
import nl.gerimedica.uploadcsvfile.model.FileData;
import nl.gerimedica.uploadcsvfile.repository.FileDataRepository;

@Service
public class CSVFileServiceImpl implements CSVFileService {

	@Autowired
	FileDataRepository repository;

	@Override
	public void save(MultipartFile file) {
		try {
			List<FileData> fileDatas = CSVFileHelper.csvToFileData(file.getInputStream());
			repository.saveAll(fileDatas);
		} catch (IOException e) {
			throw new RuntimeException("fail to store csv data: " + e.getMessage());
		}
	}

	@Override
	public ByteArrayInputStream load() {

		List<FileData> fileDatas = repository.findAll();

		ByteArrayInputStream in = CSVFileHelper.fileDataToCSV(fileDatas);
		return in;
	}

	@Override
	public List<FileData> getAllFileDatas() {
		return repository.findAll();
	}

	@Override
	public FileData getByCode(String code) {
		return repository.findByCode(code);
	}

	@Override
	public void deleteAll() {
		repository.deleteAll();
	}

}
