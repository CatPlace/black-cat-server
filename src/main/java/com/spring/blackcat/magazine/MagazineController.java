package com.spring.blackcat.magazine;

import com.spring.blackcat.common.Response;
import com.spring.blackcat.magazine.dto.CellDto;
import com.spring.blackcat.magazine.dto.MagazineTitleReqDto;
import com.spring.blackcat.magazine.dto.MagazineTitleResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MagazineController {

    private final MagazineService magazineService;

    @GetMapping("/magazines")
    public ResponseEntity getAllMagazines(@PageableDefault(page = 0, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<MagazineTitleResDto> magazineTitles = magazineService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(Response.builder().status(HttpStatus.OK.getReasonPhrase()).data(magazineTitles).code(HttpStatus.OK.value()).build());
    }


    @GetMapping("/magazines/{magazineId}")
    public ResponseEntity getSpecificMagazine(@PathVariable Long magazineId) {
        List<CellDto> cells = magazineService.getMagazineCells(magazineId);
        return ResponseEntity.status(HttpStatus.OK).body(Response.builder().status(HttpStatus.OK.getReasonPhrase()).data(cells).code(HttpStatus.OK.value()).build());
    }

    @PostMapping("/magazines")
    public ResponseEntity postMagazine(@RequestBody MagazineTitleReqDto reqDto) {

        MagazineTitleResDto resDto = magazineService.postMagazine(reqDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(Response.builder().status(HttpStatus.CREATED.getReasonPhrase()).data(resDto).code(HttpStatus.CREATED.value()).build());
    }
}
