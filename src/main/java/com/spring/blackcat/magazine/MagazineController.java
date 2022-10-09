package com.spring.blackcat.magazine;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// TODO: 2022-10-10 ResponseEntity Customize
// TODO: 2022-10-10 POST Magazine 
@RestController
@RequiredArgsConstructor
public class MagazineController {

    private final MagazineService magazineService;

    @GetMapping("/magazines")
    public ResponseEntity getAllMagazines() {
        List<MagazineTitleDto> magazineTitles = magazineService.findAll();
        return ResponseEntity.status(200).body(magazineTitles);
    }


    @GetMapping("/magazines/{magazineId}")
    public ResponseEntity getSpecificMagazine(@PathVariable Long magazineId) {
        List<CellDto> cells = magazineService.getMagazineCells(magazineId);
        return ResponseEntity.ok().body(cells);
    }

    @PostMapping
    public ResponseEntity registerMagazine() {
        return ResponseEntity.of(null);
    }
}
