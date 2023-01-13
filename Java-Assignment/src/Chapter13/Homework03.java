package Chapter13;

import java.util.Arrays;

public class Homework03 {
    public static void main(String[] args) {
        print("Han Shun Ping");

    }


    public static void print(String name){

        if (name == null){
            System.out.println("不能为空");
            return;
        }




        String[] strings = name.split(" ");


        String res = String.format("%s,%s.%c",strings[strings.length-1],strings[0],strings[1].toUpperCase().charAt(0));
        System.out.println(res);






    }



}
