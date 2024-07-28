package com.Organizer.organizer.helpers;

import java.security.Principal;

public class helpers {

    public static String loggedInUserEmail(Principal principal){
        return principal.getName();
    }
}