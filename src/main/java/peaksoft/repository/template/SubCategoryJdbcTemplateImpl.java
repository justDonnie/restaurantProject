package peaksoft.repository.template;


import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import peaksoft.dto.CategoryResponse;
import peaksoft.dto.SubCategoryResponse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SubCategoryJdbcTemplateImpl implements SubCategoryJdbcTemplate{

    private final JdbcTemplate jdbcTemplate;

    private SubCategoryResponse rowMapper(ResultSet rs, int rowNum) throws SQLException {
        return new SubCategoryResponse(
                rs.getLong("id"),
                rs.getString("name")
        );
    }

    @Override
    public List<SubCategoryResponse> getAllSubCategory() {
        String sql = """
                select id,
                name as name
                from sub_categories
                """;
        return jdbcTemplate.query(sql,this::rowMapper);
    }

    @Override
    public Optional<SubCategoryResponse> findById(Long subCatId) {
        String sql = """
                select id,
                name as name
                from sub_categories 
                where id=?
                """;
        return jdbcTemplate.query(sql,this::rowMapper,subCatId).stream().findFirst();
    }
}
