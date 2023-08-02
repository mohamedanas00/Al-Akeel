package com.lab.task.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.lab.task.model.Runner;

import com.lab.task.model.Meal;
import com.lab.task.model.Orders;
import com.lab.task.model.Resturant;
import com.lab.task.model.User;
import com.lab.task.repo.OrderRepo;
import com.lab.task.repo.RunnerRepo;
import com.lab.task.repo.customerRepo;

import JsonOutput.MealOutput;
import JsonOutput.ResturantOutput;
import JsonOutput.OrderOutput;
import JsonOutput.RunnerOutput;
import helper.listHelper;
import helper.Time;
import helper.UserPolicy;
import helper.UsersConstant;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
@Path("/customer")
@RolesAllowed({"customer"})
public class customerService {
    @Inject
    private customerRepo repo;

    @Inject
    private RunnerRepo repoRunner;

    @Inject
    private OrderRepo repoOrder;

    @Path("/signUP")
    @POST
    public String signUp(User user) {
        try {
            if (repo.CheckUserExist(user.getEmail(), user.getPassword())) {
                user.setRole("Customer");
                repo.insert(user);
                return "SIGNUP SUCCESSFULLY ! " + user.getName() + " Your id IS :" + user.getId() + " Your Role: "
                        + user.getRole();
            }
            return "User Already Exist!";
        } catch (Exception e) {
            throw new EJBException(e);
        }
    }

    @Path("/signIN")
    @POST
    public String signIN(User user) {
        try {
            User newUser = new User();
            newUser = repo.GetSpacificUser(user.getEmail(), user.getPassword());
            if (newUser != null) {
                UsersConstant.Email = newUser.getEmail();
                UsersConstant.Name = newUser.getName();
                UsersConstant.Password = newUser.getPassword();
                UsersConstant.ID = newUser.getId();
                UsersConstant.Role = "Customer";
                return "Welcom Back " + newUser.getName() + "!	Your ID: " + newUser.getId() + " Your Mail: "
                        + newUser.getEmail() + " Your Role:" + newUser.getRole();
            }
            return "Please SignUP first!";
        } catch (Exception e) {
            throw new EJBException(e);
        }
    }

    @Path("/MakeOrder/{id}")
    @POST
    public String makeOrder(@PathParam("id") Long id, List<listHelper> list) {
        try {
            UserPolicy check = new UserPolicy();
            String text = check.CheckPolicy(UsersConstant.Role, "Customer");
            if (text != "OK") {
                return text;
            }
            float TotalValue = 0.0f;
            Set<Meal> meals = new HashSet<>();
            Resturant resturant = new Resturant();
            List<Runner> runners = new ArrayList<>();
            Orders order = new Orders();
            resturant = repo.getResturantById(id);
            if (resturant == null) {
                return "Enter Wrong ResturantID";
            }
            ;
            for (listHelper l : list) {
                for (Meal it : resturant.getMeals()) {
                    if (it.getId() == l.getIds()) {
                        TotalValue += it.getPrice();
                        meals.add(it);
                    }
                }
            }
            if (TotalValue == 0.0f) {
                return "Please Check MealId , Order not Have any Meal";
            }
            runners = repoRunner.selectAvailiableRunner();
            boolean flag = false;
            for (Runner r : runners) {
                if (r.isStatus()) {
                    r.setStatus(false);
                    repoRunner.updateRunner(r);
                    order.setRunnerData(r);
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                return "Dont have Any Runner Availible";
            }
            order.setResturantData(resturant);
            order.setItems(meals);
            order.setTotal_price(TotalValue);
            order.setOrder_status("Preparing");
            order.setDate(Time.get_Date());
            repoOrder.insertOrder(order);
            return "Order is Preparing! Your Order Id is: " + order.getId() + "Total: " + order.getTotal_price();
        } catch (Exception e) {
            throw new EJBException(e);
        }
    }

    @Path("/EditOrderByAdd/{id}")
    @PUT
    public String AddNewMealToOeder(@PathParam("id") Long id, List<listHelper> list) {
        try {
            UserPolicy check = new UserPolicy();
            String text = check.CheckPolicy(UsersConstant.Role, "Customer");
            if (text != "OK") {
                return text;
            }
            List<Meal> meals = new ArrayList<>();
            Resturant resturant = new Resturant();
            Orders order = new Orders();
            order = repoOrder.getOrderById(id);
            if (order == null) {
                return "Enter Wrong OrderID";
            }
            float TotalValue = order.getTotal_price();
            resturant = repo.getResturantById(order.getResturantData().getId());
            for (Meal it : resturant.getMeals()) {
                for (listHelper l : list) {
                    if (it.getId() == l.getIds()) {
                        TotalValue += it.getPrice();
                        meals.add(it);
                    }
                }
            }
            for (Meal it : meals) {
                order.getItems().add(it);
            }
            order.setTotal_price(TotalValue);
            repoOrder.updateOrder(order);
            return null;
        } catch (Exception e) {
            throw new EJBException(e);
        }
    }

    @Path("/Delete/{id}")
    @DELETE
    public String DeleteMealFromOrder(@PathParam("id") Long id, List<listHelper> list) {
        try {
            UserPolicy check = new UserPolicy();
            String text = check.CheckPolicy(UsersConstant.Role, "Customer");
            if (text != "OK") {
                return text;
            }
            Orders order = new Orders();
            order = repoOrder.getOrderById(id);
            float TotalValue = order.getTotal_price();
            for (listHelper l : list) {// {1}
                for (Meal it : order.getItems()) {
                    if (it.getId() == l.getIds()) {
                        TotalValue -= it.getPrice();
                        order.getItems().remove(it);
                        break;
                    }
                }
            }
            order.setTotal_price(TotalValue);
            repoOrder.updateOrder(order);
            return "Delete Sucessfuly";
        } catch (Exception e) {
            throw new EJBException(e);
        }
    }

    @Path("/cancelOrder/{id}")
    @PUT
    public String CancelOrder(@PathParam("id") Long id) {
        try {
            UserPolicy check = new UserPolicy();
            String text = check.CheckPolicy(UsersConstant.Role, "Customer");
            if (text != "OK") {
                return text;
            }
            Orders order = new Orders();
            order = repoOrder.getOrderById(id);
            if (order == null) {
                return "Wrong OrderID";
            }
            if (order.getOrder_status() == "Deliverd") {
                return "Order Can not Cancel Already Deliverd";
            }
            order.setOrder_status("Cancel");
            order.getRunnerData().setStatus(true);
            repoRunner.updateRunner(order.getRunnerData());
            repoOrder.updateOrder(order);
            return "Order Cancel Sucessfuly";
        } catch (Exception e) {
            throw new EJBException(e);
        }
    }

    @Path("/showAllResturant")
    @GET
    public List<ResturantOutput> getAllResturant() {
        List<ResturantOutput> Outputs = new ArrayList<>();
        List<Resturant> resturants = new ArrayList<>();
        resturants = repo.getAllResturant();
        UserPolicy check = new UserPolicy();
        String text = check.CheckPolicy(UsersConstant.Role, "Customer");
        if (text != "OK") {
            return null;
        }
        for (Resturant it : resturants) {
            List<MealOutput> list = new ArrayList<>();
            ResturantOutput Output = new ResturantOutput();
            Output.setId(it.getId());
            Output.setName(it.getName());
            for (Meal its : it.getMeals()) {
                MealOutput meal = new MealOutput();
                meal.setId(its.getId());
                meal.setName(its.getName());
                meal.setPrice(its.getPrice());
                list.add(meal);
            }
            Output.setMenu(list);
            Outputs.add(Output);
        }
        return Outputs;
    }

    @Path("showOrderByID/{id}")
    @GET
    public OrderOutput getOrderDetails(@PathParam("id") Long id) {
        Orders order = new Orders();
        OrderOutput jsonOrder = new OrderOutput();
        ResturantOutput jsonRest = new ResturantOutput();
        RunnerOutput jsonRunner = new RunnerOutput();
        UserPolicy check = new UserPolicy();
        String text = check.CheckPolicy(UsersConstant.Role, "Customer");
        if (text != "OK") {
            jsonOrder.setMessage(text);
            return jsonOrder;
        }
        List<MealOutput> list = new ArrayList<>();
        order = repoOrder.getOrderById(id);
        if (order == null) {
            jsonOrder.setMessage("You Enter Wrong Order ID");
            return jsonOrder;
        }
        jsonOrder.setOrderId(order.getId());
        jsonOrder.setTime(order.getDate());
        jsonOrder.setTotalPrice(order.getTotal_price());
        jsonOrder.setOrder_State(order.getOrder_status());
        for (Meal it : order.getItems()) {
            MealOutput meal = new MealOutput();
            meal.setId(it.getId());
            meal.setName(it.getName());
            meal.setPrice(it.getPrice());
            list.add(meal);
        }
        jsonOrder.setMealsData(list);
        jsonRest.setId(order.getResturantData().getId());
        jsonRest.setName(order.getResturantData().getName());
        jsonRest.setOwnerId(order.getResturantData().getOwnerId());
        jsonOrder.setResturantData(jsonRest);
        jsonRunner.setName(order.getRunnerData().getName());
        jsonRunner.setFees(order.getRunnerData().getDelivery_fees());
        jsonOrder.setMessage("Welcome! this is Your Order");
        jsonOrder.setRunnerData(jsonRunner);
        return jsonOrder;
    }
}
