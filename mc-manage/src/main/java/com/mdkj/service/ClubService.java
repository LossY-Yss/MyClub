package com.mdkj.service;

import com.github.pagehelper.PageInfo;
import com.mdkj.dto.club.ClubExcel;
import com.mdkj.dto.club.ClubInsertDTO;
import com.mdkj.dto.club.ClubPageDTO;
import com.mdkj.dto.club.ClubUpdateDTO;
import com.mdkj.entity.Club;
import com.mdkj.vo.ClubVO;

import java.util.List;

public interface ClubService {
    List<ClubVO> list();
    PageInfo<Club> page(ClubPageDTO dto);
    Club select(Long id);
    int insert(ClubInsertDTO dto);
    int update(ClubUpdateDTO dto);
    int delete(Long id);
    int deleteBatch(List<Long> ids);
    List<ClubExcel> getExcelData();
    //修改--结班
    int endClub(Long id);
}
