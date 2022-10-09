package com.spring.blackcat.magazine;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/magazines")
@RequiredArgsConstructor
public class MagazineController {

    private final MagazineRepository magazineRepository;

    @GetMapping
    public ResponseEntity getAllMagazines(){
        return ResponseEntity.of(null);
    }

    @GetMapping("/{magazineId}")
    public ResponseEntity getSpecificMagazine(@PathVariable Long magazineId){
        return ResponseEntity.of(null);
    }

    @PostMapping
    public ResponseEntity registerMagazine(){
        return ResponseEntity.of(null);
    }
}
