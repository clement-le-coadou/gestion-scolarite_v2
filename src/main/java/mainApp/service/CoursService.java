package mainApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mainApp.dao.CoursRepository;
import mainApp.model.Cours;

import java.util.List;

@Service
public class CoursService {

    @Autowired
    private CoursRepository coursRepository;

    @Autowired
    public CoursService(CoursRepository coursRepository) {
        this.coursRepository = coursRepository;
    }
    
    public void createCours(Cours cours) {
        coursRepository.save(cours);
    }

    public Cours findCoursById(Long id) {
        return coursRepository.findCoursById(id);
    }

    public List<Cours> findAllCours() {
        return coursRepository.findAll();
    }

    public void updateCours(Cours cours) {
        coursRepository.save(cours); // `save` will merge the entity if it already exists
    }

    public void deleteCours(Long id) {
        coursRepository.deleteById(id);
    }

    public List<Cours> findByEnseignantId(Long enseignantId) {
        return coursRepository.findByEnseignantId(enseignantId);
    }
}
