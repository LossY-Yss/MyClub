package com.mdkj.mapper;

import com.mdkj.dto.school.SchoolPageDTO;
import com.mdkj.entity.School;
import com.mdkj.vo.SchoolVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface SchoolMapper {
    //查询--全部记录
    @Select("select * from rms_school where deleted = 0")
    List<SchoolVo> selectSchoolAll();

    //查询--单条记录
    @Select("select * from rms_school where deleted = 0 and id = #{id}")
    School select(Long id);

    //查询--条件分页
    List<School> list(SchoolPageDTO dto);

    //添加--单条
    @Insert("insert into rms_school values(null,#{title},#{college},#{major},#{clazz},#{address},#{info},#{version},#{deleted},#{created},#{updated})")
    int insert(School school);

    //删除--单条
    @Update("update rms_school set deleted = 1, updated = current_timestamp where id = #{id}")
    int delete(Long id);

    //删除--多条
    int deleteBatch(@Param("list") List<Long> ids);

    //修改--单条
    Integer update(School school);
}
