
package liftmanagement.driver;

import liftmanagement.controller.LiftManager;
import liftmanagement.entity.Request;

public class LiftManagementDemo {

    /**
     * @param args the command line arguments
     */
    static int noOfFloor = 10;
    static int noOfLift = 2;
    public static void main(String[] args) throws InterruptedException {
        LiftManager lm = new LiftManager(noOfLift, noOfFloor);
        //int initialRequest[][] = {{0,5}, {3, 7}, {2, 9}, {1,2}, {1,4}, {3,2}};
        int initialRequest[][] = {{0,7}, {3, 0}, {4, 6}};
        for(int i=0; i<initialRequest.length; i++)
        {
            Request r = new Request(initialRequest[i][0], initialRequest[i][1], 0);
            lm.scheduleRequest(r);
        }
        while(true)
        {
            lm.progress();
            Thread.sleep(1000);
        }
    }
    
}

/*
T=0
LIFT 1 -- > 0 (OPEN), LIFT 2 ---> 0 (CLOSE)
T=1
LIFT 1 -- > 0 (CLOSE), LIFT 2 ---> 1 (CLOSE)
T=2
4 6
LIFT 1 -- > 1(CLOSE), LIFT 2 ---> 2 (CLOSE)
T=3
LIFT 1 -- > 2 (CLOSE), LIFT 2 ---> 3 (OPEN)
T=4
LIFT 1 -- > 3(CLOSE) , LIFT 2 ---> 3 (CLOSE)
T=5
LIFT 1 -- > 4(OPEN), LIFT 2 ---> 2(CLOSE)
T=6
LIFT 1 -- > 4(CLOSE), LIFT 2 ---> 1(CLOSE)
T=7
LIFT 1 -- >5(CLOSE), LIFT 2 ---> 0(OPEN)
T=8
LIFT 1 -- >6(OPEN), LIFT 2 ---> 0(CLOSE)
T=9
LIFT 1 OPENS
LIFT 1 -- >6 (CLOSE), LIFT 2 ---> 0(CLOSE)
T=10
LIFT 1 -- >7(OPEN), LIFT 2 ---> 0(CLOSE)
T=11
LIFT 1 OPENS
LIFT 1 -- >7(CLOSE), LIFT 2 ---> 0(CLOSE)
LIFT 1: 11 SECONDS
LIFT 2: 8 SECONDS
*/