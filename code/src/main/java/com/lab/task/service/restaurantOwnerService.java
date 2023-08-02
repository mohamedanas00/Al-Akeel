package com.lab.task.service;

import java.util.List;
import java.util.ArrayList;
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

import com.lab.task.model.Meal;
import com.lab.task.model.Resturant;
import com.lab.task.model.User;
import com.lab.task.model.Orders;
import com.lab.task.repo.resturantOwnerRepo;

import JsonOutput.ResturantOutput;
import helper.UserPolicy;
import helper.UsersConstant;
import JsonOutput.MealOutput;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
@Path("/owner")
@RolesAllowed({"owner"})
public class restaurantOwnerService {
    @Inject
    private resturantOwnerRepo repo;

    @Path("/signUP")
    @POST
    public String signUp(User user) {
        try {
            if (repo.CheckUserExist(user.getEmail(), user.getPassword())) {
                user.setRole("ResturantOwner");
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
                UsersConstant.Role = "ResturantOwner";
                return "Welcom Back " + newUser.getName() + "!	Your ID: " + newUser.getId() + " Your Mail: "
                        + newUser.getEmail() + " Your Role:" + newUser.getRole();
            }
            return "Please SignUP first!";
        } catch (Exception e) {
            throw new EJBException(e);
        }
    }

    @Path("/AddResturant")
    @POST
    public String addResturant(Resturant resturant) {
        try {
            UserPolicy check = new UserPolicy();
            String text = check.CheckPolicy(UsersConstant.Role, "ResturantOwner");
            if (text != "OK") {
                return text;
            }
            resturant.setOwnerId(UsersConstant.ID);
            repo.insertResturant(resturant);
            for (Meal meal : resturant.getMeals()) {
                meal.setResturantId(resturant);
                repo.insertMenu(meal);
            }
            return "Resturant: " + resturant.getName() + " Menu Adding Suceesfully. " + " ID:" + resturant.getId();
        } catch (Exception e) {
            throw new EJBException(e);
        }
    }

    @Path("/AddMeal/{id}")
    @POST
    public String addResturantMeal(@PathParam("id") Long id, Meal meal) {
        try {
            UserPolicy check = new UserPolicy();
            String text = check.CheckPolicy(UsersConstant.Role, "ResturantOwner");
            if (text != "OK") {
                return text;
            }
            Resturant resturant = new Resturant();
            resturant = repo.getResturantById(id);
            if (resturant.getOwnerId() != UsersConstant.ID) {
                return "This Owner Dont Have permision to Resturant";
            }
            meal.setResturantId(resturant);
            repo.insertMenu(meal);
            return "Adding Suceesfully";
        } catch (Exception e) {
            throw new EJBException(e);
        }
    }

    @Path("{restaurantId}/AddMeal/{mealId}")
    @PUT
    public String UpdateResturantMeal(@PathParam("restaurantId") Long id, @PathParam("mealId") Long idmeal, Meal meal) {
        try {
            UserPolicy check = new UserPolicy();
            String text = check.CheckPolicy(UsersConstant.Role, "ResturantOwner");
            if (text != "OK") {
                return text;
            }
            Resturant resturant = new Resturant();
            resturant = repo.getResturantById(id);
            boolean flag = true;
            if (resturant == null) {
                return "You Enter Wrong ResturantID";
            }
            if (resturant.getOwnerId() != UsersConstant.ID) {
                return "This Owner Dont Have permision to Resturant";
            }
            for (Meal it : resturant.getMeals()) {
                if (it.getId() == idmeal) {
                    if (meal.getName() != null) {
                        it.setName(meal.getName());
                        flag = false;
                        repo.updateResturant(resturant);
                        repo.updateMeal(it);
                    }
                    if (meal.getPrice() != 0.0f) {
                        it.setPrice(meal.getPrice());
                        flag = false;
                        repo.updateResturant(resturant);
                        repo.updateMeal(it);
                    }
                    break;
                }
            }
            if (flag) {
                return "You do not update any data,check your meal id or meal data";
            }
            return "Adding Suceesfully";
        } catch (Exception e) {
            throw new EJBException(e);
        }
    }

    @Path("/showResByID/{id}")
    @GET
    public ResturantOutput getResturants(@PathParam("id") Long id) {
        UserPolicy check = new UserPolicy();
        String text = check.CheckPolicy(UsersConstant.Role, "ResturantOwner");
        if (text != "OK") {
            return null;
        }
        Resturant resturant = new Resturant();
        List<MealOutput> list = new ArrayList<>();
        ResturantOutput Output = new ResturantOutput();
        resturant = repo.getResturantById(id);
        if (resturant.getOwnerId() != UsersConstant.ID) {

            return null;
        }
        Output.setId(resturant.getId());
        Output.setName(resturant.getName());
        Output.setOwnerId(resturant.getOwnerId());
        for (Meal it : resturant.getMeals()) {
            MealOutput meal = new MealOutput();
            meal.setId(it.getId());
            meal.setName(it.getName());
            meal.setPrice(it.getPrice());
            list.add(meal);
        }
        Output.setMenu(list);
        return Output;
    }

    @Path("/{restaurantId}/deletemeals/{mealId}")
    @DELETE
    public String deleteResturantMeal(@PathParam("restaurantId") Long id, @PathParam("mealId") Long idmeal) {
        try {
            UserPolicy check = new UserPolicy();
            String text = check.CheckPolicy(UsersConstant.Role, "ResturantOwner");
            if (text != "OK") {
                return text;
            }
            Resturant resturant = new Resturant();
            Meal meal = new Meal();
            Meal meal2 = new Meal();
            resturant = repo.getResturantById(id);
            if (resturant == null) {
                return "Wrong ResturantID";
            }
            if (resturant.getOwnerId() != UsersConstant.ID) {
                return "This Owner Dont Have permision to Resturant";
            }
            for (Meal it : resturant.getMeals()) {
                if (it.getId() == idmeal) {
                    meal = it;
                    break;
                }
            }
            if (meal != null) {
                resturant.getMeals().remove(meal);
                repo.updateResturant(resturant);
            }
            meal2 = repo.getMealById(idmeal);
            if (meal2 != null) {
                repo.deleteMealById(idmeal);
            }

            return "DELETING Suceesfully";
        } catch (Exception e) {
            throw new EJBException(e);
        }
    }

    @Path("/ReportResByID/{id}")
    @GET
    public String getReportResturants(@PathParam("id") Long id) {
        UserPolicy check = new UserPolicy();
        String text = check.CheckPolicy(UsersConstant.Role, "ResturantOwner");
        if (text != "OK") {
            return null;
        }
        float TotalEarn = 0.0f;
        int CompeleteOrder = 0;
        int CancelOrder = 0;
        Resturant resturant = new Resturant();
        resturant = repo.getResturantById(id);
        if (resturant == null) {
            return "Wrong ResturantID";
        }
        if (resturant.getOwnerId() != UsersConstant.ID) {
            return "This Owner Dont Have permision to Resturant";
        }
        for (Orders it : resturant.getOrders()) {
            if (it.getOrder_status() == "Deliverd") {
                CompeleteOrder += 1;
                TotalEarn += it.getTotal_price();
            } else if (it.getOrder_status() == "Cancel") {
                CancelOrder += 1;
            }
        }
        return "Restaurant Earns: " + TotalEarn + " completed Orders: " + CompeleteOrder + "Cancel Order: " + CancelOrder;
    }


}
