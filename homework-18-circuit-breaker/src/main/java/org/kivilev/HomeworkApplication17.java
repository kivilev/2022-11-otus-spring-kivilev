package org.kivilev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HomeworkApplication17 {
    public static void main(String[] args) throws InterruptedException {
        var context = SpringApplication.run(HomeworkApplication17.class, args);
       /* var service = context.getBean(CurrencyRateService.class);
        System.out.println(service.getActualCurrencies());*/
    }
}
