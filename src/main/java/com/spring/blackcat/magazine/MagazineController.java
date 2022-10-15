package com.spring.blackcat.magazine;

import com.spring.blackcat.common.Response;
import com.spring.blackcat.magazine.dto.CellDto;
import com.spring.blackcat.magazine.dto.MagazineTitleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// TODO: 2022-10-10 POST Magazine
@RestController
@RequiredArgsConstructor
public class MagazineController {

    private final MagazineService magazineService;

    @GetMapping("/magazines")
    public ResponseEntity getAllMagazines() {
        List<MagazineTitleDto> magazineTitles = magazineService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(Response.builder().status(HttpStatus.OK.getReasonPhrase()).data(magazineTitles).code(HttpStatus.OK.value()).build());
    }


    @GetMapping("/magazines/{magazineId}")
    public ResponseEntity getSpecificMagazine(@PathVariable Long magazineId) {
        List<CellDto> cells = magazineService.getMagazineCells(magazineId);
        return ResponseEntity.status(HttpStatus.OK).body(Response.builder().status(HttpStatus.OK.getReasonPhrase()).data(cells).code(HttpStatus.OK.value()).build());
    }

    @PostMapping
    public ResponseEntity registerMagazine() {
        return ResponseEntity.of(null);
    }
}
