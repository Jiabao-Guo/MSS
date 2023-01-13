package Chapter14;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

public class listExercise02 {
    public static void main(String[] args) {

        List list = new ArrayList();
        list.add(new Books("红楼梦","曹",900));
        list.add(new Books("西游","吴",200));
        list.add(new Books("水壶","王",700));
        list.add(new Books("三国","章",500));

        for (Object o :list) {
            System.out.println(o);
        }
        System.out.println("=================");


        sortprice(list);

        for (Object o :list) {
            System.out.println(o);

        }

    }

    public static void sortprice(List list){
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - 1 -i; j++) {

                //向下转型
                Books books = (Books) list.get(j);
                Books books1 = (Books) list.get(j+1);
                //集合交换用set
                if(books.getPrice() > books1.getPrice()){
                    list.set(j,books1);
                    list.set(j+1,books);
                }
            }
        }
    }

}

class Books{
    private String name;
    private String author;
    private double price;

    public Books(String name, String author, double price) {
        this.name = name;
        this.author = author;
        this.price = price;
    }

    @Override
    public String toString() {
        return
                "name=" + name + '\t' +
                "author='" + author + '\t' +
                "price=" + price
                ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}