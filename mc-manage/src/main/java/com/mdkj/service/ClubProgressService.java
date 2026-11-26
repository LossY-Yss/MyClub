package com.mdkj.service;

import com.github.pagehelper.PageInfo;
import com.mdkj.dto.clubProgress.ClubProgressExcel;
import com.mdkj.dto.clubProgress.ClubProgressInsertDTO;
import com.mdkj.dto.clubProgress.ClubProgressPageDTO;
import com.mdkj.dto.clubProgress.ClubProgressUpdateDTO;
import com.mdkj.entity.ClubProgress;

import java.util.List;

public interface ClubProgressService {
    int insert(ClubProgressInsertDTO dto);
    ClubProgress select(Long id);
    PageInfo<ClubProgress> page(ClubProgressPageDTO dto);
    int update(ClubProgressUpdateDTO dto);
    int delete(Long id);
    int deleteBatch(List<Long> ids);

    List<ClubProgressExcel> getExcelData();

    int endClubProgress(Long id);
}
