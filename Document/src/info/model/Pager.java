package info.model;

import java.util.List;
/*
 * 分页类
 */
public class Pager<T> {
	private List<T> datas;	//分页数据
	private int pageOffset;	//分页的开始值
	private int pageSize;	//每页显示多少条
	private long totalRecord;	//总共多少记录
	
	public List<T> getDatas() {
		return datas;
	}
	public void setDatas(List<T> datas) {
		this.datas = datas;
	}
	public int getPageOffset() {
		return pageOffset;
	}
	public void setPageOffset(int pageOffset) {
		this.pageOffset = pageOffset;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public long getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(long totalRecord) {
		this.totalRecord = totalRecord;
	}
	
}
