package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
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

    public Meal create(Meal meal, int userId) {
        log.debug("service Create {}", meal.toString());
        return repository.save(meal, userId);
    }

    public void delete(int id, int userId) {
        log.debug("service Delete {}", id);
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public Meal get(int id, int userId) {
        log.debug("get Meal by id = {}", id);
        return checkNotFoundWithId(repository.get(id, userId), id);
    }


    public List<Meal> getByTime(LocalTime startTime, LocalTime endTime, Integer userId) {
        return repository.getAll(userId).stream().filter(meal -> DateTimeUtil.isBetweenInclusiveTime(meal.getTime(), startTime, endTime)).collect(Collectors.toList());
    }

    public List<Meal> getByDate(LocalDate startDate, LocalDate endDate, Integer userId) {
        return repository.getAll(userId).stream().filter(meal -> DateTimeUtil.isBetweenInclusiveDate(meal.getDate(), startDate, endDate)).collect(Collectors.toList());
    }

    public List<Meal> getUserMeals(Integer userId) {
        if (userId == null) {
            return null;
        }
        return repository.getAll(userId).stream()
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());
    }


    public void update(Meal meal, int userId) {
        checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }
}