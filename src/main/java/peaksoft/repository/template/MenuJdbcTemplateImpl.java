package peaksoft.repository.template;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import peaksoft.dto.MenuItemResponse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MenuJdbcTemplateImpl implements MenuJdbcTemplate{

    private final JdbcTemplate jdbcTemplate;

    private MenuItemResponse rowMapper(ResultSet rs, int rowNum) throws SQLException {
        return new MenuItemResponse(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("image"),
                rs.getBigDecimal("price"),
                rs.getString("description"),
                rs.getBoolean("isVegetarian")
        );
    }

    @Override
    public List<MenuItemResponse> getAll() {
        String sql = """
                select id,
                name as name,
                image as image,
                price as price,
                description as description,
                is_vegetarian as isVegetarian
                from menu_items
                """;
        return jdbcTemplate.query(sql,this::rowMapper);
    }

    @Override
    public Optional<MenuItemResponse> findById(Long menuId) {
        String sql = """
                select id,
                name as name,
                image as image,
                price as price,
                description as description,
                is_vegetarian as isVegetarian
                from menu_items where id=?
                """;
        return jdbcTemplate.query(sql,this::rowMapper,menuId).stream().findFirst();
    }

}
