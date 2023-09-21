package peaksoft.repository.template;

import peaksoft.dto.MenuItemResponse;

import java.util.List;
import java.util.Optional;

public interface MenuJdbcTemplate {

    List<MenuItemResponse> getAll();

    Optional<MenuItemResponse>findById(Long menuId);

}
