package project3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project3.models.Measurement;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
    @Query("SELECT COUNT(m) FROM Measurement m WHERE m.raining = true")
    int countRainyDays();
}
