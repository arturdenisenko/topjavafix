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
    //TODO Filters here, update fix,
    private static final Logger log = getLogger(InMemoryMealRepository.class);

    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    //private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(m -> {
            save(m, m.getUserId());
        });
    }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("save new Meal {} for user with id {}", meal, userId);

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            if (!userHaveFood(userId)) {
                repository.put(userId,
                        new ConcurrentHashMap<Integer, Meal>() {
                            {
                                put(meal.getId(), meal);
                            }
                        });
            } else {
                Map<Integer, Meal> mealMap = repository.get(userId);
                mealMap.put(meal.getId(), meal);
                repository.put(userId, mealMap);
            }
            return meal;
        }
        return null;
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("remove meal with id {} user  {}", id, userId);
        if (userHaveFood(userId)) {
            Map<Integer, Meal> mealMap = repository.get(userId);
            return mealMap.remove(id) != null;
        }
        return false;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get meal with id {} and user id {}", id, userId);
        if (userHaveFood(userId)){
            Map<Integer, Meal> mealMap = repository.get(userId);
            return mealMap.get(id);
        }
        return null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        log.info("get all meals by user id = {}", userId);
        if (userHaveFood(userId)) {

            Collection<Meal> collection = getUserFood(userId).values();
            return collection.stream()
                    .filter(meal -> meal.getUserId() == userId)
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        return null;
    }

    private boolean userHaveFood(int userId) {
        return repository.containsKey(userId);
    }

    private Map<Integer, Meal> getUserFood(int userId) {
        return repository.get(userId);
    }
}
