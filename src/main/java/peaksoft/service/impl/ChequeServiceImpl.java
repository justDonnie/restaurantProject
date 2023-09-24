package peaksoft.service.impl;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import peaksoft.dto.ChequeRequest;
import peaksoft.dto.ChequeResponse;
import peaksoft.dto.SimpleResponse;
import peaksoft.exceptions.BadCredentialException;
import peaksoft.exceptions.BadRequestException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.models.Cheque;
import peaksoft.models.MenuItem;
import peaksoft.models.User;
import peaksoft.repository.ChequeRepository;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.ChequeService;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ChequeServiceImpl implements ChequeService {
    private final ChequeRepository chequeRepository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;


    @Override
    public List<ChequeResponse> getAll() {
        List<ChequeResponse> all = chequeRepository.getAll();
        log.info("All list of cheques !!!");
        return all;
    }

    @Override
    public SimpleResponse save(ChequeRequest chequeRequest) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.getUserByEmail(email).orElseThrow(() ->
                new NotFoundException("User with email:" + email + " not found!!!"));
        if (user.getRole().name().equalsIgnoreCase("ADMIN") ||
                user.getRole().name().equalsIgnoreCase("WAITER")) {
            Cheque cheque = new Cheque();
            cheque.setCreatedAt(ZonedDateTime.now());
            List<MenuItem>menuItems=new ArrayList<>();
            for (String menuItemName : chequeRequest.getMenuItemNames()) {
                if (menuItemRepository.existsByName(menuItemName)){
                    menuItems.add(menuItemRepository.findByName(menuItemName));
                }throw new BadRequestException("MenuItem with name :"+menuItemName+" not found !!");
            }
            BigDecimal averagePrice=BigDecimal.ZERO;
            for (MenuItem menuItem : menuItems) {
                if (menuItem!=null){
                    averagePrice=averagePrice.add(menuItem.getPrice());
                }
            }
            cheque.setMenuItems(menuItems);
            cheque.setPriceAverage(averagePrice);
            user.setCheques(List.of(cheque));
            cheque.setUser(user);
            userRepository.save(user);
            chequeRepository.save(cheque);
            log.info("Cheque is created !!!");
            return SimpleResponse.builder()
                    .httpStatus(OK)
                    .message("Cheque with id:")
                    .build();
        }else
            throw new BadCredentialException("Cheque must be created by ADMIN/WAITER !!!");
    }

}
