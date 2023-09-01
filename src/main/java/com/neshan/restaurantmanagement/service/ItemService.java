package com.neshan.restaurantmanagement.service;

import com.neshan.restaurantmanagement.exception.NoSuchElementFoundException;
import com.neshan.restaurantmanagement.mapper.ItemMapper;
import com.neshan.restaurantmanagement.model.dto.ItemDto;
import com.neshan.restaurantmanagement.model.entity.Category;
import com.neshan.restaurantmanagement.model.entity.Item;
import com.neshan.restaurantmanagement.repository.ItemRepository;
import com.neshan.restaurantmanagement.repository.CategoryRepository;
import com.neshan.restaurantmanagement.util.PaginationSorting;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ItemService {

    private ItemRepository itemRepository;
    private CategoryRepository categoryRepository;
    private ItemMapper itemMapper;
    private CategoryService categoryService;

    public List<ItemDto> getAllItems(int pageNo, int pageSize, String sortBy) {

        List<Sort.Order> orders = PaginationSorting.getOrders(sortBy);
        Pageable paging = PaginationSorting.getPaging(pageNo, pageSize, orders);

        return itemRepository
                .findAll(paging)
                .map(item -> itemMapper.itemToItemDto(item))
                .getContent();
    }

    public ItemDto getItem(long id) {

        Item item = itemRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The item with ID %d was not found.", id)));

        return itemMapper.itemToItemDto(item);
    }

    public List<ItemDto> getAllItemsOfCategory(long categoryId) {

        return categoryService
                .getCategory(categoryId)
                .items();
    }

    public void createItem(long categoryId, ItemDto itemDto) {

        Category category = categoryService.getCategoryEntityById(categoryId);

        Item item = itemMapper.itemDtoToItem(itemDto);
        category.getItems().add(item);
        categoryRepository.save(category);
    }

    public void updateItem(long id, ItemDto itemRequest) {

        Item item = itemRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The item with ID %d was not found.", id)));

        itemMapper.updateItemFromDto(itemRequest, item);
        itemRepository.save(item);
    }

    public void deleteItem(long id) {
        itemRepository.deleteById(id);
    }

    public void deleteAllItems() {
        itemRepository.deleteAll();
    }

    public void deleteAllItemsOfCategory(long categoryId) {

        Category category = categoryService.getCategoryEntityById(categoryId);

        category.getItems().clear();
        categoryRepository.save(category);
    }
}
