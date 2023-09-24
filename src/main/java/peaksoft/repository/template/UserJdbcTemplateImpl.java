package peaksoft.repository.template;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.UserRequest;
import peaksoft.dto.UserResponse;
import peaksoft.enums.Role;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor

public class UserJdbcTemplateImpl implements UserJdbcTemplate {
    private final JdbcTemplate jdbcTemplate;

    private UserResponse rowMapper(ResultSet rs, int rowNum) throws SQLException {
        Long id = rs.getLong("id");
        String fullName = rs.getString("fullName");
        LocalDate dateOfBirth = null;
        String dateOfBirthStr = rs.getString("dateOfBirth");
        if (dateOfBirthStr != null && !dateOfBirthStr.isEmpty()) {
            dateOfBirth = LocalDate.parse(dateOfBirthStr);
        }
        String email = rs.getString("email");
        String password = rs.getString("password");
        String phoneNumber = rs.getString("phoneNumber");
        Role role = Role.valueOf(rs.getString("role"));
        int experience = rs.getInt("experience");

        return new UserResponse(id, fullName, dateOfBirth, email, password, phoneNumber, role, experience);
    }


    @Override
    public List<UserResponse> getAllUser() {
        String sql = """
                select
                id,
                concat(first_name,' ',last_name) as fullName,
                date_of_birth as dateOfBirth,
                email as email,
                password as password,
                phone_number as phoneNumber,
                role as role,
                experience as experience
                from users
                """;
        return jdbcTemplate.query(sql,this::rowMapper);
    }

    @Override
    public Optional<UserResponse> findUserById(Long id) {
        String sql = """
                select id,
                concat(first_name,' ',last_name) as fullName,
                date_of_birth as dateOfBirth,
                email as email,
                password as password,
                phone_number as phoneNumber,
                role as role,
                experience as experience
                from users where id=?
                """;
        return jdbcTemplate.query(sql,this::rowMapper,id).stream().findFirst();
    }

}
