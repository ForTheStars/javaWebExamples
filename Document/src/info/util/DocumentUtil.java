package info.util;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.omg.CORBA.PUBLIC_MEMBER;

import info.dao.IAttachmentDao;
import info.dto.AttachDto;
import info.model.Attachment;
import info.model.Document;
import info.model.Message;

public class DocumentUtil {
	public static String[] addAttach(AttachDto aDto,IAttachmentDao attachmentDao,Message message,Document document) throws IOException{
		//添加私人信件
		if(aDto.isHasAttach()) {
			File[] atts = aDto.getAtts();
			String[] attsContentType = aDto.getAttsContentType();
			String[] attsFilename = aDto.getAttaFilename();
			String[] newNames = new String[atts.length];
			for(int i = 0;i < atts.length;i++) {
				File file = atts[i];
				String oldName = attsFilename[i];
				String contentType = attsContentType[i];
				String newName = getNewName(oldName);
				Attachment attachment = new Attachment();
				if(message != null)
					attachment.setMessage(message);
				if(document != null)
					attachment.setDocument(document);
				attachment.setOldName(oldName);
				attachment.setNewName(newName);
				attachment.setContentType(contentType);
				attachment.setCreateDate(new Date());
				attachment.setSize(file.length());
				attachment.setType(FilenameUtils.getExtension(oldName));
				newNames[i] = newName;
				attachmentDao.add(attachment);
			}
			//上传附件
			String uploadPath = aDto.getUploadPath();
			for(int i = 0; i < atts.length; i++) {
				File file = atts[i];
				String name = newNames[i];
				String path = uploadPath+"/"+name;
				FileUtils.copyFile(file, new File(path));
			}
			return newNames;
		}
		return null;
	}
	public static String getNewName(String name) {
		String newName = new Long(new Date().getTime()).toString();
		newName = newName+"."+FilenameUtils.getExtension(name);
		return newName;
	}
}
