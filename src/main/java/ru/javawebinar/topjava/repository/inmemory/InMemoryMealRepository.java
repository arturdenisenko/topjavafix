package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = getLogger(InMemoryMealRepository.class);

    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(m -> {
            save(m, m.getUserId());
        });
    }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("save new Meal {}", meal.toString());

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return checkUserAffiliation(meal.getId(), userId)
                ? repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal) : null;

    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("remove meal with id {} user  {}", id, userId);
        return checkUserAffiliation(id, userId) && repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get meal with id {} and user id {}", id, userId);
        return checkUserAffiliation(id, userId) ? repository.get(id) : null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        log.info("get all meals by user id = {}", userId);
        Collection<Meal> collection = repository.values();
        return collection.stream()
                .filter(meal -> meal.getUserId() == userId)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private boolean checkUserAffiliation(int id, int userId) {
        return (repository.get(id).getUserId() == userId);
    }
}
