package peaksoft.repository.template;

import org.springframework.stereotype.Repository;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.UserRequest;
import peaksoft.dto.UserResponse;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserJdbcTemplate {

    List<UserResponse>getAllUser();

    Optional<UserResponse> findUserById(Long id);

}
