package auca.ac.rw.auca.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import auca.ac.rw.auca.management.model.AcademicUnit;
import auca.ac.rw.auca.management.model.EAcademicUnitType;
import auca.ac.rw.auca.management.service.AcademicUnitService;

@RestController
@RequestMapping(value = "/academic")
public class AcademicUnitController {

    @Autowired
    private AcademicUnitService service;

    @PostMapping(value = "/saveAcademic", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveAcademicUnit(@RequestParam(required = false) String parentCode,
            @RequestBody AcademicUnit unit) {
        if (parentCode == null) {
            unit.setAcademicType(EAcademicUnitType.PROGRAMME);
            String saveAcademicUnit = service.saveACademicUnitParent(unit);
            if (saveAcademicUnit.equalsIgnoreCase("Academic Unit already exists")) {
                return new ResponseEntity<>(saveAcademicUnit, HttpStatus.CONFLICT);
            } else {
                return new ResponseEntity<>(saveAcademicUnit, HttpStatus.OK);
            }
        } else {
            if (unit.getAcademicType().toString().equalsIgnoreCase("FACULTY")) {
                unit.setAcademicType(EAcademicUnitType.FACULTY);
            } else {
                unit.setAcademicType(EAcademicUnitType.DEPARTMENT);
            }

            String saveFacultyOrDepartment = service.saveAcademicUnit(unit, parentCode);
            if (saveFacultyOrDepartment.equalsIgnoreCase(
                    "That Faculty or Department already exists")) {
                return new ResponseEntity<>(saveFacultyOrDepartment, HttpStatus.CONFLICT);
            } else if (saveFacultyOrDepartment.equalsIgnoreCase(
                    "Academic Unit saved successfully")) {
                return new ResponseEntity<>(saveFacultyOrDepartment, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(saveFacultyOrDepartment, HttpStatus.NOT_FOUND);
            }

        }

    }

}
