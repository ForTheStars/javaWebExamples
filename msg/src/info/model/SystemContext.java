package info.model;


public class SystemContext {
	private static ThreadLocal<Integer> pageSize = new ThreadLocal<>();
	private static ThreadLocal<Integer> pageIndex = new ThreadLocal<Integer>();
	
	public static void setPageSize(int _pageSize){
		pageSize.set(_pageSize);
	}
	public static int getPageSize(){
		return pageSize.get();
	}
	public static void removePageSize(){
		pageSize.remove();
	}
	
	
	public static void setPageIndex(int _pageIndex) {
		pageIndex.set(_pageIndex);
	}
	
	public static int getPageIndex() {
		return pageIndex.get();
	}
	
	public static void removePageIndex() {
		pageIndex.remove();
	}
}
