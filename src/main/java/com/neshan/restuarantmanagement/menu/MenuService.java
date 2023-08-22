package com.neshan.restuarantmanagement.menu;

import java.util.List;

public interface MenuService {

    List<Menu> getAllMenus();

    Menu getMenuById(int id);

    Menu createMenu(Menu menu);

    Menu updateMenu(int id, Menu menu);

    void deleteMenu(int id);

}
