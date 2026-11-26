package com.mdkj.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mdkj.dto.club.ClubExcel;
import com.mdkj.dto.club.ClubInsertDTO;
import com.mdkj.dto.club.ClubPageDTO;
import com.mdkj.dto.club.ClubUpdateDTO;
import com.mdkj.entity.Club;
import com.mdkj.exception.ServerErrorException;
import com.mdkj.mapper.ClubMapper;
import com.mdkj.service.ClubService;
import com.mdkj.vo.ClubVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClubServiceImpl implements ClubService {

    @Autowired
    private ClubMapper clubMapper;

    @Override
    public List<ClubVO> list() {
        return clubMapper.list(new ClubPageDTO())
                .stream()
                .map(club -> BeanUtil.copyProperties(club, ClubVO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PageInfo<Club> page(ClubPageDTO dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        return new PageInfo<>(clubMapper.list(dto));
    }

    @Override
    public Club select(Long id) {
        return clubMapper.select(id);
    }

    @Override
    public int insert(ClubInsertDTO dto) {
        String info = dto.getInfo();
        List<String> tag = dto.getTag();
        Club club = BeanUtil.copyProperties(dto, Club.class);
        club.setInfo(StrUtil.isBlank(info) ? "暂无描述" : info);
        club.setTag(CollUtil.isEmpty(tag) ? "暂无标签" : String.join(",", tag));
        club.setVersion(0L);
        club.setDeleted(0);
        club.setCreated(LocalDateTime.now());
        club.setUpdated(LocalDateTime.now());
        int result = clubMapper.insert(club);
        if (result <= 0) {
            throw new ServerErrorException("DB添加失败");
        }
        return result;
    }

    @Override
    public int update(ClubUpdateDTO dto) {
        Club club = clubMapper.select(dto.getId());
        if (ObjectUtil.isNull(club)) {
            throw new ServerErrorException("记录不存在");
        }
        BeanUtil.copyProperties(dto, club);
        club.setUpdated(LocalDateTime.now());
        int result = clubMapper.update(club);
        if (result <= 0) {
            throw new ServerErrorException("DB修改失败");
        }
        return result;
    }

    @Override
    public int delete(Long id) {
        return clubMapper.delete(id);
    }

    @Override
    public int deleteBatch(List<Long> ids) {
        return clubMapper.deleteBatch(ids);
    }

    @Override
    public List<ClubExcel> getExcelData() {
        return clubMapper.list(new ClubPageDTO())
                .stream()
                .map(club -> {
                    ClubExcel clubExcel = BeanUtil.copyProperties(club, ClubExcel.class);
                    if (ObjectUtil.isNotNull(club.getDirection())) {
                        clubExcel.setTag(club.getDirection().getTitle());
                    }
                    return clubExcel;
                })
                .collect(Collectors.toList());
    }

    @Override
    public int endClub(Long id) {
        Club club = clubMapper.select(id);
        if (ObjectUtil.isNull(club)) {
            throw new ServerErrorException("记录不存在");
        }
        if (ObjectUtil.isNotNull(club.getEndTime())) {
            throw new ServerErrorException("班级已结班");
        }
        club.setEndTime(LocalDateTime.now());
        club.setUpdated(LocalDateTime.now());
        return clubMapper.update(club);
    }
}
