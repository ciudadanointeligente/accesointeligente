package org.accesointeligente.shared;

public enum FileType {
	XLS("Excel 2003", ".xls"),
	XLSX("Excel 2007", ".xlsx"),
	CSV("Valores separados por comas", ".csv"),
	ODS("OpenDocument Hoja de calculo", ".ods"),
	DOC("Word 2003", ".doc"),
	DOCX("Word 2007", ".docx"),
	ODT("OpenDocument Documento de texto", ".odt"),
	PPT("PowerPoint 2003", ".ppt"),
	PPTX("PowerPoint 2007", ".pptx"),
	ODP("OpenDocument Presentacion", ".odp"),
	PDF("Portable Document Format", ".pdf"),
	TXT("Texto plano", ".txt"),
	HTML("Documento HTML", ".html"),
	XML("Documento XML", ".xml");

	private String name;
	private String extension;

	private FileType(String name, String extension) {
		this.name = name;
		this.extension = extension;
	}

	public String getName() {
		return name;
	}

	public String getExtension() {
		return extension;
	}
}
