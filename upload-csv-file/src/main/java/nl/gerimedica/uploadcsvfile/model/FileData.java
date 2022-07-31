package nl.gerimedica.uploadcsvfile.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FileData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String source;

	private String codeListCode;

	@Column(unique = true)
	private String code;

	private String displayValue;

	private String longDescription;

	private String fromDate;

	private String toDate;

	private String sortingPriority;

	public FileData() {

	}

	public FileData(String source, String codeListCode, String code, String displayValue,
			String longDescription, String fromDate, String toDate, String sortingPriority) {
		super();
		this.source = source;
		this.codeListCode = codeListCode;
		this.code = code;
		this.displayValue = displayValue;
		this.longDescription = longDescription;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.sortingPriority = sortingPriority;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getCodeListCode() {
		return codeListCode;
	}

	public void setCodeListCode(String codeListCode) {
		this.codeListCode = codeListCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDisplayValue() {
		return displayValue;
	}

	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}

	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getSortingPriority() {
		return sortingPriority;
	}

	public void setSortingPriority(String sortingPriority) {
		this.sortingPriority = sortingPriority;
	}

	@Override
	public String toString() {
		return "FileData [ source=" + source + ", codeListCode=" + codeListCode + ", code=" + code
				+ ", displayValue=" + displayValue + ", longDescription=" + longDescription + ", fromDate=" + fromDate
				+ ", toDate=" + toDate + ", sortingPriority=" + sortingPriority + "]";
	}


}
