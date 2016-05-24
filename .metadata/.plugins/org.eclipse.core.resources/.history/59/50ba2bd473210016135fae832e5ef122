package info.model;

public class SystemContext {
	private static ThreadLocal<Integer> pageSize = new ThreadLocal<>();
	private static ThreadLocal<Integer> pageIndex = new ThreadLocal<>();
	
	public static ThreadLocal<Integer> getPageSize() {
		return pageSize;
	}
	public static void setPageSize(ThreadLocal<Integer> pageSize) {
		SystemContext.pageSize = pageSize;
	}
	public static ThreadLocal<Integer> getPageIndex() {
		return pageIndex;
	}
	public static void setPageIndex(ThreadLocal<Integer> pageIndex) {
		SystemContext.pageIndex = pageIndex;
	}
	public static void removePageSize(){
		pageSize.remove();
	}
	public static void removePageIndex(){
		pageIndex.remove();
	}
}
