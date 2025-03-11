package com.phoenix.devops.service.impl;

import com.phoenix.devops.model.RecordLogInfo;
import com.phoenix.devops.service.ILogRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DefaultLogRecordServiceImpl implements ILogRecordService {
    @Override
    public void record(RecordLogInfo easyLogInfo) {
    }
}