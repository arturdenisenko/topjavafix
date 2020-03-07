package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    @Autowired
    private MealRestController mealRestController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        mealRestController = new MealRestController(new MealService(new InMemoryMealRepository()));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        int userId = Integer.parseInt(request.getParameter("userId"));

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id), userId,
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        if (meal.isNew()) {
            log.info("Create {}", meal);
            mealRestController.create(meal, userId);
        } else {
            log.info("Update {}", meal);
            mealRestController.update(meal, userId);
        }
        response.sendRedirect("meals?action=getAll&userId=" + userId);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        int userId;
        userId = Integer.parseInt(request.getParameter("userId"));
        request.setAttribute("userId", userId);

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {} for user {}", id, userId);
                mealRestController.delete(id, userId);
                response.sendRedirect("meals?action=getAll&userId=" + userId);
                break;
            case "filter":
                log.info("filter by date or time");
                LocalDate fromDate = LocalDate.parse(request.getParameter("fromDate"));
                LocalDate toDate = LocalDate.parse(request.getParameter("toDate"));
                LocalTime fromTime = LocalTime.parse(request.getParameter("fromTime"));
                LocalTime toTime = LocalTime.parse(request.getParameter("toTime"));
                List<Meal> tempList = mealRestController.getByDate(fromDate, toDate, userId);
                tempList = mealRestController.getByTime(fromTime, toTime, userId);
                request.setAttribute("meals", MealsUtil.getTos(tempList, SecurityUtil.authUserCaloriesPerDay()));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(userId, LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealRestController.get(getId(request), userId);

                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                request.setAttribute("meals",
                        MealsUtil.getTos(mealRestController.getAll(userId), SecurityUtil.authUserCaloriesPerDay()));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }

    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
