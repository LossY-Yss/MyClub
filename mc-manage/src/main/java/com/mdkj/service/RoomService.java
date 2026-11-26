package com.mdkj.service;

import com.github.pagehelper.PageInfo;
import com.mdkj.dto.room.RoomExcel;
import com.mdkj.dto.room.RoomInsertDTO;
import com.mdkj.dto.room.RoomPageDTO;
import com.mdkj.dto.room.RoomUpdateDTO;
import com.mdkj.entity.Room;
import com.mdkj.vo.RoomVo;

import java.util.List;

public interface RoomService {
    //查询--全部
    //查询--全部
    List<RoomVo> selectRoomAll();

    //查询--单条
    Room select(Long id);

    //查询--条件分页
    PageInfo<Room> page(RoomPageDTO dto);

    //添加--单条
    int insert(RoomInsertDTO dto);

    //删除--单条
    int delete(Long id);

    //删除--多条
    int deleteBatch(List<Long> ids);

    //修改--单条
    int update(RoomUpdateDTO dto);

    //获取房间的Excel数据
    List<RoomExcel> getExcelData();
}
