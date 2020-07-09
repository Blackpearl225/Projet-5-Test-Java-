package com.parkit.parkingsystem.service;

import java.util.Date;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }

        Date inHour = ticket.getInTime();
        Date outHour = ticket.getOutTime();
        

        //TODO: Some tests are failing here. Need to check if this logic is correct
        long duration = outHour.getTime() - inHour.getTime();
        double total = duration;
        double modulo = 0;
        total/=1000*60;
        if(total <30)
        	total =0;
        else if(total>=30 && total<60){
        	total = (double)total/60;   	
        }
        else {
        	
        	modulo = total%60;
        	modulo = (double) total/60;
        	total = (double)total/60;
        	total+=modulo;
        	total/=2;
        	
        }
        
        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
                ticket.setPrice(total * Fare.CAR_RATE_PER_HOUR);
                break;
            }
            case BIKE: {
                ticket.setPrice(total * Fare.BIKE_RATE_PER_HOUR);
                break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
    }
}