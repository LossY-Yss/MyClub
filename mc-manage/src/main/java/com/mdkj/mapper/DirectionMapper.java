package com.mdkj.mapper;

import com.github.pagehelper.PageInfo;
import com.mdkj.dto.Direction.DirectionInsertDTO;
import com.mdkj.dto.Direction.DirectionPageDTO;
import com.mdkj.entity.Direction;
import com.mdkj.vo.DirectionVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DirectionMapper {

    @Select("""
        <script>
            select * from cms_direction
            <where>
                <if test='title != null'>title like concat('%', #{title}, '%') and</if>
                deleted = 0
            </where>
        </script>
    """)
    List<Direction> list(DirectionPageDTO dto);

    @Select("select * from cms_direction where id = #{id} and deleted = 0")
    Direction select(Long id);

    @Insert("""
        insert into cms_direction values (null, #{title}, #{info}, #{version}, #{deleted}, #{created}, #{updated})
    """)
    int insert(Direction direction);

    @Update("""
        <srcipt>
            update cms_direction set 
            <if test = 'title != null'>title like concat('%', #{title}, '%'),</if>
            <if test = 'info != null'>info like concat('%', #{info}, '%'),</if>
            version = version + 1
            where id = #{id} and deleted = 0 and version = #{version}
        </script>
    """)
    int update(Direction direction);

    @Update("update cms_direction set deleted = 1 where id = #{id} and deleted = 0")
    int delete(Long id);

    @Update("""
    <script>
        update cms_direction set deleted = 1 where id in 
        <foreach collection='list' item='e' open='(' separator=',' close=')'>
            #{e}
        </foreach>
    </script>
    """)
    int deleteBatch(@Param("list") List<Long> list);
}
