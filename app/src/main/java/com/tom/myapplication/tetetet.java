package com.tom.myapplication;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Tomleisen on 2021/3/12.
 * Email : xy162162a@163.com
 * Operation :
 */
public class tetetet {

    public static void main(String[] args) throws IOException {
        System.out.println("input：");
        Scanner sc = new Scanner("5.");
        String input = sc.next();
        if (!input.matches("^[0-9]+([.]{0,1}[0-9]+){0,1}$")){
            System.out.println("this is not a number");
        }else if (input.matches("^[0-9]+$")){
            System.out.print("this is a integer");
        }else {
            System.out.print("this is a double");
        }
    }


}
