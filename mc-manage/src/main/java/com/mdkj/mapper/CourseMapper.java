package com.mdkj.mapper;

import com.mdkj.dto.course.CoursePageDTO;
import com.mdkj.entity.Course;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CourseMapper {

    @Select("""
        <script>
            select * from cms_course
            <where>
                <if test='title != null'>title like concat('%', #{title}, '%') and</if>
                deleted = 0
            </where>
        </script>
    """)
    List<Course> list(CoursePageDTO dto);

    @Select("select * from cms_course where id = #{id} and deleted = 0")
    Course select(Long id);

    @Insert("""
        insert into cms_course values (null, #{title}, #{fkDirectionId}, #{idx}, #{stage}, #{hours}, #{info}, #{version}, #{deleted}, #{created}, #{updated})
    """)
    int insert(Course course);

    @Update("""
        <script>
            update cms_course set 
            <if test = 'title != null'>title = #{title},</if>
            <if test = 'fkDirectionId != null'>fk_direction_id = #{fkDirectionId},</if>
            <if test = 'idx != null'>idx = #{idx},</if>
            <if test = 'stage != null'>stage = #{stage},</if>
            <if test = 'hours != null'>hours = #{hours},</if>
            <if test = 'info != null'>info = #{info},</if>
            version = version + 1
            where id = #{id} and deleted = 0 and version = #{version}
        </script>
    """)
    int update(Course course);

    @Update("update cms_course set deleted = 1 where id = #{id} and deleted = 0")
    int delete(Long id);

    @Update("""
    <script>
        update cms_course set deleted = 1 where id in 
        <foreach collection='list' item='e' open='(' separator=',' close=')'>
            #{e}
        </foreach>
    </script>
    """)
    int deleteBatch(@Param("list") List<Long> list);
}