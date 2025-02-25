package com.project.cinemamanagement.Event;

import com.project.cinemamanagement.Entity.User;
import com.project.cinemamanagement.PayLoad.Request.UserRequest;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private UserRequest user;


    public OnRegistrationCompleteEvent(Object source, UserRequest user) {
        super(source);
        this.user = user;
    }

}
