package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management

        UserRepository us = new InMemoryUserRepository();
        List<User> users = us.getAll();
        for (User u : users
        ) {
            System.out.println(u);

        }
        MealRepository mr = new InMemoryMealRepository();
        //Get check
        System.out.println("<<=Get check=>>");
        System.out.println(mr.get(6, 4));
        System.out.println(mr.get(1, 1));
        System.out.println("<=End get check=>");

        //Delete check
        System.out.println("<<=Delete check=>>");
        System.out.println(mr.delete(6, 4));
        System.out.println(mr.delete(2, 1));
        System.out.println("<=End delete check=>");

        //Save check
        System.out.println("<<=Save check=>>");
        Meal testMeal = new Meal(4, LocalDateTime.of(2021, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
        System.out.println(mr.save(testMeal, 4));
        System.out.println(mr.get(8, 4));
        System.out.println("<=End save check=>");

        //Update check
        System.out.println("<<=Update check=>>");
        Meal testMeal2 = new Meal(4, LocalDateTime.of(2021, Month.MARCH, 30, 10, 0), "Завтрак", 500);
        System.out.println(mr.save(testMeal, 4));
        System.out.println(mr.get(8, 4));

        System.out.println(mr.save(
                new Meal(8, 4,
                        LocalDateTime.of(2021, Month.FEBRUARY,
                                28, 10, 0),
                        "ПЛОТНЫЙ УЖИН", 500), 4));

        System.out.println(mr.save(
                new Meal(8, 4,
                        LocalDateTime.of(2021, Month.FEBRUARY,
                                28, 10, 0),
                        "СНОВА ЗАВТРАК", 500), 5));

        System.out.println(mr.get(8, 4));
        System.out.println("<=End update check=>");
       /* try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            MealRestController mealRestController = appCtx.getBean(MealRestController.class);

            mealRestController.create(new Meal(null, 1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        }*/
    }
}
