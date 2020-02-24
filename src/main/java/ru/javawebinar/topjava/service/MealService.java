package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.filter.MealByNameFilter;
import ru.javawebinar.topjava.filter.ModelFilter;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private static final Logger log = getLogger(MealService.class);

    private MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal) {
        log.debug("service Create {}", meal.toString());
        return repository.save(meal);
    }

    public void delete(int id) {
        log.debug("service Delete {}", id);
        checkNotFoundWithId(repository.delete(id), id);
    }

    public Meal get(int id) {
        log.debug("get Meal by id = {}", id);
        return checkNotFoundWithId(repository.get(id), id);
    }

    public List<Meal> getByName(String name, Integer id) {
        log.debug("get Meal by name and user id");
        ModelFilter filter = new MealByNameFilter();
        return filter.meetCriteria((List) repository.getAll(id), name);
    }

    public List<Meal> getByDateTime(LocalTime startDateTime, LocalTime endDateTime, Integer userId) {
        return repository.getAll(userId).stream().filter(meal -> DateTimeUtil.isBetweenInclusive(meal.getTime(), startDateTime, endDateTime)).collect(Collectors.toList());
    }

    public List<Meal> getUserMeals(Integer userId) {
        if (userId == null) {
            return null;
        }
        return (List<Meal>) repository.getAll(userId);
    }


    public void update(Meal meal) {
        checkNotFoundWithId(repository.save(meal), meal.getId());
    }
}