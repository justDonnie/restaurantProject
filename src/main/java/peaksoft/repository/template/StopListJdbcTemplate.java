package peaksoft.repository.template;

import org.springframework.stereotype.Repository;
import peaksoft.dto.StopListResponse;

import java.util.List;
import java.util.Optional;

@Repository
public interface StopListJdbcTemplate {

    List<StopListResponse> getAllStopLists();

    Optional<StopListResponse>findById(Long listId);

}
