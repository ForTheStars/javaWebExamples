package info.test;

import java.util.Random;

import info.dao.DAOFactory;
import info.dao.IUserDao;
import info.model.User;

public class InitUser {
	
	static String[] firstName = new String[] { "张", "刘", "牛", "李", "吕", "付",
			"副", "王", "汪", "赵", "孔", "谭", "贪", "夏侯", "令狐", "上官", "欧阳", "司马",
			"诸葛", "曹", "关", "孙", "甘" };
	static String[] centerName = { "立", "祝", "共", "都", "高", "陆", "恶", "人", "达",
			"玉", "莫", "靓", "宇" };
	static String[] lastName = { "强", "颖", "备", "亮", "云", "正", "冲", "兄弟", "大",
			"小", "关", "撒旦", "立", "玉", "鱼", "牛", "泵", "秒", "米", "个", "鐜", "惇",
			"彦", "另", "琳", "浩", "皓", "永锋", "明正", "丽音", "志峰", "春" };
	static Random ran = new Random();
	private IUserDao userDao = DAOFactory.getUserDao();
	
	public static void main(String[] args) {
		new InitUser();
	}
	
	public InitUser() {
		addUser();
	}
	
	public void addUser(){
		for(int i=1;i<=550;i++){
			User user = new User();
			user.setNickname(ranName());
			user.setPassword((8080+ran.nextInt(12346578)+""));
			user.setType(0);
			user.setUsername("user"+i);
			userDao.add(user);
			System.out.println(user.getNickname());
		}
	}
	
	private static String ranName() {
		int num = ran.nextInt(5);
		if(num<2) {
			//三个字
			return firstName[ran.nextInt(firstName.length)]
			                 +centerName[ran.nextInt(centerName.length)]
			                 +lastName[ran.nextInt(lastName.length)];
		} else {
			//三个字
			return firstName[ran.nextInt(firstName.length)]
			                 +lastName[ran.nextInt(lastName.length)];
		}
	}

}
