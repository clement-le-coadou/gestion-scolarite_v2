package mainApp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mainApp.model.Cours;

import java.util.List;

@Repository
public interface CoursRepository extends JpaRepository<Cours, Long> {

    // Read - finding by ID
    Cours findCoursById(Long id);

    // List all
    List<Cours> findAll();

    // Delete
    void deleteById(Long id);
}
