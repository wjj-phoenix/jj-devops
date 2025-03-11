package com.phoenix.devops.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.mybatisflex.core.BaseMapper;
import com.phoenix.devops.entity.SysMenu;

/**
 *  映射层。
 *
 * @author wjj-phoenix
 * @since 2025-03-11
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

}
