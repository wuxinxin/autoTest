package com.example.dztest.extentreport.db.dao;

import com.example.dztest.extentreport.db.domain.CaseInfo;

import java.util.List;

public interface CaseInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CaseInfo record);

    int insertSelective(CaseInfo record);

    int batchInsert(List<CaseInfo> caseInfoList);

    CaseInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CaseInfo record);

    int updateByPrimaryKey(CaseInfo record);
}