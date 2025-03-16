package com.phoenix.devops.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.mybatisflex.core.BaseMapper;
import com.phoenix.devops.entity.Machine;

/**
 * 主机表 映射层。
 *
 * @author wjj-phoenix
 * @since 2025-03-16
 */
@Mapper
public interface MachineMapper extends BaseMapper<Machine> {

}
