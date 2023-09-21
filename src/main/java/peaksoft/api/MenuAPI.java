package peaksoft.api;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.MenuItemRequest;
import peaksoft.dto.MenuItemResponse;
import peaksoft.dto.SimpleResponse;
import peaksoft.service.MenuItemService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menuItems")
@Tag(name = "MenuItem API")
public class MenuAPI {
    private final MenuItemService menuItemService;

    @PermitAll
    @GetMapping
    List<MenuItemResponse> getAllMenu() {
        return menuItemService.getAllMenu();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PostMapping
    SimpleResponse saveMenu(@RequestBody MenuItemRequest menuItemRequest) {
        return menuItemService.saveMenu(menuItemRequest);
    }


    @PermitAll
    @GetMapping("/byId/{menuId}")
    Optional<MenuItemResponse> findMenuById(@PathVariable Long menuId) {
        return menuItemService.findMenuById(menuId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PutMapping("/update/{menuId}")
    SimpleResponse updateMenu(@PathVariable Long menuId,
                              @RequestBody MenuItemRequest request) {
        return menuItemService.updateMenu(menuId, request);
    }


    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @DeleteMapping("/delete/{menuId}")
    SimpleResponse deleteMenuItem(@PathVariable Long menuId) {
        return menuItemService.deleteMenu(menuId);
    }

    @PermitAll
    @GetMapping("/sorted")
    List<MenuItemResponse>sortedByPrice(@RequestParam String ascOrDesc){
        return menuItemService.sortedByPrice(ascOrDesc);
    }

    @PermitAll
    @GetMapping("/isVegan")
    List<MenuItemResponse>isVegan(@RequestParam Boolean veganOrNonVegan){
        return menuItemService.isVegetarian(veganOrNonVegan);
    }

}
