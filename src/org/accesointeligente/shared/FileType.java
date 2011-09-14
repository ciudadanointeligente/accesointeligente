/**
 * Acceso Inteligente
 *
 * Copyright (C) 2010-2011 Fundación Ciudadano Inteligente
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
	RTF("Texto con formato", ".rtf"),
	HTML("Documento HTML", ".html"),
	HTM("Documento HTML", ".htm"),
	XML("Documento XML", ".xml"),
	ZIP("Archivo Comprimido", ".zip"),
	RAR("Archivo Comprimido", ".rar"),
	BIN("Documento", ".bin"),
	JPG("Imagen", ".jpg"),
	JPEG("Imagen", ".jpeg"),
	PNG("Imagen", ".png"),
	TIFF("Imagen", ".tiff"),
	TIF("Imagen", ".tif"),
	GIF("Imagen", ".gif"),
	BMP("Imagen", ".bmp"),
	TGA("Imagen", ".tga");

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
