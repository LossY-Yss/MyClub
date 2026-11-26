package com.mdkj.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mdkj.dto.room.RoomExcel;
import com.mdkj.dto.room.RoomInsertDTO;
import com.mdkj.dto.room.RoomPageDTO;
import com.mdkj.dto.room.RoomUpdateDTO;
import com.mdkj.entity.Room;
import com.mdkj.exception.ServerErrorException;
import com.mdkj.mapper.RoomMapper;
import com.mdkj.service.RoomService;
import com.mdkj.vo.RoomVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomMapper roomMapper;

    @Override
    public List<RoomVo> selectRoomAll() {
        return roomMapper.selectRoomAll();
    }

    //@Cacheable(key = "#p0")
    @Override
    public Room select(Long id) {
        return roomMapper.select(id);
    }

    @Override
    public PageInfo<Room> page(RoomPageDTO dto) {
        //开启分页
        PageHelper.startPage(dto.getPageNum(),dto.getPageSize());
        //执行查询
        List<Room> list = roomMapper.list(dto);
        return new PageInfo<>(list);
    }

    @Override
    public int insert(RoomInsertDTO dto) {
        //将dto的属性拷贝 生成一个room对象
        Room room = BeanUtil.copyProperties(dto, Room.class);
        //设置默认值
        room.setVersion(0L);
        room.setDeleted(0);
        room.setCreated(LocalDateTime.now());
        room.setUpdated(LocalDateTime.now());
        return roomMapper.insert(room);
    }

    @Override
    public int delete(Long id) {
        return roomMapper.delete(id);
    }

    @Override
    public int deleteBatch(List<Long> ids) {
        return roomMapper.deleteBatch(ids);
    }

    @Override
    public int update(RoomUpdateDTO dto) {
        //根据id查询要修改的数据是否存在
        Room room = roomMapper.select(dto.getId());
        if(ObjectUtil.isNull(room)){
            throw  new ServerErrorException("记录不存在");
        }
        //将dto（要修改的数据）的属性拷贝到 room（当前的数据）实现属性覆盖
        BeanUtil.copyProperties(dto,room);
        //设置默认值
        room.setUpdated(LocalDateTime.now());
        //执行mapper修改
        return roomMapper.update(room);
    }

    @Override
    public List<RoomExcel> getExcelData() {
        List<Room> list = roomMapper.list(new RoomPageDTO());
        List<RoomExcel> roomExcelList = list.stream().map(room -> BeanUtil.copyProperties(room, RoomExcel.class)).collect(Collectors.toList());
        return roomExcelList;
    }
}
