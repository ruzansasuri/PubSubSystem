package edu.rit.CSCI652.demo;

import java.util.Scanner;
/**
 * Created by Amol on 1/29/2018.
 */
public class PubSubMenu {

    public static void main(String args[]){
        PubSubMenu psm = new PubSubMenu();
        psm.showMenu();
    }

    private void showMenu(){
        System.out.println("***** PubSub Menu *****");
        Scanner in = new Scanner(System.in);
        // show menu
        System.out.println("1. Publish");
        System.out.println("2. Advertise");
        System.out.println("3. Subscribe");
        System.out.println("4. Read");
        System.out.println("5. Unsubscribe");
        System.out.println("0. Exit\n");
        boolean exit = false;
        int option = 0;
        do {
            System.out.print("Choose menu option : ");
            option = in.nextInt();
            switch (option) {
                case 1:
                    System.out.println("**** Starting publish ****\n");
                    invokePublish();
                    break;
                case 2:
                    System.out.println("**** Starting advertise ****\n");
                    invokeAdvertise();
                    break;
                case 3:
                    System.out.println("**** Starting subscribe ****\n");
                    invokeSubscribe();
                    break;
                case 4:
                    System.out.println("**** Starting read ****\n");
                    invokeRead();
                    break;
                case 5:
                    System.out.println("**** Starting unsubscribe ****\n");
                    invokeUnsubscribe();
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice \n");
            }
        } while (!exit);
        System.out.println("**** Exiting PubSub Menu ****\n");
    }

    private void invokePublish(){

    }

    private void invokeAdvertise(){

    }

    private void invokeSubscribe(){

    }

    private void invokeRead(){

    }

    private void invokeUnsubscribe(){

    }
}