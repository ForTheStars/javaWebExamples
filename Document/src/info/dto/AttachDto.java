package info.dto;

import java.io.File;

public class AttachDto {
	private File[] atts;
	private String[] attsContentType;
	private String[] attaFilename;
	private boolean hasAttach;
	private String uploadPath;
	
	public AttachDto() {
	}
	public AttachDto(boolean hasAttach) {
		this.hasAttach = hasAttach;
	}
	public AttachDto(File[] atts, String[] attsContentType, String[] attaFilename, String uploadPath) {
		this.atts = atts;
		this.attsContentType = attsContentType;
		this.attaFilename = attaFilename;
		this.uploadPath = uploadPath;
		this.hasAttach = true;
	}
	
	public File[] getAtts() {
		return atts;
	}
	public void setAtts(File[] atts) {
		this.atts = atts;
	}
	public String[] getAttsContentType() {
		return attsContentType;
	}
	public void setAttsContentType(String[] attsContentType) {
		this.attsContentType = attsContentType;
	}
	public String[] getAttaFilename() {
		return attaFilename;
	}
	public void setAttaFilename(String[] attaFilename) {
		this.attaFilename = attaFilename;
	}
	public boolean isHasAttach() {
		return hasAttach;
	}
	public void setHasAttach(boolean hasAttach) {
		this.hasAttach = hasAttach;
	}
	public String getUploadPath() {
		return uploadPath;
	}
	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}
	
}
