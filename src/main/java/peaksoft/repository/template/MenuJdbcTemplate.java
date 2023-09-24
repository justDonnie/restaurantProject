package peaksoft.repository.template;

import org.springframework.stereotype.Repository;
import peaksoft.dto.MenuItemResponse;

import java.util.List;
import java.util.Optional;
@Repository
public interface MenuJdbcTemplate {

    List<MenuItemResponse> getAll();

    Optional<MenuItemResponse>findById(Long menuId);

}
