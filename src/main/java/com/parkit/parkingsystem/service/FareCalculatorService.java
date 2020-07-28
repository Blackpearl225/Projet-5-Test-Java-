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
        long data = outHour.getTime() - inHour.getTime();
        double duration=data;
        duration/=1000*60;
        if(duration <30)
        	duration = 0;
        else if(duration>=30 && duration<60)
        	duration=(double)duration/60;
        else 
        	duration = duration/60;   	
        
        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
                ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR);
                break;
            }
            case BIKE: {
                ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR);
                break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
    }
    //Applied discount if recurrent user
    public void calculateFareWithDiscount(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }

        Date inHour = ticket.getInTime();
        Date outHour = ticket.getOutTime();
        
        long data  = outHour.getTime() - inHour.getTime();
        double duration = data;
        duration/=1000*60;
        if(duration <30)
        	duration = 0;
        else if(duration>=30 && duration<60)
        	duration=(double)duration/60;
        else 
        	duration = duration/60;   	
        
        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
                ticket.setPrice( (duration * Fare.CAR_RATE_PER_HOUR) - (0.05* duration * Fare.CAR_RATE_PER_HOUR));
                break;
            }
            case BIKE: {
                ticket.setPrice((duration * Fare.BIKE_RATE_PER_HOUR)-(0.05* duration * Fare.BIKE_RATE_PER_HOUR));
                break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
    }
}
