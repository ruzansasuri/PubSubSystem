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

    public interface TopicsInterface{

        void selectedTopic(Topic topic);
    }

    public void showTopics(ArrayList<Topic> topicArrayList, TopicsInterface topicsInterface){


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
                    break;
                case 2:
                    System.out.println("**** Starting advertise ****\n");
                    pubSubMenuInterface.invokeAdvertise();
                    break;
                case 3:
                    System.out.println("**** Starting subscribe ****\n");
                    pubSubMenuInterface.invokeSubscribe();
                    break;
                case 4:
                    System.out.println("**** Starting read ****\n");
                    pubSubMenuInterface.invokeRead();
                    break;
                case 5:
                    System.out.println("**** Starting unsubscribe ****\n");
                    pubSubMenuInterface.invokeUnsubscribe();
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


}