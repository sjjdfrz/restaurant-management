package com.neshan.restaurantmanagement.mapper;

import com.neshan.restaurantmanagement.model.dto.CategoriesDto;
import com.neshan.restaurantmanagement.model.dto.CategoryDto;
import com.neshan.restaurantmanagement.model.entity.Category;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface CategoryMapper {

    CategoriesDto categoryToCategoriesDto(Category category);

    CategoryDto categoryToCategoryDto(Category category);

    Category categoriesDtoToCategory(CategoriesDto categoriesDto);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCategoryFromDto(CategoriesDto dto, @MappingTarget Category category);
}
