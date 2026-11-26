package com.mdkj.mapper;

import com.mdkj.dto.menu.MenuPageDTO;
import com.mdkj.entity.Menu;
import com.mdkj.entity.RoleMenu;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface MenuMapper {

    //查询--单条
    @ResultMap("mMap")
    @Select("select * from ums_menu where id = #{id}")
    Menu select(Long id);

    //查询--全部

    @Results(id = "mMap",value = {
            @Result(column = "id",property = "id",id = true),
            @Result(column = "pid",property = "pid"),
            @Result(column = "pid",property = "parent",one = @One(select = "com.mdkj.mapper.MenuMapper.select"))
    })
    @Select("""
                <script>
                    select * from ums_menu
                    <where>
                        <if test='title!=null'> title like concat('%',#{title},'%') and</if>
                        <if test='pid!= null'>pid=#{pid} and </if>
                        deleted = 0
                    </where>
                    
                </script>
            """)
    List<Menu> list(MenuPageDTO dto);

    //添加--单条
    @Insert("""
            insert into ums_menu (title, url, icon, pid, info, version, deleted, created, updated)
            values (#{title}, #{url}, #{icon}, #{pid}, #{info}, #{version}, #{deleted}, #{created}, #{updated})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Menu menu);

    //修改--单条
    @Update("""
            <script>
            update ums_menu
            <set>
            <if test='title != null'> title = #{title}, </if>
            <if test='url != null'> url = #{url}, </if>
            <if test='icon != null'> icon = #{icon}, </if>
            <if test='pid != null'> pid = #{pid}, </if>
            <if test='info != null'> info = #{info}, </if>
            <if test='deleted != null'> deleted = #{deleted}, </if>
            <if test='created != null'> created = #{created}, </if>
            <if test='updated != null'> updated = #{updated}, </if>
            version = version + 1
            </set>
            where id = #{id} and deleted = 0 and version = #{version}
            </script>
            """)
    int update(Menu menu);

    //删除
    @Update("""
            update ums_menu set deleted = 1, updated = current_timestamp where id = #{param1}
            """)
    int delete(Long id);

    @Update("""
            <script>
            update ums_menu set deleted = 1, updated = current_timestamp
            where id in
            <foreach collection='list' item='e' open='(' close=')' separator=','>
                ${e}
            </foreach>
            </script>
            """)
    int deleteBatch(List<Long> ids);

    //查询--角色菜单
    @Select("""
        select * from ums_menu where id in(
        	select fk_menu_id from ums_role_menu where fk_role_id = #{roleId} and deleted = 0
        ) and deleted = 0
    """)
    List<Menu> listByRoleId(Long roleId);

    //删除--角色菜单
    @Update("update ums_role_menu set deleted = 1,updated = now() where fk_role_id = #{roleId}")
    int deleteRoleMenuByRoleId(Long roleId);

    //添加--角色菜单
    @Insert("""
            <script>
                insert into ums_role_menu values
                <foreach collection = 'list' item = 'e' separator = ','>
                    (null,#{e.fkRoleId},#{e.fkMenuId},#{e.version},#{e.deleted},#{e.created},#{e.updated})
                </foreach>
            </script>
    """)
    int insertRoleMenuBatch(List<RoleMenu> roleMenus);

    //查询--员工菜单
    @Select("""
        select * from ums_menu where id in(
        	select distinct fk_menu_id from ums_role_menu where fk_role_id in(
        		select fk_role_id from ums_emp_role where fk_emp_id = #{empId} and deleted = 0
        	) and deleted = 0
        ) and deleted = 0
    """)
    List<Menu> listByEmpId(Long empId);
}
