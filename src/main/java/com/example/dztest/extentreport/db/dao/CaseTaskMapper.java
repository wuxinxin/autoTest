package com.example.dztest.extentreport.db.dao;

import com.example.dztest.extentreport.db.domain.CaseTask;

public interface CaseTaskMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CaseTask record);

    int insertSelective(CaseTask record);

    CaseTask selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CaseTask record);

    int updateByPrimaryKey(CaseTask record);
}