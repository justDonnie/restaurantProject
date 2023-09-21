package peaksoft.service.impl;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.StopListRequest;
import peaksoft.dto.StopListResponse;
import peaksoft.enums.Role;
import peaksoft.exceptions.AccessDeniedException;
import peaksoft.exceptions.BadCredentialException;
import peaksoft.exceptions.BadRequestException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.models.MenuItem;
import peaksoft.models.StopList;
import peaksoft.models.User;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.StopListRepository;
import peaksoft.repository.UserRepository;
import peaksoft.repository.template.StopListJdbcTemplate;
import peaksoft.service.StopListService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class StopListServiceImpl implements StopListService {
    private final MenuItemRepository menuItemRepository;
    private final StopListRepository stopListRepository;
    private final StopListJdbcTemplate stopListJdbcTemplate;
    private final UserRepository userRepository;


    @Override
    public SimpleResponse createList(Long menuId, StopListRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("Authentication required to create a stop lists !!!");
        }
        String email = authentication.getName();
        User user = userRepository.getUserByEmail(email)
                .orElseThrow(() -> new BadCredentialException("There are no any Users with email: " + email + " !!!"));
        if (user.getRole().equals(Role.WAITER)) {
            throw new AccessDeniedException("Authentication required to be ADMIN/CHEF to create a stop list !!!");
        }
        if (request.date().isBefore(LocalDate.now())) {
            throw new BadRequestException("The date cannot be in the past !!!");
        }
        MenuItem menuItem = menuItemRepository.findById(menuId)
                .orElseThrow(() -> new NotFoundException("The menu item with the specified ID was not found !!!"));
        if (stopListRepository.existsByDateAndMenuItem(request.date(), menuItem)) {
            throw new BadRequestException("The stop list already exists for the specified date and for the current meal !!!");
        }
        StopList stopList = new StopList(request.reason(), request.date());
        menuItem.setIsBlocked(stopList.getDate());
        stopList.setMenuItem(menuItem);
        stopListRepository.save(stopList);
        return new SimpleResponse(
                HttpStatus.OK,
                "Stop list is created !!!"
        );
    }

    @Override
    public List<StopListResponse> allStopLists() {
        return stopListJdbcTemplate.getAllStopLists();
    }

    @Override
    public Optional<StopListResponse> findById(Long listId) {
        return stopListJdbcTemplate.findById(listId);
    }

    @Override
    public SimpleResponse updateList(Long listId, StopListRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("Authentication required to update a stop lists !!!");
        }
        String email = authentication.getName();
        User user = userRepository.getUserByEmail(email)
                .orElseThrow(() -> new BadCredentialException("There are no any Users with email: " + email + " !!!"));
        if (user.getRole().equals(Role.WAITER)) {
            throw new AccessDeniedException("Authentication required to be ADMIN/CHEF to update a stop list !!!");
        }
        if (listId != null) {
            StopList stopList = stopListRepository.findById(listId).orElseThrow(() ->
                    new NotFoundException(String.format("StopList with ID :%s already exists", listId)));
            stopList.setReason(request.reason());
            stopList.setDate(request.date());
            stopListRepository.save(stopList);
            return new SimpleResponse(
                    HttpStatus.OK,
                    " Stop List is successfully updated !!!"
            );
        }
        return null;
    }

    @Override
    public SimpleResponse deleteList(Long listId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("Authentication required to delete a stop lists !!!");
        }
        String email = authentication.getName();
        User user = userRepository.getUserByEmail(email)
                .orElseThrow(() -> new BadCredentialException("There are no any Users with email: " + email + " !!!"));
        if (user.getRole().equals(Role.WAITER)) {
            throw new AccessDeniedException("Authentication required to be ADMIN/CHEF to delete a stop list !!!");
        }
        StopList stopList = stopListRepository.findById(listId)
                .orElseThrow(() -> new NotFoundException(" There are no any stop lists in database!!!"));
        stopList.getMenuItem().setIsBlocked(null);
        menuItemRepository.save(stopList.getMenuItem());
        stopListRepository.deleteById(listId);
        log.info(" Stop List is successfully deleted !!!");
        return new SimpleResponse(
                HttpStatus.OK,
                "Stop List is successfully deleted !!!"
        );
    }
}
