package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {

    private static final Logger log = getLogger(MealRestController.class);

    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<Meal> getAll(int userId) {
        log.info("get all meals for user with id {}", userId);
        return service.getUserMeals(userId);
    }

    public Meal get(int id, int userId) {
        log.info("get meal with {}, user id {}", id, userId);
        return service.get(id, userId);
    }

    public Meal create(Meal meal, int userId) {
        log.info("create meal {} for user {}", meal, userId);
        checkNew(meal);
        return service.create(meal, userId);
    }

    public void delete(int id, int userId) {
        log.info("delete {}", id);
        service.delete(id, userId);
    }

    public void update(Meal meal, int userId) {
        log.info("update {} with id={}", meal, userId);
        assureIdConsistent(meal, meal.getId());
        service.update(meal, userId);
    }

    public List<Meal> getByTime(LocalTime startTime, LocalTime endTime, List<Meal> list, Integer userId) {
        log.info("get meal filter by date time start date time = {} end date time = {} user id = {}", startTime, endTime, userId);
        return service.getByTime(startTime, endTime, list, userId);
    }

    public List<Meal> getByDate(LocalDate startDate, LocalDate endDate, List<Meal> list, Integer userId) {
        log.info("get meal filter by date time start date time = {} end date time = {} user id = {}", startDate, endDate, userId);
        return service.getByDate(startDate, endDate, list, userId);
    }
}