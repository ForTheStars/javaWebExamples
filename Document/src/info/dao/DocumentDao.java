package info.dao;


import org.springframework.stereotype.Repository;
import info.model.Document;

@Repository("documentDao")
public class DocumentDao extends BaseDao<Document> implements IDocumentDao {

}
