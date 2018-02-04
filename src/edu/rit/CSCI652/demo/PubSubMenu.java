package edu.rit.CSCI652.demo;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Amol on 1/29/2018.
 */
public class PubSubMenu {

    public interface PubSubMenuInterface {

        void invokePublish();


        void invokeAdvertise();


        void invokeSubscribe();


        void invokeRead();


        void invokeUnsubscribe();

    }

    public interface topicInterface {

        void insertEvent (Topic topic, String title, String message);
    }

    public void showTopics(ArrayList<Topic> topicArrayList, topicInterface topicInterface){

        int idx=1;
        for(Topic topic:topicArrayList){
            System.out.println(idx + ". " + topic.getName());
            idx++;
        }

        System.out.print("Choose a topic:");
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();

        System.out.print("Enter your title for the message:");
        String title = in.next();
        System.out.print("Enter your message:");
        String msg = in.next();

        topicInterface.insertEvent(topicArrayList.get(choice-1),title, msg);
    }

    public void showMenu(PubSubMenuInterface pubSubMenuInterface) {

        System.out.println("***** PubSub Menu *****");
        Scanner in = new Scanner(System.in);

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
                    pubSubMenuInterface.invokePublish();
                    return;
                case 2:
                    System.out.println("**** Starting advertise ****\n");
                    pubSubMenuInterface.invokeAdvertise();
                    return;
                case 3:
                    System.out.println("**** Starting subscribe ****\n");
                    pubSubMenuInterface.invokeSubscribe();
                    return;
                case 4:
                    System.out.println("**** Starting read ****\n");
                    pubSubMenuInterface.invokeRead();
                    return;
                case 5:
                    System.out.println("**** Starting unsubscribe ****\n");
                    pubSubMenuInterface.invokeUnsubscribe();
                    return;
                case 0:
                    exit = true;
                    return;
                default:
                    System.out.println("Invalid choice \n");
            }
        } while (!exit);

        System.out.println("**** Exiting PubSub Menu ****\n");
    }


}