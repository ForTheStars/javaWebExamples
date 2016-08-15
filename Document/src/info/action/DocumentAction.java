package info.action;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import info.dto.AttachDto;
import info.model.Department;
import info.model.Document;
import info.model.SystemContext;
import info.model.User;
import info.service.IDepartmentService;
import info.service.IDocumentService;
import info.util.ActionUtil;

@Controller("documentAction")
@Scope("prototype")
public class DocumentAction extends ActionSupport implements ModelDriven<Document> {
	private Document doc;
	private Integer isRead;
	private Integer depId;
	private String con;
	private IDocumentService documentService;
	private IDepartmentService departmentService;
	private File[] atts;
	private String[] attsFileName;
	private String[] attsContentType;
	private Integer[] ds;
	
	public Document getDoc() {
		return doc;
	}
	public void setDoc(Document doc) {
		this.doc = doc;
	}
	public Integer getIsRead() {
		return isRead;
	}
	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}
	public Integer getDepId() {
		return depId;
	}
	public void setDepId(Integer depId) {
		this.depId = depId;
	}
	public String getCon() {
		return con;
	}
	public void setCon(String con) {
		this.con = con;
	}
	public IDocumentService getDocumentService() {
		return documentService;
	}
	@Resource
	public void setDocumentService(IDocumentService documentService) {
		this.documentService = documentService;
	}
	public IDepartmentService getDepartmentService() {
		return departmentService;
	}
	@Resource
	public void setDepartmentService(IDepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	public File[] getAtts() {
		return atts;
	}
	public void setAtts(File[] atts) {
		this.atts = atts;
	}
	public String[] getAttsFileName() {
		return attsFileName;
	}
	public void setAttsFileName(String[] attsFileName) {
		this.attsFileName = attsFileName;
	}
	public String[] getAttsContentType() {
		return attsContentType;
	}
	public void setAttsContentType(String[] attsContentType) {
		this.attsContentType = attsContentType;
	}
	public Integer[] getDs() {
		return ds;
	}
	public void setDs(Integer[] ds) {
		this.ds = ds;
	}
	
	public String listReceive() {
		ActionContext.getContext().put("deps", departmentService.listAllDep());
		if(isRead == null || isRead == 0) {
			ActionContext.getContext().put("pages",documentService.findNotReadDocument(con, depId));
		} else {
			ActionContext.getContext().put("pages",documentService.findReadDocument(con, depId));
		}
		return SUCCESS;
	}
	
	public String show() throws IllegalAccessException, InvocationTargetException {
		Document document = documentService.load(doc.getId());
		BeanUtils.copyProperties(doc, document);
		ActionContext.getContext().put("atts", documentService.listAttachmentByDocument(doc.getId()));
		ActionContext.getContext().put("deps", documentService.listDocSendDep(doc.getId()));
		return SUCCESS;
	}
	
	public String updateRead() throws IllegalAccessException, InvocationTargetException {
		Document document = documentService.updateRead(doc.getId(), isRead);
		BeanUtils.copyProperties(doc, document);
		ActionContext.getContext().put("atts", documentService.listAttachmentByDocument(doc.getId()));
		ActionContext.getContext().put("deps", documentService.listDocSendDep(doc.getId()));
		return SUCCESS;
	}
	
	public String delete() {
		documentService.delete(doc.getId());
		ActionUtil.setUrl("document_listSend");
		return ActionUtil.REDIRECT;
	}
	
	public String listSend() {
		ActionContext.getContext().put("deps", departmentService.listAllDep());
		User user = (User)ActionContext.getContext().getSession().get("loginUser");
		ActionContext.getContext().put("pages", documentService.findSendDocument(user.getId()));
		return SUCCESS;
	}
	
	public String addInput() {
		User user = (User)ActionContext.getContext().getSession().get("loginUser");
		List<Department> departments = departmentService.listUserDep(user.getId());
		ActionContext.getContext().put("deps", departments);
		return SUCCESS;
	}
	
	public String add() throws IOException {
		if(atts == null || atts.length <= 0) {
			documentService.add(doc, ds,new AttachDto(false));
		} else {
			String uploadPath = SystemContext.getRealPath()+"\\upload";
			documentService.add(doc,ds,new AttachDto(atts,attsContentType,attsFileName,uploadPath));
		}
		ActionUtil.setUrl("document_listSend.action");
		return ActionUtil.REDIRECT;
	}
	
	public void validateAdd() {
		if(ds==null||ds.length<=0) {
			this.addFieldError("ds", "必须选择要发送的部门");
		}
		if(doc.getTitle()==null||"".equals(doc.getTitle())) {
			this.addFieldError("title","部门公文的标题不能为空");
		}
		if(this.hasFieldErrors()) {
			addInput();
		}
	}
	
	@Override
	public Document getModel() {
		if(doc == null)
			doc = new Document();
		return doc;
	}

}
