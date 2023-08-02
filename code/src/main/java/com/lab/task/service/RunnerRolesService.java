package com.lab.task.service;


import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.lab.task.model.Orders;
import com.lab.task.model.Runner;
import com.lab.task.repo.OrderRepo;
import com.lab.task.repo.RunnerRepo;
import helper.UsersConstant;
import helper.UserPolicy;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
@Path("/runner")
@RolesAllowed({"runner"})
public class RunnerRolesService {
    @Inject
    private RunnerRepo repo;

    @Inject
    private OrderRepo repoOrder;


    @Path("/signUP")
    @POST
    public String signUp(Runner runner) {
        try {
            if (repo.CheckRunnerExist(runner.getEmail(), runner.getPassword())) {
                runner.setStatus(true);
                repo.insertRuner(runner);
                return "SIGNUP SUCCESSFULLY ! " + runner.getName() + " Your id IS :" + runner.getId() + " Your Role:Runner";
            }
            return "User Already Exist!";
        } catch (Exception e) {
            throw new EJBException(e);
        }
    }

    @Path("/signIN")
    @POST
    public String signIN(Runner runner) {
        try {
            Runner newRunner = new Runner();
            newRunner = repo.GetSpacificRunner(runner.getEmail(), runner.getPassword());
            if (newRunner != null) {
                UsersConstant.Email = newRunner.getEmail();
                UsersConstant.Name = newRunner.getName();
                UsersConstant.Password = newRunner.getPassword();
                UsersConstant.ID = newRunner.getId();
                UsersConstant.Role = "Runner";
                return "Welcom Back " + newRunner.getName() + "!	Your ID: " + newRunner.getId() + " Your Mail: " + newRunner.getEmail();
            }
            return "Please SignUP first!";
        } catch (Exception e) {
            throw new EJBException(e);
        }
    }

    @Path("/mark")
    @GET
    public String MarkAsDeliverd() {
        UserPolicy check = new UserPolicy();
        String text = check.CheckPolicy(UsersConstant.Role, "Runner");
        if (text != "OK") {
            return text;
        }
        Runner newRunner = new Runner();
        newRunner = repo.GetSpacificRunner(UsersConstant.Email, UsersConstant.Password);
        if (newRunner == null) {
            return "You Should SignIn first";
        }
        for (Orders it : newRunner.getOrders()) {
            if (it.getOrder_status() == "Preparing") {
                it.setOrder_status("Deliverd");
                newRunner.setStatus(true);
                repo.updateRunner(newRunner);
                repoOrder.updateOrder(it);
                return "Done! Order has deliverd";
            }
        }
        return "All Order Marked You Dont have any delivers";
    }

    @Path("/Trips")
    @GET
    public String GetNumberOfTrips() {
        UserPolicy check = new UserPolicy();
        String text = check.CheckPolicy(UsersConstant.Role, "Runner");
        if (text != "OK") {
            return text;
        }
        int numberOfTrips = 0;
        Runner newRunner = new Runner();
        newRunner = repo.GetSpacificRunner(UsersConstant.Email, UsersConstant.Password);
        if (newRunner == null) {
            return "You Should SignIn first";
        }
        for (Orders it : newRunner.getOrders()) {
            if (it.getOrder_status() == "Deliverd") {
                numberOfTrips += 1;
            }
        }
        return "Your number of trips completed is: " + numberOfTrips;
    }


}
