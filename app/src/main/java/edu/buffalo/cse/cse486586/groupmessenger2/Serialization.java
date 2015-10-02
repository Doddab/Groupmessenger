package edu.buffalo.cse.cse486586.groupmessenger2;
import java.io.Serializable;
/**
 * Created by user on 10/3/15.
 */
public class Serialization implements Serializable,Comparable {

        String message;

    @Override
    public int compareTo(Object another) {
        return 0;
    }

    Double message_id;
        //String message_order;
        int counter=0;
        String myPort;
        boolean deliverable=false;
        double proposedvalue;

    public Double getCurrentvalue() {
        return currentvalue;
    }

    public void setCurrentvalue(Double currentvalue) {
        this.currentvalue = currentvalue;
    }

    public Double getProposedvalue() {
        return proposedvalue;
    }

    public void setProposedvalue(Double proposedvalue) {
        this.proposedvalue = proposedvalue;
    }

    public Double getFinalvalue() {
        return finalvalue;
    }

    public void setFinalvalue(Double finalvalue) {
        this.finalvalue = finalvalue;
    }

    Double currentvalue = message_id;
        Double finalvalue;


    public Double getFinal_value() {
        return Final_value;
    }

    public void setFinal_value(Double final_value) {
        Final_value = final_value;
    }

    public boolean isDeliverable() {
        return deliverable;
    }

    public void setDeliverable(boolean deliverable) {
        this.deliverable = deliverable;
    }

    public String getMyPort() {
        return myPort;
    }

    public void setMyPort(String myPort) {
        this.myPort = myPort;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Double getMessage_id() {
        return message_id;
    }

    public void setMessage_id(Double message_id) {
        this.message_id = message_id;
    }

    /*public String getMessage_order() {
        return message_order;
    }*/

    /*public void setMessage_order(String message_order) {
        this.message_order = message_order;
    }*/

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    Double Final_value;


        public Serialization(String message, Double message_id, String myPort) {
            this.message = message;
            this.message_id = message_id;
            this.myPort = myPort;
        }

    }

//Comparator method to compare proposed value and current value
//ComapareTo method implementation

