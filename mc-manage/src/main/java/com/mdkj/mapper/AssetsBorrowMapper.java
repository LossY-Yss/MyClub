package com.mdkj.mapper;

import com.mdkj.dto.assetsBorrow.AssetsBorrowPageDTO;
import com.mdkj.entity.AssetsBorrow;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface AssetsBorrowMapper {

    //条件查询
    @Results(
            id = "asMap", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "fkAssetsId", column = "fk_assets_id"),
            @Result(property = "fkEmpId", column = "fk_emp_id"),
            @Result(property = "assets", column = "fk_assets_id", one = @One(
                    select = "com.mdkj.mapper.AssetsMapper.select"
            )),
            @Result(property = "emp", column = "fk_emp_id", one = @One(
                    select = "com.mdkj.mapper.EmpMapper.select"
            ))
    })
    @Select("""
                <script>
                    select * from rms_assets_borrow
                    <where>
                        <if test='fkAssetsId !=null'>
                            fk_assets_id = #{fkAssetsId} and
                        </if>
                        <if test='fkEmpId !=null'>
                            fk_emp_id = #{fkEmpId} and
                        </if>
                        deleted = 0
                    </where>
                </script>
            """)
    List<AssetsBorrow> list(AssetsBorrowPageDTO dto);

    //主键查询
    @ResultMap("asMap")
    @Select("select * from rms_assets_borrow where id = #{id} and deleted = 0")
    AssetsBorrow select(Long id);

    //添加--单条
    @Insert("insert into rms_assets_borrow values(null,#{fkAssetsId},#{fkEmpId},#{count},#{borrowTime},#{expectedReturnTime}," +
            "#{returnTime},#{info},#{version},#{deleted},#{created},#{updated})")
    int insert(AssetsBorrow assetsBorrow);

    //修改--单条
    @Update("""
                <script>
                    update rms_assets_borrow set 
                    <if test='fkAssetsId != null'> fk_assets_id = #{fkAssetsId},</if>
                    <if test='fkEmpId != null'> fk_emp_id = #{fkEmpId},</if>
                    <if test='count != null'> count = #{count},</if>
                    <if test='borrowTime != null'> borrow_time = #{borrowTime},</if>
                    <if test='expectedReturnTime != null'> expected_return_time = #{expectedReturnTime},</if>
                    <if test='returnTime != null'> return_time = #{returnTime},</if>
                    <if test='info != null'> info = #{info},</if>
                    <if test='deleted != null'> deleted = #{deleted},</if>
                    <if test='created != null'> created = #{created},</if>
                    <if test='updated != null'> updated = #{updated},</if>
                    version = version + 1
                    where id = #{id} and deleted = 0 and version = #{version}
                </script>
            """)
    int update(AssetsBorrow assetsBorrow);

    //删除--单条
    @Update("update rms_assets_borrow set deleted = 1,updated = current_timestamp where id = #{id}")
    int delete(Long id);

    //删除--多条
    @Update("""
                <script>
                    update rms_assets_borrow set deleted = 1,updated = current_timestamp where id in
                    <foreach collection = 'list' item = 'e' open='(' separator=',' close=')'>
                            #{e}
                    </foreach>
                </script>
            """)
    int deleteBatch(@Param("list") List<Long> ids);

}
