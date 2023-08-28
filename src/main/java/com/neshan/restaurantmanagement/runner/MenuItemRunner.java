package com.neshan.restaurantmanagement.runner;

import com.neshan.restaurantmanagement.model.entity.MenuItem;
import com.neshan.restaurantmanagement.repository.MenuItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class MenuItemRunner implements CommandLineRunner {

    private MenuItemRepository menuItemRepository;

    @Override
    public void run(String... args) {

        MenuItem menuItem1 = MenuItem
                .builder()
                .name("پیتزا مخصوص")
                .price(157500)
                .category("پیتزا")
                .description("سس گوجه، ژامبون گوشت و مرغ، رست بیف، قارچ، پنیر پروسس، فلفل دلمه، ذرت، زیتون، سس ۲ عدد رایگان")
                .build();

        MenuItem menuItem2 = MenuItem
                .builder()
                .name("پیتزا چیکن آلفردو")
                .price(162000)
                .category("پیتزا")
                .description("سس آلفردو، فیله مرغ گریل، قارچ، پنیر گودا، پنیر پروسس، فلفل دلمه، ذرت، زیتونسس ۲ عدد رایگان")
                .build();

        MenuItem menuItem3 = MenuItem
                .builder()
                .name("برگر ذغالی")
                .price(99000)
                .category("برگر")
                .description("برگر دستی ۹۰% ۱۵۰ گرم، سس باربیکیو، گوجه، کاهو، خیارشور، نان گرد، سس ۲ عدد رایگان")
                .build();

        MenuItem menuItem4 = MenuItem
                .builder()
                .name("چیز برگر")
                .price(117000)
                .category("برگر")
                .description("برگر دستی ۹۰% ۱۵۰ گرم، پنیر گودا، پنیر پروسس، گوجه، کاهو، خیارشور، نان گرد سس ۲ عدد رایگان")
                .build();

        MenuItem menuItem5 = MenuItem
                .builder()
                .name("پاستا چیکن آلفردو")
                .price(179000)
                .category("پاستا")
                .description("پاستا پنه، سس آلفردو")
                .build();

        MenuItem menuItem6 = MenuItem
                .builder()
                .name("سینی دورهمی ویژه")
                .price(1020000)
                .category("سینی")
                .description("۴۰۰ گرم گوشت گوسفندی، یک سیخ کباب لقمه ۱۵۰ گرمی، ۴۰۰ تا ۴۵۰ گرم جوجه کباب با استخوان (ران)، ۴۰۰ گرم ماهی قزل آلا سرخ شده، ۲۵۰ تا ۳۰۰ گرم خورشت قورمه سبزی، ۲.۵ پرس برنج ایرانی ۳۵۰ گرمی، دورچین: سیب زمینی سرخ کرده، لیمو، کلم قرمز، هویج، خیارشور")
                .build();

        MenuItem menuItem7 = MenuItem
                .builder()
                .name("چلو کباب سلطانی")
                .price(440000)
                .category("چلو کباب")
                .description("ک سیخ کباب برگ گوشت راسته گوسفندی ۱۸۰ گرمی، یک سیخ کباب لقمه مخلوط گوشت گوساله و گوسفندی ۱۵۰ گرمی، ۳۵۰ گرم برنج ایرانی، دورچین: کره، لیمو، کلم قرمز، هویج، خیارشور")
                .build();

        MenuItem menuItem8 = MenuItem
                .builder()
                .name("چلو کباب کوبیده")
                .price(235000)
                .category("چلو کباب")
                .description("۲ سیخ کباب کوبیده مخلوط گوشت گوساله و گوسفندی ۲۰۰ گرمی، ۳۵۰ گرم برنج ایرانی، دورچین: کره، لیمو، کلم قرمز، هویج، خیارشور")
                .build();

        MenuItem menuItem9 = MenuItem
                .builder()
                .name("چلو ماهیچه مخصوص")
                .price(510000)
                .category("غذای ایرانی")
                .description("۵۵۰ گرم ماهیچه گوسفندی، ۳۵۰ گرم برنج ایرانی، دورچین: پیاز سرخ کرده، سیر، فلفل دلمه ای، خیارشور، کره")
                .build();

        MenuItem menuItem10 = MenuItem
                .builder()
                .name("چلو ماهی قزل آلا کبابی")
                .price(275000)
                .category("غذای ایرانی")
                .description("۳۰۰ گرم ماهی قزل آلا کبابی، ۳۵۰ گرم برنج ایرانی، دورچین: لیمو، خیارشور")
                .build();

        MenuItem menuItem11 = MenuItem
                .builder()
                .name("چلو خورشت قورمه سبزی")
                .price(140000)
                .category("خورشت")
                .description("۲۵۰ تا ۳۰۰ گرم خورشت قورمه سبزی، ۳۰ گرم گوشت گوسفندی، ۳۵۰ گرم برنج ایرانی")
                .build();

        MenuItem menuItem12 = MenuItem
                .builder()
                .name("ماست خامه ای موسیر")
                .price(10000)
                .category("پیش غذا")
                .description("یک نفره")
                .build();

        MenuItem menuItem13 = MenuItem
                .builder()
                .name("ماست ساده")
                .price(4000)
                .category("پیش غذا")
                .description("یک نفره")
                .build();

        MenuItem menuItem14 = MenuItem
                .builder()
                .name("خیارشور")
                .price(15000)
                .category("پیش غذا")
                .description("یک نفره")
                .build();

        MenuItem menuItem15 = MenuItem
                .builder()
                .name("زیتون پرورده")
                .price(16000)
                .category("پیش غذا")
                .description("یک نفره")
                .build();

        MenuItem menuItem16 = MenuItem
                .builder()
                .name("سیب زمینی سرخ کرده")
                .price(35000)
                .category("پیش غذا")
                .description("یک نفره، سیب زمینی سرخ کرده خلالی")
                .build();

        MenuItem menuItem17 = MenuItem
                .builder()
                .name("سالاد فصل")
                .price(15000)
                .category("پیش غذا")
                .description("کاهو، خیار، گوجه، کلم قرمز، سس مایونز")
                .build();

        MenuItem menuItem18 = MenuItem
                .builder()
                .name("نوشابه بطری کوکا کولا")
                .price(11000)
                .category("نوشیدنی")
                .description("۳۰۰ میلی لیتر")
                .build();

        MenuItem menuItem19 = MenuItem
                .builder()
                .name("نوشابه بطری فانتا")
                .price(11000)
                .category("نوشیدنی")
                .description("۳۰۰ میلی لیتر")
                .build();

        MenuItem menuItem20 = MenuItem
                .builder()
                .name("نوشابه خانواده کوکا کولا")
                .price(29500)
                .category("نوشیدنی")
                .description("۱.۵ لیتر")
                .build();

        MenuItem menuItem21 = MenuItem
                .builder()
                .name("دوغ بطری خوشگوار")
                .price(10000)
                .category("نوشیدنی")
                .description("۳۰۰ میلی لیتر")
                .build();

        MenuItem menuItem22 = MenuItem
                .builder()
                .name("آب معدنی کوچک")
                .price(5000)
                .category("نوشیدنی")
                .description("۰.۵ لیتر")
                .build();

        menuItemRepository.saveAll(List.of(
                menuItem1, menuItem2, menuItem3,
                menuItem4, menuItem5, menuItem6,
                menuItem7, menuItem8, menuItem9,
                menuItem10, menuItem11, menuItem12,
                menuItem13, menuItem14, menuItem15,
                menuItem16, menuItem17, menuItem18,
                menuItem19, menuItem20, menuItem21, menuItem22));
    }
}
