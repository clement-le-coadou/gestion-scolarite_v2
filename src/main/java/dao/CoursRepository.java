package dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.yourcompany.yourprojectname.model.Cours;

import java.util.List;

@Repository
public interface CoursRepository extends JpaRepository<Cours, Long> {

    // The following methods are automatically implemented by Spring Data JPA:

    // Create - this is done automatically with `save()`
    // public Cours save(Cours cours);

    // Read - finding by ID
    Cours findCoursById(Long id);

    // List all
    List<Cours> findAll();

    // Update - this is done automatically with `save()` as it merges entities
    // public Cours save(Cours cours);

    // Delete
    void deleteById(Long id);
}
