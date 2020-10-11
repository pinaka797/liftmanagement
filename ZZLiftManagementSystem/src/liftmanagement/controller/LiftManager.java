/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liftmanagement.controller;

import java.util.LinkedList;
import java.util.List;
import liftmanagement.driver.ReadInput;
import liftmanagement.entity.Lift;
import liftmanagement.entity.LiftDirection;
import liftmanagement.entity.LiftState;
import liftmanagement.entity.Request;


public class LiftManager {

    boolean flag = true;
    //list of all lift 
    List<Lift> liftList;
    int noOfFloor;
    static int timeCont = 0;
    Scheduler scheduler;
    //request to be served
    List<Request> unAssignedRequest;
    //input class 
    ReadInput reader;

    //initialize system
    public LiftManager(int noOfLift, int noOfFloor) {
        this.noOfFloor = noOfFloor;
        liftList = new LinkedList<>();
        unAssignedRequest = new LinkedList<>();
        for (int i = 0; i < noOfLift; i++) {
            Lift l = new Lift(i + 1, LiftState.CLOSE, 0, LiftDirection.UP);
            liftList.add(l);
        }
        scheduler = new RoundRobinScheduler();
        reader = new ReadInput();
    }

    //take request from user
    public void takeNewRequest() {
        int source, destination;
        System.out.println("Enter Soucrce Destination eg: 3 4");
        reader.readStringArray();
        source = Integer.parseInt(reader.getNextString());
        destination = Integer.parseInt(reader.getNextString());
        Request r = new Request(source, destination, timeCont);
        // schedule request
        scheduleRequest(r);
    }
    
    //add request to request list
    public void addRequst(Request r){
        unAssignedRequest.add(r);
    }

    //assign request to lift
    public void scheduleRequest(Request r) {
        scheduler.scheduleRequest(r, liftList);
    }

    public void printSnapShot() {
        System.out.println("Time " + timeCont);
        String str = "";
        for (Lift l : liftList) {
            str = str + "Lift " + l.getLiftId() + "-> " + l.getCurrentFloor() + " (" + l.getLiftState() + "), ";
        }
        str = str.substring(0, str.length() - 2);
        System.out.println(str);
    }

    //stae progress 
    public void progress() {
        
        if (flag == true) {
            printSnapShot();
        } else {
            for (Lift l : liftList) {
                System.out.println("Lift " + l.getLiftId()  + " " + l.getUnitTime());
                
            }
            System.exit(0);
        }
        
        // check if new request come
        char option = 'N';
        while (option == 'Y') {
            System.out.println("Do we have new request Press Y for Yes and N for No");
            option = reader.readChar();
            switch (option) {
                case 'Y':
                    // take new request
                    takeNewRequest();
                    break;
                case 'N':
                    break;
                default:
                    System.err.println("Invalid input");
                    break;
            }
        }

        flag = false;
        for (Lift l : liftList) {
            if (l.getRequest().size() > 0 || l.getLiftState() == LiftState.OPEN) {
                l.move();
                flag = true;
            }
        }
        timeCont++;

        

    }

}
