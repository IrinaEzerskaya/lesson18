package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        Path path = Paths.get("products.txt");
        int line = 0;

        // Специальный класс для хранения информации о товаре
        ProductItem currentItem = new ProductItem();

        try (Stream<String> lines = Files.lines(path)) {

            // Для реализации данного функционала использовать StreamAPI.
            // Конвертируем StringAPI в StringList
            List<String> readLines = lines
                    .collect(Collectors.toList());

            // Инициализируем буфер для сохранения записей
            // Данные о товарах хранить в списке, реализовать чтение данных из файла в список
            List<ProductItem> productItems = new ArrayList<>();

            // Определяем итератор для чтения
            Iterator<String> iterator = readLines.iterator();

            // Открываем цикл чтения
            while (iterator.hasNext()) {

                String inputLine = iterator.next();
                if (inputLine != null) {
                    switch (line) {
                        // Первая строка содержит название
                        case 0 -> currentItem.name = inputLine;
                        // Вторая - количество
                        case 1 -> currentItem.quantity = Float.parseFloat(inputLine);
                        // Третья - цена
                        case 2 -> currentItem.price = Float.parseFloat(inputLine);
                    }
                    line++;
                    // Если достигли третьей строки, сбрасываем накопленное в буфер
                    if (line>2) {
                        line=0;
                        productItems.add(currentItem);
                        currentItem = new ProductItem();
                    }
                }
            }

            // Открываем цикл чтения по буферу для определения поставленных вопросов
            //productItems.forEach((n) -> System.out.println(n.name + ", " + n.price + " " + n.quantity));
            float maxAllowedPrice = 100; // Цена отсечки для подсчета кол-ва товаров
            int maxPriceCount = 0;
            float sumPrice = 0;
            int itemCount = 0;
            float minPrice = -1;
            String minPriceName = "";

            /**
             * посчитать количество товаров в чеке, стоимость которых больше заданной,
             * найти товар с минимальной ценой, вывести информацию о нем
             * найти среднюю цену товаров.
             */

            System.out.println("Товары в чеке:");

            for (ProductItem item : productItems) {
                if (item.price > maxAllowedPrice) maxPriceCount++;
                if (minPrice<0) {
                    // Начальная инициализация
                    minPrice = item.price;
                    minPriceName = item.name + ", цена: " + item.price;
                } else {
                    if (minPrice>item.price) {
                        // Найден товар с меньшей ценой
                        minPrice = item.price;
                        minPriceName = item.name + ", цена: " + item.price;
                    }
                }

                sumPrice += item.price;
                itemCount++;

                System.out.println(item.name + ", цена: " + item.price + ", кол-во: " + item.quantity);
            }

            System.out.println("=============================");
            System.out.println("Количество товаров в чеке, стоимость которых больше заданной ("+maxAllowedPrice+"): " + maxPriceCount);
            System.out.println("Товар с минимальной ценой: " + minPriceName);
            System.out.println("Средняя цена товара: " + (sumPrice/itemCount));

        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
