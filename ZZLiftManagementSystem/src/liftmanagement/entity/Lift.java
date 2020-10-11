/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liftmanagement.entity;
import java.util.LinkedList;
import java.util.List;


public class Lift {
    
    int liftId;
    LiftState liftState;
    int currentFloor;
    LiftDirection direction;
    List<LiftRequest> request;
    int lastFloorServed ;
    int unitTime;

    public Lift(int liftId, LiftState liftState, int currentFloor, LiftDirection direction) {
        this.liftId = liftId;
        this.liftState = liftState;
        this.currentFloor = currentFloor;
        this.direction = direction;
        this.request = new LinkedList<>();
        this.lastFloorServed = -1;
    }
    
    public void getRequest(Request r){
        if(request.size() == 0){
            if(r.sourceFloor > currentFloor)
                direction = LiftDirection.UP;
            else if(r.sourceFloor < currentFloor)
                direction = LiftDirection.DOWN;
            else if(r.destinationFloor > currentFloor)
                direction = LiftDirection.UP;
            else if(r.destinationFloor < currentFloor)
                direction = LiftDirection.DOWN;
        }
        LiftRequest lr = new LiftRequest(r.sourceFloor, r.destinationFloor, unitTime);
        request.add(lr);
    }
    
    public void moveUp(){
        currentFloor++;
    }
    
    public void moveDown(){
        currentFloor--;
    }
    
    public void setDirection(){
        if(direction == LiftDirection.UP){
            for(Request r: request)
            {
                if(r.destinationFloor > currentFloor)
                    return;
            }
            direction = LiftDirection.DOWN;
        }else if(direction == LiftDirection.DOWN){
            for(Request r: request)
            {
                if(r.destinationFloor < currentFloor)
                    return;
            }
            direction = LiftDirection.UP;
        }
    }
    
    public void move(){
        
        //check if current floor has soucre or destination 
        if(request.size() == 0)
        {
            //unitTime = 0;
            liftState = LiftState.CLOSE;
            return;
        }
        unitTime++;
        
        if(liftState == LiftState.OPEN)
        {
            liftState = LiftState.CLOSE;
            return;
        }
        //current floor is soucre then open and close
        for(LiftRequest r : request){
            // open and close
            if(currentFloor == r.sourceFloor && r.sourceFinieshed == false && lastFloorServed != currentFloor){
                //open gate
                liftState = LiftState.OPEN;
                r.sourceFinieshed = true;
                lastFloorServed = currentFloor;
                setDirection();
                return;
            }
            
        }
        
        //current floor is destination then open 
        for(LiftRequest r : request){
            // open and close
            if(currentFloor == r.destinationFloor && r.sourceFinieshed == true && lastFloorServed != currentFloor){
                //remove this request from queue
                request.remove(r);
                liftState  = LiftState.OPEN;
                lastFloorServed = currentFloor;
                setDirection();
                return;
            }
            
        }
        
        if(direction == LiftDirection.UP)
            moveUp();
        else
            moveDown();
 
    }

    public List<LiftRequest> getRequest() {
        return request;
    }

    public void setRequest(List<LiftRequest> request) {
        this.request = request;
    }

    public int getLiftId() {
        return liftId;
    }

    public void setLiftId(int liftId) {
        this.liftId = liftId;
    }

    public LiftState getLiftState() {
        return liftState;
    }

    public void setLiftState(LiftState liftState) {
        this.liftState = liftState;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public int getUnitTime() {
        return unitTime;
    }

    public void setUnitTime(int unitTime) {
        this.unitTime = unitTime;
    }

    public LiftDirection getDirection() {
        return direction;
    }

    public void setDirection(LiftDirection direction) {
        this.direction = direction;
    }

    public int getLastFloorServed() {
        return lastFloorServed;
    }

    public void setLastFloorServed(int lastFloorServed) {
        this.lastFloorServed = lastFloorServed;
    }
    
    
    
}
