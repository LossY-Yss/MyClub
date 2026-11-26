package com.mdkj.mapper;

import com.mdkj.dto.role.RolePageDTO;
import com.mdkj.entity.EmpRole;
import com.mdkj.entity.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface RoleMapper {

    //添加--单条
    @Insert("""
            insert into ums_role (title, info, version, deleted, created, updated)
            values (#{title}, #{info}, #{version}, #{deleted}, #{created}, #{updated})
            """)
    int insert(Role role);

    //查询--单条
    @Select("""
            select * from ums_role t
            where t.id = #{id} and t.deleted = 0
            """)
    Role select(Long id);

    //查询--全部
    @Select("""
            <script>
            select * from ums_role t
            <where>
                <if test='title != null'> title like concat('%', #{title}, '%') and </if>
                t.deleted = 0
            </where>
            </script>
            """)
    List<Role> list(RolePageDTO dto);

    //修改--单条
    @Update("""
            <script>
            update ums_role
            <set>
            <if test='title != null'> title = #{title}, </if>
            <if test='info != null'> info = #{info}, </if>
            <if test='deleted != null'> deleted = #{deleted}, </if>
            <if test='created != null'> created = #{created}, </if>
            <if test='updated != null'> updated = #{updated}, </if>
            version = version + 1
            </set>
            where id = #{id} and deleted = 0 and version = #{version}
            </script>
            """)
    int update(Role role);

    //删除--单条
    @Update("""
            update ums_role set deleted = 1, updated = current_timestamp
            where id = #{param1}
            """)
    int delete(Long id);

    //删除--多条
    @Update("""
            <script>
            update ums_role set deleted = 1, updated = current_timestamp
            where id in
            <foreach collection='list' item='e' open='(' close=')' separator=','>
                ${e}
            </foreach>
            </script>
            """)
    int deleteBatch(List<Long> ids);

    //查询--员工角色
    @Select("""
        select * from ums_role where id in(
        	select fk_role_id from ums_emp_role where fk_emp_id = #{empId} and deleted = 0
        ) and deleted = 0
    """)
    List<Role> listByEmpId(Long empId);

    //删除--员工角色
    @Update("""
        update ums_emp_role set deleted = 1 , updated = now()  where fk_emp_id = #{empId}
    """)
    int deleteEmpRoleByEmpId(Long empId);

    //新增--员工角色
    @Insert("""
        <script>
            insert into ums_emp_role values 
            <foreach collection = 'list' item='e' separator=','>
                (null,#{e.fkEmpId},#{e.fkRoleId},#{e.version},#{e.deleted},#{e.created},#{e.updated})
            </foreach>
        </script>
    """)
    int insertRoleBatch(List<EmpRole> empRoles);
}
