package cn.lemongo97.wol.service;

import cn.lemongo97.wol.model.MachinePO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.IOException;

public interface MachineService extends IService<MachinePO> {
    IPage<MachinePO> list(Long userId, Page<MachinePO> pageInfo);

    MachinePO add(MachinePO machine);

    void modify(MachinePO machine);

    void wake(String machineId) throws IOException;
}
