package com.neshan.restaurantmanagement.runner;

import com.neshan.restaurantmanagement.model.entity.Item;
import com.neshan.restaurantmanagement.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class ItemRunner implements CommandLineRunner {

    private ItemRepository itemRepository;

    @Override
    public void run(String... args) {

        Item item1 = Item
                .builder()
                .name("پیتزا مخصوص")
                .price(157500)
                .description("سس گوجه، ژامبون گوشت و مرغ، رست بیف، قارچ، پنیر پروسس، فلفل دلمه، ذرت، زیتون، سس ۲ عدد رایگان")
                .build();

        Item item2 = Item
                .builder()
                .name("پیتزا چیکن آلفردو")
                .price(162000)
                .description("سس آلفردو، فیله مرغ گریل، قارچ، پنیر گودا، پنیر پروسس، فلفل دلمه، ذرت، زیتونسس ۲ عدد رایگان")
                .build();

        Item item3 = Item
                .builder()
                .name("برگر ذغالی")
                .price(99000)
                .description("برگر دستی ۹۰% ۱۵۰ گرم، سس باربیکیو، گوجه، کاهو، خیارشور، نان گرد، سس ۲ عدد رایگان")
                .build();

        Item item4 = Item
                .builder()
                .name("چیز برگر")
                .price(117000)
                .description("برگر دستی ۹۰% ۱۵۰ گرم، پنیر گودا، پنیر پروسس، گوجه، کاهو، خیارشور، نان گرد سس ۲ عدد رایگان")
                .build();

        Item item5 = Item
                .builder()
                .name("پاستا چیکن آلفردو")
                .price(179000)
                .description("پاستا پنه، سس آلفردو")
                .build();

        Item item6 = Item
                .builder()
                .name("سینی دورهمی ویژه")
                .price(1020000)
                .description("۴۰۰ گرم گوشت گوسفندی، یک سیخ کباب لقمه ۱۵۰ گرمی، ۴۰۰ تا ۴۵۰ گرم جوجه کباب با استخوان (ران)، ۴۰۰ گرم ماهی قزل آلا سرخ شده، ۲۵۰ تا ۳۰۰ گرم خورشت قورمه سبزی، ۲.۵ پرس برنج ایرانی ۳۵۰ گرمی، دورچین: سیب زمینی سرخ کرده، لیمو، کلم قرمز، هویج، خیارشور")
                .build();

        Item item7 = Item
                .builder()
                .name("چلو کباب سلطانی")
                .price(440000)
                .description("ک سیخ کباب برگ گوشت راسته گوسفندی ۱۸۰ گرمی، یک سیخ کباب لقمه مخلوط گوشت گوساله و گوسفندی ۱۵۰ گرمی، ۳۵۰ گرم برنج ایرانی، دورچین: کره، لیمو، کلم قرمز، هویج، خیارشور")
                .build();

        Item item8 = Item
                .builder()
                .name("چلو کباب کوبیده")
                .price(235000)
                .description("۲ سیخ کباب کوبیده مخلوط گوشت گوساله و گوسفندی ۲۰۰ گرمی، ۳۵۰ گرم برنج ایرانی، دورچین: کره، لیمو، کلم قرمز، هویج، خیارشور")
                .build();

        Item item9 = Item
                .builder()
                .name("چلو ماهیچه مخصوص")
                .price(510000)
                .description("۵۵۰ گرم ماهیچه گوسفندی، ۳۵۰ گرم برنج ایرانی، دورچین: پیاز سرخ کرده، سیر، فلفل دلمه ای، خیارشور، کره")
                .build();

        Item item10 = Item
                .builder()
                .name("چلو ماهی قزل آلا کبابی")
                .price(275000)
                .description("۳۰۰ گرم ماهی قزل آلا کبابی، ۳۵۰ گرم برنج ایرانی، دورچین: لیمو، خیارشور")
                .build();

        Item item11 = Item
                .builder()
                .name("چلو خورشت قورمه سبزی")
                .price(140000)
                .description("۲۵۰ تا ۳۰۰ گرم خورشت قورمه سبزی، ۳۰ گرم گوشت گوسفندی، ۳۵۰ گرم برنج ایرانی")
                .build();

        Item item12 = Item
                .builder()
                .name("ماست خامه ای موسیر")
                .price(10000)
                .description("یک نفره")
                .build();

        Item item13 = Item
                .builder()
                .name("ماست ساده")
                .price(4000)
                .description("یک نفره")
                .build();

        Item item14 = Item
                .builder()
                .name("خیارشور")
                .price(15000)
                .description("یک نفره")
                .build();

        Item item15 = Item
                .builder()
                .name("زیتون پرورده")
                .price(16000)
                .description("یک نفره")
                .build();

        Item item16 = Item
                .builder()
                .name("سیب زمینی سرخ کرده")
                .price(35000)
                .description("یک نفره، سیب زمینی سرخ کرده خلالی")
                .build();

        Item item17 = Item
                .builder()
                .name("سالاد فصل")
                .price(15000)
                .description("کاهو، خیار، گوجه، کلم قرمز، سس مایونز")
                .build();

        Item item18 = Item
                .builder()
                .name("نوشابه بطری کوکا کولا")
                .price(11000)
                .description("۳۰۰ میلی لیتر")
                .build();

        Item item19 = Item
                .builder()
                .name("نوشابه بطری فانتا")
                .price(11000)
                .description("۳۰۰ میلی لیتر")
                .build();

        Item item20 = Item
                .builder()
                .name("نوشابه خانواده کوکا کولا")
                .price(29500)
                .description("۱.۵ لیتر")
                .build();

        Item item21 = Item
                .builder()
                .name("دوغ بطری خوشگوار")
                .price(10000)
                .description("۳۰۰ میلی لیتر")
                .build();

        Item item22 = Item
                .builder()
                .name("آب معدنی کوچک")
                .price(5000)
                .description("۰.۵ لیتر")
                .build();

        itemRepository.saveAll(List.of(
                item1, item2, item3,
                item4, item5, item6,
                item7, item8, item9,
                item10, item11, item12,
                item13, item14, item15,
                item16, item17, item18,
                item19, item20, item21, item22));
    }
}
