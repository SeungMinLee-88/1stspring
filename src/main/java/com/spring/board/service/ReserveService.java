package com.spring.board.service;

import com.spring.board.dto.BoardDTO;
import com.spring.board.dto.ReserveDTO;
import com.spring.board.dto.ReserveTimeDTO;
import com.spring.board.dto.TimeDto;
import com.spring.board.entity.*;
import com.spring.board.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReserveService {
    private final ReserveRepository reserveRepository;
    private final UserRepository userRepository;
    private final HallRepository hallRepository;
    private final TimeRepository timeRepository;
    private final ReserveTimeRepository reserveTimeRepository;

    public ReserveDTO save(ReserveDTO reserveDTO) throws IOException {
        // 파일 첨부 여부에 따라 로직 분리
        /*if (boardDTO.getBoardFile().isEmpty()) {*/

        Optional<UserEntity> optionalUserEntity = userRepository.findById(reserveDTO.getUserId());
        Optional<HallEntity> optionalHallEntity = hallRepository.findById(reserveDTO.getHallId());
        if (optionalUserEntity.isPresent() && optionalHallEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();
            HallEntity hallEntity = optionalHallEntity.get();
            ReserveEntity reserveEntity = ReserveEntity.toSaveEntity(reserveDTO, userEntity, hallEntity);
            ReserveEntity reserveEntitys = reserveRepository.save(reserveEntity);

            //List<ReserveTimeDTO> timeDtoList = reserveDTO.getReserveTime();
            for(int i = 0; i < reserveDTO.getReserveTimeSave().size(); i++) {
                System.out.println("timeDtoList : " + reserveDTO.getReserveTimeSave().get(i));
                TimeEntity timeEntity = timeRepository.findById(reserveDTO.getReserveTimeSave() .get(i)).get();
                ReserveTimeEntity reserveTimeEntity = ReserveTimeEntity.toSaveEntity(reserveEntity, timeEntity,reserveDTO);
                ReserveTimeEntity reserveTimeEntitys = reserveTimeRepository.save(reserveTimeEntity);
            }
            ModelMapper mapper = new ModelMapper();

            System.out.println("reserveEntitys : " + reserveEntitys.toString());
            ReserveDTO reserveDTO1  = mapper.map(reserveEntitys, new TypeToken<ReserveDTO>(){}.getType());

            return reserveDTO1;

        } else {
            return null;
        }
    }


    @Transactional
    public List<ReserveDTO> reserveList(ReserveDTO reserveDTO) {
        List<ReserveEntity> reserveEntityList = reserveRepository.findByReserveDateContaining(reserveDTO.getReserveDate());

        for(int i = 0; i < reserveEntityList.size(); i++) {
            System.out.println("reserveEntityList : " + reserveEntityList.get(i).getReserveTimeEntity().toString());
        }

        ModelMapper mapper = new ModelMapper();
        List<ReserveDTO> reserveDTOList  = mapper.map(reserveEntityList, new TypeToken<List<ReserveDTO>>(){}.getType());
        for(int i = 0; i < reserveDTOList.size(); i++) {
            System.out.println("getReserveTime : " + mapper.map(reserveEntityList.get(i).getReserveTimeEntity(), new TypeToken<List<ReserveTimeDTO>>(){}.getType()));
        }

        List<ReserveDTO> reserveDTOList2 = mapper.map(reserveEntityList, new TypeToken<List<ReserveDTO>>(){}.getType());

        return reserveDTOList2;
    }

    @Transactional
    public ReserveDTO reserveList(Long id) {
        ModelMapper mapper = new ModelMapper();
        Optional<ReserveEntity> optionalReserveEntity = reserveRepository.findById(id);
        if (optionalReserveEntity.isPresent()) {
            ReserveEntity reserveEntity = optionalReserveEntity.get();
            ReserveDTO reserveDTO = mapper.map(reserveEntity, new TypeToken<ReserveDTO>(){}.getType());
            return reserveDTO;
        } else {
            return null;
        }
    }


    @Transactional
    public List<ReserveTimeDTO> reserveTimeList(ReserveTimeDTO reserveTimeDTO) {
        List<ReserveTimeEntity> reserveTimeEntityList = reserveTimeRepository.findByReserveDate(reserveTimeDTO.getReserveDate());
        ModelMapper mapper = new ModelMapper();
        List<ReserveTimeDTO> reserveDTOList2 = mapper.map(reserveTimeEntityList, new TypeToken<List<ReserveTimeDTO>>(){}.getType());
        return reserveDTOList2;
    }

    @Transactional
    public List<TimeDto> timeList(Map<String, String> params) {
        System.out.println("params.get : " + params.get("reserveDate"));
        List<TimeEntity> timeEntityList = timeRepository.findByReserveDate(params.get("reserveDate"));
        ModelMapper mapper = new ModelMapper();
        List<TimeDto> timeDtoList = mapper.map(timeEntityList, new TypeToken<List<TimeDto>>(){}.getType());
        return timeDtoList;
    }

    /* for(int i = 0; i < reserveDTOList.size(); i++) {

            System.out.println("getReserveTime : " + mapper.map(reserveEntityList.get(i).getReserveTimeEntity(), new TypeToken<ReserveTimeDTO>(){}.getType()));
            System.out.println("getReserveReason : " + mapper.map(reserveEntityList.get(i).getReserveReason(), new TypeToken<String>(){}.getType()));

            reserveTimeDTO.setId(mapper.map(reserveEntityList.get(i).getId(), new TypeToken<Long>(){}.getType()));
            reserveTimeDTO.setReserveDate(mapper.map(reserveEntityList.get(i).getReserveDate(), new TypeToken<String>(){}.getType()));
            reserveTimeDTO.setReserveReason(mapper.map(reserveEntityList.get(i).getReserveReason(), new TypeToken<String>(){}.getType()));
            reserveTimeDTO.setReservePeriod(mapper.map(reserveEntityList.get(i).getReservePeriod(), new TypeToken<String>(){}.getType()));
            reserveTimeDTO.setReserveReason(mapper.map(reserveEntityList.get(i).getReserveReason(), new TypeToken<String>(){}.getType()));
            reserveTimeDTO.setUserId(mapper.map(reserveEntityList.get(i).getReservePeriod(), new TypeToken<Long>(){}.getType()));
            reserveTimeDTO.setHallId(mapper.map(reserveEntityList.get(i).getReservePeriod(), new TypeToken<Long>(){}.getType()));
            reserveTimeDTO.setReserveTime(mapper.map(reserveEntityList.get(i).getReserveTimeEntity(), new TypeToken<List<ReserveTimeDTO>>(){}.getType()));

            reserveDTOListAdd.add(i, reserveTimeDTO);
        }
        System.out.println("reserveDTOListAdd : " + reserveDTOListAdd);*/

    private ReserveDTO convertToDto(ReserveEntity reserveEntity) {
        ModelMapper mapper = new ModelMapper();
        ReserveDTO reserveDTO = mapper.map(reserveEntity, ReserveDTO.class);
        return reserveDTO;
    }

    public void deleteReserve(ReserveDTO reserveDTO) throws IOException {
        reserveRepository.deleteById(reserveDTO.getId());
    }

    public void deletetime(TimeDto timeDto) throws IOException {
        //timeRepository.deleteById(timeDto.getId());
    }
}
