package ru.mephi.malskiy;

import ru.mephi.malskiy.enums.Coordinates;
import ru.mephi.malskiy.service.WeatherService;

import java.util.Scanner;

public class WeatherApp {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            WeatherService weatherService = new WeatherService();

            while (true) {
                System.out.println("Введите код города или EX что бы выйти");
                System.out.println("Доступные города: Москва(MSC), Санкт-Петербург(SPB)");
                System.out.print("> ");
                String code = scanner.nextLine().trim().toUpperCase();

                if (code.equals("EX")) {
                    break;
                }

                Coordinates coordinates = Coordinates.findByCode(code);
                if (coordinates == null) {
                    System.out.println("Сервис пока не предоставляет информацию по данному городу");
                    continue;
                }

                System.out.println("Введите количество дней в прогнозе, включая текущий");
                int lim = scanner.nextInt();
                scanner.nextLine();

                if (lim < 1 || lim > 11) {
                    System.out.println("Количество дней не может быть меньше 1 и больше 11");
                    continue;
                }

                weatherService.getWeather(coordinates.getLocation(), lim);
            }
        } catch (Exception e){
            System.out.println("Ошибка работы программы " + e.getMessage());
            e.printStackTrace();
        }
    }
}
