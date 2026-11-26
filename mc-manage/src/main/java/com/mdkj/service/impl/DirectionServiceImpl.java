package com.mdkj.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mdkj.dto.Direction.DirectionExcel;
import com.mdkj.dto.Direction.DirectionInsertDTO;
import com.mdkj.dto.Direction.DirectionPageDTO;
import com.mdkj.dto.Direction.DirectionUpdateDTO;
import com.mdkj.entity.Direction;
import com.mdkj.mapper.DirectionMapper;
import com.mdkj.service.DirectionService;
import com.mdkj.vo.DirectionVO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DirectionServiceImpl implements DirectionService {

    @Autowired
    private DirectionMapper directionMapper;

    @Override
    public List<DirectionVO> list() {
        return directionMapper.list(new DirectionPageDTO())
                .stream().map(direction ->BeanUtil.copyProperties(direction, DirectionVO.class)).collect(Collectors.toList());
    }

    @Override
    public PageInfo<Direction> page(DirectionPageDTO dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        return new PageInfo<>(directionMapper.list(dto));
    }

    @Override
    public Direction select(Long id) {
        return directionMapper.select(id);
    }

    @Override
    public int insert(DirectionInsertDTO dto) {
        Direction direction = BeanUtil.copyProperties(dto, Direction.class);
        direction.setVersion(0L);
        direction.setDeleted(0);
        direction.setCreated(LocalDateTime.now());
        direction.setUpdated(LocalDateTime.now());
        return directionMapper.insert(direction);
    }

    @Override
    public int update(DirectionUpdateDTO dto) {
        Direction direction = BeanUtil.copyProperties(dto, Direction.class);
        direction.setUpdated(LocalDateTime.now());
        return directionMapper.update(direction);
    }

    @Override
    public int delete(Long id) {
        return directionMapper.delete(id);
    }

    @Override
    public int deleteBatch(List<Long> list) {
        return directionMapper.deleteBatch(list);
    }

    @Override
    public List<DirectionExcel> export() {
        List<Direction> directions = directionMapper.list(new DirectionPageDTO());
        List<DirectionExcel> directionExcels = directions.stream().map(direction -> BeanUtil.copyProperties(direction, DirectionExcel.class)).collect(Collectors.toList());
        return directionExcels;
    }


}
