package info.test;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.URLDataSource;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class TestMail {
	@Resource(name="mailSender")
	JavaMailSender mailSender;
	
	@Test
	public void testSendEmail() {
		try {
			//单个用户可在beans.xml下配置
			JavaMailSenderImpl jmSenderImpl = (JavaMailSenderImpl)mailSender;
			jmSenderImpl.setHost("smtp.sina.com");
			jmSenderImpl.setProtocol("smtp");
			jmSenderImpl.setUsername("oh_java");
			jmSenderImpl.setPassword("java86");
			//创建mimeMessage
			MimeMessage msg = mailSender.createMimeMessage();
			//通过MimeMessageHelper来完成对邮件信息的创建
			MimeMessageHelper helper = new MimeMessageHelper(msg,true, "utf-8");
			helper.setFrom("oh_java@sina.com");
			helper.setTo("oh_java@163.com");
			helper.setSubject("Spring邮件发送测试");
			//设置邮件的正文
			helper.setText("<div style='color:red;font-size:15px;'>通过Spring来发送邮件</div>" +
					"<img src='cid:x1'/>kajsdkjsadf<img src='cid:x2'/>ksjdhfksjdhf<img src='cid:x3'/>", true);
			//添加附件
			helper.addAttachment(MimeUtility.encodeText("参考图片"), new FileSystemResource("D:/Desktop/复杂的mail形式.png"));
			//添加邮件内容中信息
			FileSystemResource fsr = new FileSystemResource("D:/Desktop/复杂的mail形式.png");
			helper.addInline("x1", fsr);
			helper.addInline("x2", new URLDataSource(new URL("https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3034132144,4091860566&fm=96")));
			helper.addInline("x3", new URLDataSource(new URL("https://ss1.baidu.com/70cFfyinKgQFm2e88IuM_a/forum/pic/item/a9d3fd1f4134970a9b762c7c94cad1c8a7865d5f.jpg")));
			mailSender.send(msg);
		} catch (MailException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testReg() {
		String html = "asdads..<img src='xxxx.jpg'/>adfas..." +
				"<img src='http://ssss.ssjpg'/>asdkfjaksjdhfkajsfd<img src=\"sdkjfskjhfd.hpg\"/>";
		Pattern p = Pattern.compile("<img.*?\\s+src=['\"](.*?)['\"].*?>");
		Matcher m = p.matcher(html);
		while(m.find()) {
			System.out.println(m.group(1));
		}
	}
	
}
