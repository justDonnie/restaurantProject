package peaksoft.repository.template;

import peaksoft.dto.StopListResponse;

import java.util.List;
import java.util.Optional;

public interface StopListJdbcTemplate {

    List<StopListResponse> getAllStopLists();

    Optional<StopListResponse>findById(Long listId);

}
