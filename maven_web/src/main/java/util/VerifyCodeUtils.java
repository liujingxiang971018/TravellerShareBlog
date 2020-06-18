package util;

import java.util.Random;

/*
* 验证码生成工具类，随机生成6位数字的验证码
* */
public class VerifyCodeUtils {
    public static String getVerifyCode(){
        String str="0123456789";
        Random random=new Random();
        StringBuilder sb=new StringBuilder();
        for(int i=1;i<=6;i++) {
            int index = random.nextInt(str.length());
            //获取字符
            char ch = str.charAt(index);
            sb.append(ch);
        }
        return sb.toString();
    }

    public static String getTemporaryPassword(){
        String str="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        //生成随机角标
        Random random=new Random();
        StringBuilder sb=new StringBuilder();
        for(int i=1;i<=8;i++) {
            int index = random.nextInt(str.length());
            //获取字符
            char ch = str.charAt(index);
            sb.append(ch);
        }
        return sb.toString();
    }
}
