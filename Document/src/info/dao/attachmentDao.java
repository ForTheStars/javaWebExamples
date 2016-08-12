package info.dao;


import org.springframework.stereotype.Repository;

import info.model.Attachment;

@Repository("attachmentDao")
public class attachmentDao extends BaseDao<Attachment> implements IAttachmentDao {

}
