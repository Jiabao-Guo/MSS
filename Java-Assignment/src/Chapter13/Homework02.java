package Chapter13;

import java.util.ArrayList;

public class Homework02 {


    public static void main(String[] args) {

        try {
            userRegister("发送","432234","fdfd@sd");
            System.out.println("注册成功");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }



    public static void userRegister(String name, String password, String gmail){

        if( !(name.length()>=2 && name.length() <= 4) ){
            throw new RuntimeException("用户名长度不合法");
        }

        if ( !(password.length() == 6 && (isDigital(password)))){
            throw new RuntimeException("密码有误");
        }

        int i = gmail.indexOf('@');
        int j = gmail.indexOf('.');
        if ( !((i > 0)&&(i < j))){
            throw new RuntimeException("邮箱错误");
        }


    }

    public static boolean isDigital(String password){
        char[] chars = password.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            if( chars[i] < '0' || chars[i] > '9'){
                return false;
            }
        }
        return true;
    }


}


