//package com.neshan.restaurantmanagement.runner;
//
//import com.neshan.restaurantmanagement.model.entity.Restaurant;
//import com.neshan.restaurantmanagement.repository.RestaurantRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@AllArgsConstructor
//@Component
//public class RestaurantRunner implements CommandLineRunner {
//
//    private RestaurantRepository restaurantRepository;
//
//    @Override
//    public void run(String... args) {
//
//        Restaurant restaurant1 = Restaurant
//                .builder()
//                .name("پسران کریم خیام")
//                .address("استان خراسان رضوی، مشهد، بلوار خیام، بین خیام ۶۱ و خیام ۶۳، ساختمان مروارید")
//                .telephone(5131919)
//                .build();
//
//        Restaurant restaurant2 = Restaurant
//                .builder()
//                .name("کلبه")
//                .address("استان خراسان رضوی، مشهد، نبش هاشمیه ۱۸، رستوران کلبه")
//                .telephone(5138831855L)
//                .build();
//
//        Restaurant restaurant3 = Restaurant
//                .builder()
//                .name("راستگو")
//                .address("استان خراسان رضوی، مشهد، بلوار وکیل آباد، بین وکیل آباد ۲۳ و ۲۵")
//                .telephone(9151679399L)
//                .build();
//
//        restaurantRepository.saveAll(List.of(restaurant1, restaurant2, restaurant3));
//    }
//}
