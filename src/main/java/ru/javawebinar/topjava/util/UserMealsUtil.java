package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

// Meal = Food = Еда;  Exceed = Limit = Превышение;

    public static List<UserMealWithExceed>  getFilteredWithExceeded
            (List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map <LocalDate, Integer> mapMeal = new HashMap<>();
        LocalDate localDate;
        List <UserMealWithExceed> userMealWithExceedList = new ArrayList<>();

        for (UserMeal meal: mealList) {
            localDate = meal.getDateTime().toLocalDate();
            if (mapMeal.containsKey(localDate)) {
                mapMeal.put(localDate, mapMeal.get(localDate) + meal.getCalories());
            } else {
                mapMeal.put(meal.getDateTime().toLocalDate(), meal.getCalories());
            }
        }

        for (UserMeal meal: mealList) {
            if (TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                userMealWithExceedList.add(
                        new UserMealWithExceed(
                                meal.getDateTime(),
                                meal.getDescription(),
                                meal.getCalories(),
                                (mapMeal.get(meal.getDateTime().toLocalDate()) > caloriesPerDay)
                        ));
            }
        }
        return userMealWithExceedList;
    }
}
