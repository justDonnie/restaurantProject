package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.models.SubCategory;
@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {

    @Query("select case when count(s) > 0 then true else false end from SubCategory s where lower(s.name) = lower(:name)")
    boolean existsByNameIgnoreCase(String name);

}