package Group.com.starmerch.Artifact.star_merch_hub.controller;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import Group.com.starmerch.Artifact.star_merch_hub.model.Role;
import Group.com.starmerch.Artifact.star_merch_hub.model.User;

@ControllerAdvice
public class GlobalModelAttributes {

    @ModelAttribute
    public void addUserInformation(
            Model model,
            Authentication authentication) {

        model.addAttribute(
            "loggedIn",
            false
        );

        model.addAttribute(
            "isAdmin",
            false
        );

        model.addAttribute(
            "isStaff",
            false
        );

        model.addAttribute(
            "isStaffOrAdmin",
            false
        );


        if (authentication != null
                && authentication.getPrincipal()
                    instanceof User user) {

            model.addAttribute(
                "loggedIn",
                true
            );

            model.addAttribute(
                "currentUsername",
                user.getUsername()
            );

            model.addAttribute(
                "currentRole",
                user.getRole()
            );

            boolean admin =
                user.getRole() == Role.ADMIN;

            boolean staff =
                user.getRole() == Role.STAFF;

            model.addAttribute(
                "isAdmin",
                admin
            );

            model.addAttribute(
                "isStaff",
                staff
            );

            model.addAttribute(
                "isStaffOrAdmin",
                admin || staff
            );
        }
    }
}