package com.stock.util;

import ch.qos.logback.core.net.LoginAuthenticator;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by kerno on 2016/3/28.
 */
public class Sender {
    private static Properties prop = new Properties();
    private static void initProperties(Boolean needProxy){
        prop.setProperty("mail.host", "smtp.163.com");
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth", "true");
        if(needProxy){
            prop.setProperty("proxy","cn-proxy.jp.oracle.com:80");
        }
    }

    public static void send(Boolean needProxy,String receiver,String subject,String content) throws Exception {
        initProperties(needProxy);
         //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        Session session = getSession();
         //2、通过session得到transport对象
         Transport ts = session.getTransport();
         //3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
         ts.connect();
         //4、创建邮件
         Message message = createSimpleMail(session,receiver,subject,content);
         //5、发送邮件
         ts.sendMessage(message, message.getAllRecipients());
         ts.close();
    }
    /**
     * 客户端程序自己实现Authenticator子类用于用户认证
     */
    static class MyAuthenricator extends Authenticator{
        String user=null;
        String pass="";
        public MyAuthenricator(String user,String pass){
            this.user=user;
            this.pass=pass;
        }
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(user,pass);
        }

    }

    private static Session getSession(){
        //使用JavaMail发送邮件的5个步骤
        //1、创建session
        MyAuthenricator authenticator = new MyAuthenricator("kerno1018@163.com", "wyf518123");
        return Session.getDefaultInstance(prop,authenticator);

    }

    private static MimeMessage createSimpleMail(Session session, String receiver, String subject, String content)
           throws Exception {
           //创建邮件对象
           MimeMessage message = new MimeMessage(session);
           //指明邮件的发件人
           message.setFrom(new InternetAddress("kerno1018@163.com"));
           //指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
           message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
           //邮件的标题
           message.setSubject(subject);
           //邮件的文本内容
           message.setContent(content, "text/html;charset=UTF-8");
           //返回创建好的邮件对象
           return message;
       }
}
