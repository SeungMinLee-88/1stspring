package com.spring.board.controller;

import com.spring.board.dto.BoardDTO;
import com.spring.board.dto.ReserveDTO;
import com.spring.board.dto.TimeDto;
import com.spring.board.entity.ReserveEntity;
import com.spring.board.entity.TimeEntity;
import com.spring.board.service.ReserveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reserve")
public class ReserveController {

    private final ReserveService reserveService;

    @PutMapping("/save")
    public ResponseEntity<ReserveEntity> reserveSave(@RequestBody ReserveDTO reserveDTO) throws IOException {
        System.out.println("reserveDTO = " + reserveDTO);
        /*List<TimeDto> timeDtoList = reserveDTO.getReserveTime();*/
        ReserveDTO reserveDTO1 = reserveService.save(reserveDTO);
        return ResponseEntity.ok(ReserveEntity.builder().id(reserveDTO1.getId()).build());
    }
//@GetMapping(value = "/product/{productId}")
//@GetMapping("/request1")
//    public String getRequestParam1(
//            @RequestParam String name,
//            @RequestParam String email,
//            @RequestParam String organization
    @GetMapping("/reservelist")
    public List<ReserveDTO> reserveList(@RequestParam Map<String, String> params) {
        /*StringBuilder sb = new StringBuilder();*/
        params.entrySet().forEach(map -> {
            /*sb.append(map.getKey() + " : " + map.getValue() + "\n");*/
            System.out.println("map : " + map.getKey() + " : " + map.getValue() + "\n");
        });

        ReserveDTO reserveDTO = new ReserveDTO();
        reserveDTO.setReserveDate(params.get("reserveDate"));
        System.out.println("reserveList reserveDTO : " + reserveDTO.toString());
        // DB에서 전체 게시글 데이터를 가져와서 list.html에 보여준다.
        List<ReserveDTO> reserveDTOList = reserveService.reserveList(reserveDTO);
        System.out.println("reserveDTOList : " + reserveDTOList.toString());
        //model.addAttribute("boardList", boardDTOList);
        return reserveDTOList;
    }


/*    @DeleteMapping("/deletetime")
    public ResponseEntity<TimeDto> deletetime(@RequestBody TimeDto timeDto) throws IOException {
        System.out.println("reserveDTO = " + timeDto);
        reserveService.deletetime(timeDto);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(timeDto);
    }*/

}
