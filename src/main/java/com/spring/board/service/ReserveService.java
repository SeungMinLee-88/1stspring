package com.spring.board.service;

import com.spring.board.dto.BoardDTO;
import com.spring.board.dto.ReserveDTO;
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
import java.util.Optional;

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

            List<ReserveEntity> timeDtoList = reserveDTO.getReserveTime();
            for(int i = 0; i < timeDtoList.size(); i++) {
                System.out.println("timeDtoList : " + timeDtoList.get(i));
                TimeEntity timeEntity = timeRepository.findById(timeDtoList.get(i)).get();
                ReserveTimeEntity reserveTimeEntity = ReserveTimeEntity.toSaveEntity(reserveEntity, timeEntity);
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
        List<ReserveEntity> reserveEntityList = reserveRepository.findByreserveDateQuery(reserveDTO.getReserveDate());
        //List<ReserveDTO> reserveDTOList = new ArrayList<>();
        for(int i = 0; i < reserveEntityList.size(); i++) {
            System.out.println("reserveEntityList : " + reserveEntityList.get(i).toString());

        }
/*
        Page<BoardDTO> boardDTOList = boardEntities.map(board -> new BoardDTO(board.getId(), board.getBoardWriter(), board.getBoardTitle(), board.getBoardHits(), board.getCreatedTime()));*/

        ModelMapper mapper = new ModelMapper();
        List<ReserveDTO> reserveDTOList  = mapper.map(reserveEntityList, new TypeToken<List<ReserveDTO>>(){}.getType());
        return reserveDTOList;
    }

    public void deletetime(TimeDto timeDto) throws IOException {
        //timeRepository.deleteById(timeDto.getId());
    }
}
