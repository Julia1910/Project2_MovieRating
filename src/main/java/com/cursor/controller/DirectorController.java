package com.cursor.controller;

import com.cursor.dto.DirectorDto;
import com.cursor.service.interfaces.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DirectorController {

    private final DirectorService directorService;

    @Autowired
    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @GetMapping(value = "/directors", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DirectorDto>> getAll() {
        return new ResponseEntity<>(directorService.getAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/directors/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DirectorDto> getById(
            @PathVariable(value = "id") Long id) {
        DirectorDto directorDto = directorService.getById(id);
        if (directorDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(directorDto, HttpStatus.OK);
    }

    @PostMapping(value = "/directors", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DirectorDto> add(@RequestBody DirectorDto directorDto) {
        DirectorDto director = directorService.add(directorDto);
        return new ResponseEntity<>(director, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/directors/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DirectorDto> remove(
            @PathVariable(value = "id") Long id
    ) {
        directorService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
