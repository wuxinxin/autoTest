package com.example.dztest.extentreport.db.dao;

import com.example.dztest.extentreport.db.domain.CaseInfoTest;

public interface CaseInfoTestMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CaseInfoTest record);

    int insertSelective(CaseInfoTest record);

    CaseInfoTest selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CaseInfoTest record);

    int updateByPrimaryKey(CaseInfoTest record);
}