package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

public class MealRestController {

    private static final Logger log = getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public List<Meal> getAll(Integer userId) {
        log.info("get all meals from users");
        return service.getUserMeals(userId);
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(meal);
    }

    public List<Meal> getByName(String name, Integer id) {
        log.info("get meal by description {} and user id {}", name, id);
        return service.getByName(name, id);
    }

    public List<Meal> getByDateTime(LocalTime startDateTime, LocalTime endDateTime, Integer userId) {
        log.info("get meal filter by date time start date time = {} end date time = {} user id = {}", startDateTime, endDateTime, userId);
        return service.getByDateTime(startDateTime, endDateTime, userId);
    }
}