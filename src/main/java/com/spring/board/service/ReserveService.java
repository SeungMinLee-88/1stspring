package com.spring.board.service;


import com.spring.board.dto.ReserveDTO;
import com.spring.board.dto.ReserveTimeDTO;
import com.spring.board.dto.TimeDto;
import com.spring.board.entity.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public interface ReserveService {


    ReserveDTO reserveSave(ReserveDTO reserveDTO) throws IOException;

    ReserveDTO updateReserve(ReserveDTO reserveDTO);

    List<ReserveDTO> reserveList(ReserveDTO reserveDTO);

    ReserveDTO reserveDetail(Long id);

    List<ReserveTimeDTO> reserveTimeList(ReserveTimeDTO reserveTimeDTO);

    List<TimeDto> timeList(Map<String, String> params);

    ReserveDTO convertToDto(ReserveEntity reserveEntity);

    void deleteReserve(ReserveDTO reserveDTO) throws IOException;

}
