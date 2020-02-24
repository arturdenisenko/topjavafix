package ru.javawebinar.topjava.filter;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;

public class MealByNameFilter implements ModelFilter<Meal, String> {
    @Override
    public List<Meal> meetCriteria(List<Meal> allMeals, String name) {
        List<Meal> meals = new ArrayList<>();
        for (Meal meal : allMeals) {
            if (meal.getDescription().contains(name)) {
                meals.add(meal);
            }
        }
        return meals;
    }
}
