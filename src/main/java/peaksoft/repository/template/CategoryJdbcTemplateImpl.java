package peaksoft.repository.template;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import peaksoft.dto.CategoryResponse;
import peaksoft.dto.RestaurantResponse;
import peaksoft.enums.CuisineType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor

public class CategoryJdbcTemplateImpl implements CategoryJdbcTemplate {
    private final JdbcTemplate jdbcTemplate;

    private CategoryResponse rowMapper(ResultSet rs, int rowNum) throws SQLException {
        return new CategoryResponse(
                rs.getLong("id"),
                rs.getString("name")
        );
    }
    @Override
    public List<CategoryResponse> getAllCategories() {
        String sql = """
               select id,
               name as name 
               from categories 
               """;
        return jdbcTemplate.query(sql,this::rowMapper);
    }

    @Override
    public Optional<CategoryResponse> findCategoryById(Long catId) {
       String sql = """
               select id,
               name as name
               from categories
               where id=?
               """;
        return jdbcTemplate.query(sql,this::rowMapper,catId).stream().findFirst();
    }
}
