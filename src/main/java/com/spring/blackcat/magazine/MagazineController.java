package com.spring.blackcat.magazine;

import com.spring.blackcat.common.response.ResponseDto;
import com.spring.blackcat.common.response.ResponseUtil;
import com.spring.blackcat.magazine.dto.CellDto;
import com.spring.blackcat.magazine.dto.MagazineTitleReqDto;
import com.spring.blackcat.magazine.dto.MagazineTitleResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MagazineController {

    private final MagazineService magazineService;

    @GetMapping("/magazines")
    public ResponseDto getAllMagazines(@PageableDefault(page = 0, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<MagazineTitleResDto> magazineTitles = magazineService.findAll(pageable);
        return ResponseUtil.SUCCESS("모든 매거진 조회 성공", magazineTitles);
    }


    @GetMapping("/magazines/{magazineId}")
    public ResponseDto getSpecificMagazine(@PathVariable Long magazineId) {
        List<CellDto> cells = magazineService.getMagazineCells(magazineId);
        return ResponseUtil.SUCCESS("매거진 상세 조회 성공", cells);
    }

    @PostMapping("/magazines")
    public ResponseDto postMagazine(@RequestBody MagazineTitleReqDto reqDto) {

        MagazineTitleResDto resDto = magazineService.postMagazine(reqDto);

        return ResponseUtil.SUCCESS("매거진 등록 성공", resDto);
    }
}
