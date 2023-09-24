package peaksoft.repository.template;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import peaksoft.dto.RestaurantResponse;
import peaksoft.enums.CuisineType;
import peaksoft.models.Restaurant;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor

public class RestJdbcTemplateImpl implements RestJdbcTemplate{

    private final JdbcTemplate jdbcTemplate;

    private RestaurantResponse rowMapper(ResultSet rs, int rowNum) throws SQLException {
        return new RestaurantResponse(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("location"),
                CuisineType.valueOf(rs.getString("restType")),
                rs.getInt("numberOfEmployees"),
                rs.getInt("services")
        );
    }


    @Override
    public List<RestaurantResponse> getAllRestaurants() {
        String sql = """
                select id,
                name as name,
                location as location,
                rest_type as restType,
                number_of_employees as numberOfEmployees,
                services as services
                from restaurants
                """;
        return jdbcTemplate.query(sql,this::rowMapper);
    }

    @Override
    public Optional<RestaurantResponse> findRestaurantById(Long restId) {
        String sql = """
                select id,
                name as name,
                location as location,
                rest_type as restType,
                number_of_employees as numberOfEmployees,
                services as services
                from restaurants where id=?
                """;
        return jdbcTemplate.query(sql,this::rowMapper,restId).stream().findFirst();
    }
}
