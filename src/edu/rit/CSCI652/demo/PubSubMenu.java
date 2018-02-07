package edu.rit.CSCI652.demo;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Amol on 1/29/2018.
 */
public class PubSubMenu {

    PubSubMenuInterface pubSubMenuInterface;

    public interface PubSubMenuInterface {

        void invokePublish();


        void invokeAdvertise();


        void invokeSubscribe();


        void invokeRead();

        void invokeReadFromKeyword();


        void invokeUnsubscribe();

    }

    public interface topicInterface {

        void selectedTopic(Topic topic);
    }

    public void showTopics(ArrayList<Topic> topicArrayList, topicInterface topicInterface){

        int idx=1;
        for(Topic topic:topicArrayList){
            System.out.println(idx + ". " + topic.getName());
            idx++;
        }

        System.out.print("\nChoose a topic:");
        Scanner in = new Scanner(System.in);
        int choice;

        try {
            choice = in.nextInt();
        }catch (Exception e){
            showTopics(topicArrayList, topicInterface);
            return;
        }

        topicInterface.selectedTopic(topicArrayList.get(choice-1));
    }


    public void setPubSubMenuInterface(PubSubMenuInterface pubSubMenuInterface) {
        this.pubSubMenuInterface = pubSubMenuInterface;
    }

    public void showMenu() {

        System.out.println("\n***** PubSub Menu *****");
        Scanner in = new Scanner(System.in);

        System.out.println("1. Publish");
        System.out.println("2. Advertise");
        System.out.println("3. Subscribe");
        System.out.println("4. Read from topics");
        System.out.println("5. Read from keywords");
        System.out.println("6. Unsubscribe");
        System.out.println("0. Exit\n");


        boolean exit = false;
        int option = 0;


            System.out.print("Choose menu option : ");
            try {
                option = in.nextInt();
            }catch (Exception e){
                showMenu();
                return;
            }
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
                    System.out.println("**** Starting read from topics ****\n");
                    pubSubMenuInterface.invokeRead();
                    return;
                case 5:
                    System.out.println("**** Starting read from keywords ****\n");
                    pubSubMenuInterface.invokeReadFromKeyword();
                    return;
                case 6:
                    System.out.println("**** Starting unsubscribe ****\n");
                    pubSubMenuInterface.invokeUnsubscribe();
                    return;
                case 0:
                    exit = true;
                    return;
                default:
                    System.out.println("Invalid choice \n");
            }


        System.out.println("**** Exiting PubSub Menu ****\n");
    }


}