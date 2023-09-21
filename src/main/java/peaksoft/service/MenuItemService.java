package peaksoft.service;

import peaksoft.dto.*;

import java.util.List;
import java.util.Optional;

public interface MenuItemService {

    SimpleResponse saveMenu(MenuItemRequest request);

    List<MenuItemResponse> getAllMenu();

    Optional<MenuItemResponse> findMenuById(Long menuId);

    SimpleResponse updateMenu(Long menuId, MenuItemRequest request);

    SimpleResponse deleteMenu(Long menuId);

    List<MenuItemResponse>sortedByPrice(String ascOrDesc);

    List<MenuItemResponse>isVegetarian(Boolean veganOrNot);

}
