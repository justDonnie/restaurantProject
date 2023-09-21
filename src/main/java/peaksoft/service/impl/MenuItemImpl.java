package peaksoft.service.impl;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import peaksoft.dto.MenuItemRequest;
import peaksoft.dto.MenuItemResponse;
import peaksoft.dto.SimpleResponse;
import peaksoft.enums.Role;
import peaksoft.exceptions.AccessDeniedException;
import peaksoft.exceptions.BadCredentialException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.models.MenuItem;
import peaksoft.models.Restaurant;
import peaksoft.models.SubCategory;
import peaksoft.models.User;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.SubCategoryRepository;
import peaksoft.repository.UserRepository;
import peaksoft.repository.template.MenuJdbcTemplate;
import peaksoft.service.MenuItemService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MenuItemImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final MenuJdbcTemplate menuJdbcTemplate;
    private final RestaurantRepository restaurantRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final UserRepository userRepository;


    @Override
    public SimpleResponse saveMenu(MenuItemRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("Authentication required to create a MENU !!!");
        }
        String email = authentication.getName();
        User user = userRepository.getUserByEmail(email)
                .orElseThrow(() -> new BadCredentialException("There are no any Users with email: " + email + " !!!"));
        if (user.getRole().equals(Role.WAITER)) {
            throw new AccessDeniedException("Authentication required to be ADMIN/CHEF to create a MENU !!!");
        }
        Restaurant restaurant = null;
        for (Restaurant restaurants : restaurantRepository.findAll()) {
            restaurant = restaurants;
        }
        SubCategory subCategory = subCategoryRepository.findById(request.subCategoryId())
                .orElseThrow(() -> new NotFoundException("There are no any subcategories in database!!!"));
        MenuItem menuItem = new MenuItem();
        menuItem.setName(request.name());
        menuItem.setImage(request.image());
        menuItem.setPrice(request.price());
        menuItem.setDescription(request.description());
        menuItem.setVegetarian(request.isVegetarian());
        menuItem.setSubCategory(subCategory);
        menuItem.setRestaurant(restaurant);
        menuItemRepository.save(menuItem);
        return new SimpleResponse(
                HttpStatus.OK,
                "MenuItem is successfully created !!!"
        );
    }

    @Override
    public List<MenuItemResponse> getAllMenu() {
        return menuJdbcTemplate.getAll();
    }

    @Override
    public Optional<MenuItemResponse> findMenuById(Long menuId) {
        return menuJdbcTemplate.findById(menuId);
    }

    @Override
    public SimpleResponse updateMenu(Long menuId, MenuItemRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("Authentication required to update a MENU !!!");
        }
        String email = authentication.getName();
        User user = userRepository.getUserByEmail(email)
                .orElseThrow(() -> new BadCredentialException("There are no any Users with email: " + email + " !!!"));
        if (user.getRole().equals(Role.WAITER)) {
            throw new AccessDeniedException("Authentication required to be ADMIN/CHEF to update a MENU !!!");
        }
        if (request.name().isBlank() &&
                request.name().isBlank() &&
                request.price() == null &&
                request.description().isBlank() &&
                request.isVegetarian() == null) {
            throw new BadCredentialException("Invalid commands !!!");
        } else {
            SubCategory subCategory = subCategoryRepository.findById(request.subCategoryId())
                    .orElseThrow(() -> new NotFoundException("There are no any subcategories in database!!!"));
            MenuItem menuItem = menuItemRepository.findById(menuId).orElseThrow(() -> new NotFoundException("There are no any dishes on this ID: " + menuId));
            menuItem.setName(request.name());
            menuItem.setImage(request.image());
            menuItem.setPrice(request.price());
            menuItem.setDescription(request.description());
            menuItem.setVegetarian(request.isVegetarian());
            menuItem.setSubCategory(subCategory);
            menuItemRepository.save(menuItem);
            log.info("Dish with ID: "+menuId+" is successfully updated!!!");
            return new SimpleResponse(
                    HttpStatus.OK,
                    "Dish with ID: "+menuId+" is successfully updated!!!"
            );
        }
    }

    @Override
    public SimpleResponse deleteMenu(Long menuId) {
        MenuItem menuItem =menuItemRepository.findById(menuId).orElseThrow(
                () -> {
                    String massage = "Menu with id: " + menuId + " is not found!";
                    log.error(massage);
                    return  new NotFoundException(massage);
                });
        menuItemRepository.delete(menuItem);
        return new SimpleResponse(
                HttpStatus.OK,
                "Menu is successfully deleted !!!"
        );
    }

    @Override
    public List<MenuItemResponse> sortedByPrice(String ascOrDesc) {
        if (ascOrDesc==null){
            throw new BadCredentialException("Invalid commands !!!");
        }else{
            if (ascOrDesc.equalsIgnoreCase("asc")){
                List<MenuItemResponse>menuItemResponses = menuJdbcTemplate.getAll();
                menuItemRepository.sortedPriceAsc();
                return menuItemResponses;
            } else if (ascOrDesc.equalsIgnoreCase("desc")) {
                List<MenuItemResponse>menuItemResponses = menuJdbcTemplate.getAll();
                menuItemRepository.sortedPriceDesc();
                return menuItemResponses;
            }
        }
        return null;
    }

    @Override
    public List<MenuItemResponse> isVegetarian(Boolean veganOrNot) {
        if (veganOrNot.equals(true) || veganOrNot.equals(false)) {
            if (veganOrNot.equals(true)) {
                List<MenuItemResponse> menuItemResponses = menuJdbcTemplate.getAll();
                menuItemRepository.isTrue();
                return menuItemResponses;
            } else if (veganOrNot.equals(false)) {
                List<MenuItemResponse> menuItemResponses = menuJdbcTemplate.getAll();
                menuItemRepository.isFalse();
                return menuItemResponses;
            }
        } else {
            throw new BadCredentialException(" Incorrect command! Put the correct commands!!!");
        }
        return null;
    }
}
