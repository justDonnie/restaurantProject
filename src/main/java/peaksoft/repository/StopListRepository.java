package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import peaksoft.models.MenuItem;
import peaksoft.models.StopList;

import java.time.LocalDate;

@Repository
public interface StopListRepository extends JpaRepository<StopList, Long> {

    boolean existsByDateAndMenuItem(LocalDate date, MenuItem menuItem);
}