package com.mdkj.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mdkj.dto.school.SchoolExcel;
import com.mdkj.dto.school.SchoolInsertDTO;
import com.mdkj.dto.school.SchoolPageDTO;
import com.mdkj.dto.school.SchoolUpdateDTO;
import com.mdkj.entity.School;
import com.mdkj.exception.ServerErrorException;
import com.mdkj.mapper.SchoolMapper;
import com.mdkj.service.SchoolService;
import com.mdkj.vo.SchoolVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    private SchoolMapper schoolMapper;

    @Override
    public List<SchoolVo> selectSchoolAll() {
        return schoolMapper.selectSchoolAll();
    }

    @Override
    public School select(Long id) {
        return schoolMapper.select(id);
    }

    @Override
    public PageInfo<School> page(SchoolPageDTO dto) {
        //开启分页
        PageHelper.startPage(dto.getPageNum(),dto.getPageSize());
        //执行查询
        List<School> list = schoolMapper.list(dto);
        return new PageInfo<>(list);
    }

    @Override
    public int insert(SchoolInsertDTO dto) {
        //将dto的属性拷贝 生成一个room对象
        School school = BeanUtil.copyProperties(dto, School.class);
        //设置默认值
        String address = dto.getAddress();
        String info = dto.getInfo();
        school.setAddress(StrUtil.isBlank(address) ? "暂无地址" : address);
        school.setInfo(StrUtil.isBlank(info) ? "暂无描述" : info);
        school.setVersion(0L);
        school.setDeleted(0);
        school.setCreated(LocalDateTime.now());
        school.setUpdated(LocalDateTime.now());
        return schoolMapper.insert(school);
    }

    @Override
    public int delete(Long id) {
        return schoolMapper.delete(id);
    }

    @Override
    public int deleteBatch(List<Long> ids) {
        return schoolMapper.deleteBatch(ids);
    }

    @Override
    public Integer update(SchoolUpdateDTO dto) {
        //根据id查询要修改的数据是否存在
        School school = schoolMapper.select(dto.getId());
        if(ObjectUtil.isNull(school)){
            throw  new ServerErrorException("记录不存在");
        }
        //将dto（要修改的数据）的属性拷贝到 room（当前的数据）实现属性覆盖
        BeanUtil.copyProperties(dto,school);
        //设置默认值
        school.setUpdated(LocalDateTime.now());
        //执行mapper修改
        return schoolMapper.update(school);
    }

    @Override
    public List<SchoolExcel> getExcelData() {
        return schoolMapper.list(new SchoolPageDTO())
                .stream()
                .map(school -> BeanUtil.copyProperties(school, SchoolExcel.class))
                .collect(Collectors.toList());
    }
}
