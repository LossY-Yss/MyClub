package com.mdkj.mapper;

import com.mdkj.dto.room.RoomPageDTO;
import com.mdkj.entity.Room;
import com.mdkj.vo.RoomVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface RoomMapper {

    //查询--全部
    @Select("select * from rms_room where deleted = 0")
    List<RoomVo> selectRoomAll();

    //查询--单条
    @Select("select * from rms_room where id = #{id} and deleted = 0")
    Room select(Long id);

    //查询--条件分页
    List<Room> list(RoomPageDTO dto);

    //添加--单条
    @Insert("insert into rms_room values(null,#{title},#{address},#{info},#{capacity},#{version},#{deleted},#{created},#{updated})")
    int insert(Room room);

    //删除--单条
    @Update("update rms_room set deleted = 1, updated = current_timestamp where id = #{id}")
    int delete(Long id);

    //删除--多条
    int deleteBatch(@Param("list") List<Long> ids);

    //修改--单条
    int update(Room room);
}
