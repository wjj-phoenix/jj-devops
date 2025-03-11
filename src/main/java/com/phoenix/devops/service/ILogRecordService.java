package com.phoenix.devops.service;

import com.phoenix.devops.model.RecordLogInfo;

public interface ILogRecordService {
    /**
     * 保存 log
     *
     * @param easyLogInfo 日志实体
     */
    void record(RecordLogInfo easyLogInfo);

}