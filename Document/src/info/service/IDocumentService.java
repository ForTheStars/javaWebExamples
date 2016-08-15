package info.service;

import java.io.IOException;
import java.util.List;

import info.dto.AttachDto;
import info.model.Attachment;
import info.model.Department;
import info.model.Document;
import info.model.Pager;

public interface IDocumentService {
	public void add(Document doc,Integer[] depIds,AttachDto attachDto) throws IOException;
	public void delete(int id);
	public Document updateRead(int id,Integer isRead);
	public Pager<Document> findSendDocument(int userId);
	public Pager<Document> findReadDocument(String con,Integer depId);
	public Pager<Document> findNotReadDocument(String con,Integer depId);
	public List<Attachment> listAttachmentByDocument(int docId);
	public Document load(int id);
	public List<Department> listDocSendDep(int id);
}
