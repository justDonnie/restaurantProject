package peaksoft.repository.template;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import peaksoft.dto.StopListResponse;
import peaksoft.dto.SubCategoryResponse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Repository
@RequiredArgsConstructor

public class StopListJdbcTemplateImpl implements StopListJdbcTemplate{

    private final JdbcTemplate jdbcTemplate;

    private StopListResponse rowMapper(ResultSet rs, int rowNum) throws SQLException {
        return new StopListResponse(
                rs.getLong("id"),
                rs.getString("reason"),
                LocalDate.parse(rs.getString("date"))
        );
    }

    @Override
    public List<StopListResponse> getAllStopLists() {
        String sql = """
                select id,
                reason as reason,
                date as date
                from stop_lists
                """;
        return jdbcTemplate.query(sql,this::rowMapper);
    }

    @Override
    public Optional<StopListResponse> findById(Long listId) {
        String sql = """
                select id,
                reason as reason,
                date as date
                from stop_lists where id=?
                """;
        return jdbcTemplate.query(sql,this::rowMapper,listId).stream().findFirst();
    }
}
