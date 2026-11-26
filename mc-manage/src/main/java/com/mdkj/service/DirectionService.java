package com.mdkj.service;

import com.github.pagehelper.PageInfo;
import com.mdkj.dto.Direction.DirectionExcel;
import com.mdkj.dto.Direction.DirectionInsertDTO;
import com.mdkj.dto.Direction.DirectionPageDTO;
import com.mdkj.dto.Direction.DirectionUpdateDTO;
import com.mdkj.entity.Direction;
import com.mdkj.vo.DirectionVO;
import java.util.List;

public interface DirectionService {

    List<DirectionVO> list();

    PageInfo<Direction> page(DirectionPageDTO dto);

    Direction select(Long id);

    int insert(DirectionInsertDTO dto);

    int update(DirectionUpdateDTO dto);

    int delete(Long id);

    int deleteBatch(List<Long> list);

    List<DirectionExcel> export();
}
