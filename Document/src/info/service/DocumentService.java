package info.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import info.dao.IAttachmentDao;
import info.dao.IDepartmentDao;
import info.dao.IDocumentDao;
import info.dao.IUserDao;
import info.dto.AttachDto;
import info.model.Attachment;
import info.model.DepDocument;
import info.model.Department;
import info.model.Document;
import info.model.Pager;
import info.model.SystemContext;
import info.model.User;
import info.model.UserReadDocument;
import info.util.DocumentUtil;

@Service("documentService")
public class DocumentService implements IDocumentService {
	private IDocumentDao documentDao;
	private IUserDao userDao;
	private IAttachmentDao attachmentDao;
	private IDepartmentDao departmentDao;
	
	
	public IDocumentDao getDocumentDao() {
		return documentDao;
	}
	@Resource
	public void setDocumentDao(IDocumentDao documentDao) {
		this.documentDao = documentDao;
	}
	
	public IUserDao getUserDao() {
		return userDao;
	}
	@Resource
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
	
	public IAttachmentDao getAttachmentDao() {
		return attachmentDao;
	}
	@Resource
	public void setAttachmentDao(IAttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
	}

	public IDepartmentDao getDepartmentDao() {
		return departmentDao;
	}
	@Resource
	public void setDepartmentDao(IDepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}
	
	@Override
	public void add(Document doc, Integer[] depIds, AttachDto attachDto) throws IOException {
		doc.setCreateDate(new Date());
		doc.setUser(SystemContext.getLoginUser());
		documentDao.add(doc);
		List<Department> departments = departmentDao.listByIds(depIds);
		DepDocument dDocument = null;
		for(Department dep:departments){
			dDocument = new DepDocument();
			dDocument.setDepartment(dep);
			dDocument.setDocument(doc);
			documentDao.addObj(dDocument);
		}
		DocumentUtil.addAttach(attachDto, attachmentDao, null, doc);
	}
	@Override
	public void delete(int id) {
		//1、删除和用户的对应关系
		String hql = "delete UserReadDocument urd where urd.document.id=?";
		documentDao.executeByHql(hql, id);
		//2、删除和部门的对应关系
		hql = "delete DepDocument dd where dd.document.id = ?";
		documentDao.executeByHql(hql,id);
		//3、删除附件
		List<Attachment> atts = this.listAttachmentByDocument(id);
		hql = "delete Attachment att where att.document.id = ?";
		documentDao.executeByHql(hql, id);
		//4、删除公文
		documentDao.delete(id);
		for(Attachment attachment : atts) {
			new File(SystemContext.getRealPath()+"/upload/"+attachment.getNewName()).delete();
		}
	}
	
	@Override
	public Pager<Document> findSendDocument(int userId){
		String hql = "select new Document(id,title,content,createDate) from Document doc " +
				"where doc.user.id=? order by createDate desc";
		return documentDao.find(hql,userId);
	}
	
	@Override
	public Document updateRead(int id, Integer isRead) {
		User user = SystemContext.getLoginUser();
		Document document = documentDao.load(id);
		if(isRead == null || isRead == 0) {
			if(!checkDocIsRead(user.getId(), id)){
				UserReadDocument urd = new UserReadDocument();
				urd.setDocument(document);
				urd.setUser(user);
				documentDao.addObj(urd);
			}
		}
		return document;
	}
	@Override
	public Pager<Document> findReadDocument(String con, Integer depId) {
		User u = SystemContext.getLoginUser();
		String hql = "select doc from Document doc left join fetch doc.user u left join fetch u.department dep " +
				"where doc.id in (select urd.document.id from UserReadDocument urd where urd.user.id=?)";
		if(con!=null&&!"".equals(con)) {
			hql+=" and (doc.title like '%"+con+"%' or doc.content like '%"+con+"%')";
		}
		if(depId!=null&&depId>0) {
			hql+=" and dep.id="+depId;
		}
		hql+=" order by doc.createDate desc";
		return documentDao.find(hql, u.getId());
	}
	@Override
	public Pager<Document> findNotReadDocument(String con, Integer depId) {
		User u = SystemContext.getLoginUser();
		String hql = "select doc from Document doc left join fetch doc.user u left join fetch u.department dep where " +
				"doc.id not in (select urd.document.id from UserReadDocument urd where urd.user.id=?)";
		if(con!=null&&!"".equals(con)) {
			hql+=" and (doc.title like '%"+con+"%' or doc.content like '%"+con+"%')";
		}
		if(depId!=null&&depId>0) {
			hql+=" and dep.id="+depId;
		}
		return documentDao.find(hql, u.getId());
	}
	@Override
	public List<Attachment> listAttachmentByDocument(int docId) {
		return attachmentDao.list("from Attachment a where a.document.id = ?", docId);
	}
	@Override
	public Document load(int id) {
		return documentDao.load(id);
	}
	@Override
	public List<Department> listDocSendDep(int id) {
		String hql = "select dd.department from DepDocument dd where dd.document.id=?";
		return departmentDao.list(hql, id);
	}
	private boolean checkDocIsRead(int userId,int docId) {
		String hql = "select count(*) from UserReadDocument urd where urd.user.id=? and urd.document.id=?";
		Long count =(Long)documentDao.queryByHql(hql,new Object[]{userId,docId});
		if(count == null || count == 0)
			return false;
		return true;
	}
}
