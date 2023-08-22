package com.neshan.restuarantmanagement.menu;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MenuServiceImpl implements MenuService{

    private MenuRepository menuRepository;

    @Override
    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    @Override
    public Menu getMenuById(int id) {
        Optional<Menu> result = menuRepository.findById(id);
        if(result.isPresent())
            return result.get();
        else
            throw new RuntimeException("not found");
    }

    @Override
    public Menu createMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    @Override
    public Menu updateMenu(int id, Menu menuRequest) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));

        menu.setTitle(menuRequest.getTitle());
        menu.setDescription(menuRequest.getDescription());
        return menuRepository.save(menu);
    }

    @Override
    public void deleteMenu(int id) {

        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));

        menuRepository.delete(menu);
    }
}
