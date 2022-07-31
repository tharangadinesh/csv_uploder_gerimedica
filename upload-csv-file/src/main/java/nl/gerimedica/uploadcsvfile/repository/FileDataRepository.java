package nl.gerimedica.uploadcsvfile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import nl.gerimedica.uploadcsvfile.model.FileData;

@Repository
public interface FileDataRepository extends JpaRepository < FileData, Long > {

	@Query("FROM FileData WHERE code =:code")
	FileData findByCode(@Param("code")String code);

}
