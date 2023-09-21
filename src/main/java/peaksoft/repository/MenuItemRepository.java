package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.dto.MenuItemResponse;
import peaksoft.models.MenuItem;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    @Query("select new peaksoft.dto.MenuItemResponse(m.id,m.name,m.image,m.price,m.description,m.isVegetarian)from MenuItem m order by m.price asc")
    List<MenuItemResponse>sortedPriceAsc();

    @Query("select new peaksoft.dto.MenuItemResponse(m.id,m.name,m.image,m.price,m.description,m.isVegetarian)from MenuItem m order by m.price desc")
    List<MenuItemResponse>sortedPriceDesc();


    @Query("select new peaksoft.dto.MenuItemResponse(m.id,m.name,m.image,m.price,m.description,m.isVegetarian)from MenuItem m order by true")
    List<MenuItemResponse> isTrue();

    @Query("select new peaksoft.dto.MenuItemResponse(m.id,m.name,m.image,m.price,m.description,m.isVegetarian)from MenuItem m order by false")
    List<MenuItemResponse>isFalse();

}