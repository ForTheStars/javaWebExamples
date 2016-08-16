package info.test;


import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import info.dto.AttachDto;
import info.model.Document;
import info.model.SystemContext;
import info.model.User;
import info.service.IDocumentService;
import info.service.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class TestDocument {
	
	@Resource(name="userService")
	private IUserService userService;
	
	
	@Resource(name="documentService")
	private IDocumentService documentService;
	
	@Before
	public void init() {
		User u = userService.load(2);
		SystemContext.setLoginUser(u);
	}
	
	@Test
	public void testAdd() {
		try {
			Integer[] depIds = new Integer[]{1,2,3,4,5};
			Document doc = new Document();
			doc.setContent("测试测试");
			doc.setTitle("测试下");
			documentService.add(doc, depIds, new AttachDto(false));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetSendDocument() {
		SystemContext.setPageSize(15);
		SystemContext.setPageOffset(0);
		List<Document> docs = documentService.findSendDocument(2).getDatas();
		for(Document d:docs) {
			System.out.println(d.getContent());
		}
	}
	
	
	@Test
	public void testGetReadDocument() {
		User u = userService.load(4);
		SystemContext.setLoginUser(u);
		SystemContext.setPageSize(15);
		SystemContext.setPageOffset(0);
		List<Document> docs = documentService.findReadDocument("测", 2).getDatas();
		for(Document d:docs) {
			System.out.println(d.getContent());
		}
	}
	
	@Test
	public void testGetNotReadDocument() {
		User u = userService.load(4);
		SystemContext.setLoginUser(u);
		SystemContext.setPageSize(15);
		SystemContext.setPageOffset(0);
		List<Document> docs = documentService.findNotReadDocument("测试", 22).getDatas();
		for(Document d:docs) {
			System.out.println(d.getContent());
		}
	}
	
	@Test
	public void testDelete() {
		documentService.delete(1);
	}

}
