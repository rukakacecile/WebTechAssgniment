package auca.ac.rw.auca.management.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.ac.rw.auca.management.model.AcademicUnit;
import auca.ac.rw.auca.management.repository.IAcademicUnitRepository;

@Service
public class AcademicUnitService {

    @Autowired
    private IAcademicUnitRepository repository;

    public String saveACademicUnitParent(AcademicUnit unit) {
        Boolean checkAcademic = repository.existsByAcademicCode(unit.getAcademicCode());
        if (checkAcademic) {
            return "Academic Unit already exists";
        } else {
            repository.save(unit);
            return "Academic Unit saved successfully";
        }
    }

    public String saveAcademicUnit(AcademicUnit unit, String parentCode) {
        Optional<AcademicUnit> getParent = repository.findByAcademicCode(parentCode);
        if (getParent.isPresent()) {
            if (repository.existsByAcademicCode(unit.getAcademicCode())) {
                return "That Faculty or Department already exists";
            } else {
                unit.setAcademicUnit(getParent.get());
                repository.save(unit);
                return "Academic Unit saved successfully";
            }

        } else {
            return "Parent with that code is not exist";
        }

    }

}
