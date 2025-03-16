package com.phoenix.devops.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.phoenix.devops.common.SelectCommon;
import com.phoenix.devops.entity.Machine;
import com.phoenix.devops.lang.IPage;
import com.phoenix.devops.mapper.MachineMapper;
import com.phoenix.devops.service.IMachineService;
import com.phoenix.devops.service.IResourceAuthService;
import com.phoenix.devops.utils.CollectUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.phoenix.devops.entity.table.ResourceAuthTableDef.RESOURCE_AUTH;

/**
 * 主机表 服务层实现。
 *
 * @author wjj-phoenix
 * @since 2025-03-16
 */
@Service
public class MachineServiceImpl extends ServiceImpl<MachineMapper, Machine> implements IMachineService {
    @Resource
    private IResourceAuthService resourceAuthService;

    @Override
    public Machine fetchMachineById(Long id) {
        return mapper.selectOneWithRelationsById(id);
    }

    @Override
    public IPage<Machine> fetchAllMachinesByConditionWithPage(Integer page, Integer limit, String condition) {
        Page<Machine> paginate = SelectCommon.findAllPaginateWithRelations(page, limit, condition, mapper);
        if (CollectUtil.isNotEmpty(paginate.getRecords())) {
            return IPage.of(
                    paginate.getPageNumber(),
                    paginate.getPageSize(),
                    paginate.getTotalRow(),
                    paginate.getTotalPage(),
                    paginate.getRecords()
            );
        }
        return IPage.empty();
    }

    @Override
    public long addMachine(Machine machine) {
        machine.setCreatedUser(StpUtil.getLoginIdAsLong());
        machine.setUpdatedUser(StpUtil.getLoginIdAsLong());
        if (!this.save(machine)) {
            throw new IllegalStateException("添加主机信息失败!");
        }
        return machine.getId();
    }

    @Override
    public boolean modMachineById(Machine machine) {
        machine.setUpdatedUser(StpUtil.getLoginIdAsLong());
        if (!this.updateById(machine)) {
            throw new IllegalStateException("修改主机信息失败!");
        }
        return true;
    }

    @Override
    public boolean delMachine(Set<Long> ids) {
        boolean isSuccess = resourceAuthService.remove(QueryWrapper.create().where(RESOURCE_AUTH.MACHINE_ID.in(ids)));

        if (!isSuccess && !this.removeByIds(ids)) {
            throw new IllegalStateException("删除主机信息失败!");
        }
        return true;
    }
}
