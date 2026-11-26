package com.mdkj.mapper;

import com.mdkj.dto.club.ClubPageDTO;
import com.mdkj.entity.Club;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ClubMapper {

    @Results(id = "clubMap", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "fkDirectionId", column = "fk_direction_id"),
            @Result(property = "direction", column = "fk_direction_id",
                    one = @One(select = "com.mdkj.mapper.DirectionMapper.select"))
    })
    @Select("""
            <script>
            select * from cms_club t
            <where>
                <if test='title != null'> title like concat('%', #{title}, '%') and </if>
                t.deleted = 0
            </where>
            </script>
            """)
    List<Club> list(ClubPageDTO dto);

    @ResultMap("clubMap")
    @Select("select * from cms_club where id = #{id} and deleted = 0")
    Club select(Long id);

    @Insert("insert into cms_club " +
            "values (null, #{title}, #{tag}, #{fkDirectionId}, #{startTime}, #{expectedEndTime}, #{endTime}, #{info}, #{version}, #{deleted}, #{created}, #{updated})")
    int insert(Club club);

    @Update("""
            <script>
            update cms_club
            <set>
            <if test='title != null'> title = #{title}, </if>
            <if test='tag != null'> tag = #{tag}, </if>
            <if test='fkDirectionId != null'> fk_direction_id = #{fkDirectionId}, </if>
            <if test='startTime != null'> start_time = #{startTime}, </if>
            <if test='expectedEndTime != null'> expected_end_time = #{expectedEndTime}, </if>
            <if test='endTime != null'> end_time = #{endTime}, </if>
            <if test='info != null'> info = #{info}, </if>
            <if test='deleted != null'> deleted = #{deleted}, </if>
            <if test='created != null'> created = #{created}, </if>
            <if test='updated != null'> updated = #{updated}, </if>
            version = version + 1
            </set>
            where id = #{id} and deleted = 0 and version = #{version}
            </script>
            """)
    int update(Club club);

    @Update("""
            update cms_club set deleted = 1, updated = current_timestamp
            where id = #{param1}
            """)
    int delete(Long id);

    @Update("""
            <script>
            update cms_club set deleted = 1, updated = current_timestamp
            where id in
            <foreach collection='list' item='e' open='(' close=')' separator=','>
                ${e}
            </foreach>
            </script>
            """)
    int deleteBatch(@Param("list") List<Long> ids);
}
