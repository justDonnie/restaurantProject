package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.dto.ChequeResponse;
import peaksoft.models.Cheque;

import java.util.List;

@Repository
public interface ChequeRepository extends JpaRepository<Cheque, Long> {

    @Query("select new peaksoft.dto.ChequeResponse (c.id,c.priceAverage,c.createdAt)from Cheque  c")
    List<ChequeResponse>getAll();



}