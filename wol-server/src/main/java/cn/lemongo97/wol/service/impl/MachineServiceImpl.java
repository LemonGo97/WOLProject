package cn.lemongo97.wol.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.lemongo97.wol.Response;
import cn.lemongo97.wol.common.ResultCode;
import cn.lemongo97.wol.exception.WOLBaseException;
import cn.lemongo97.wol.mapper.MachineMapper;
import cn.lemongo97.wol.model.MachinePO;
import cn.lemongo97.wol.service.MachineService;
import cn.lemongo97.wol.ws.WebSocketServer;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;

@Service
public class MachineServiceImpl extends ServiceImpl<MachineMapper, MachinePO> implements MachineService {

    @Autowired
    private MachineMapper machineMapper;

    @Override
    public IPage<MachinePO> list(Long userId, Page<MachinePO> page) {
        return machineMapper.selectPage(page, new QueryWrapper<MachinePO>().select("*").eq("user_id", userId));
    }

    @Override
    public MachinePO add(MachinePO machine) {
        machine.setCreateTime(Date.from(Instant.now()));
        machine.setUpdateTime(Date.from(Instant.now()));
        this.save(machine);
        return machine;
    }

    @Override
    public void modify(MachinePO machine) {
        MachinePO old = this.getById(machine.getId());
        if (old == null) {
            throw new WOLBaseException(ResultCode.OBJECT_NOT_EXIST);
        }
        machine.setUpdateTime(Date.from(Instant.now()));
        this.updateById(machine);
    }

    @Override
    public void wake(String machineId) throws IOException {
        MachinePO machine = this.getById(machineId);
        WebSocketServer.sendInfo(machine.getServiceId(),
                Response.success(machine.getServiceId(),
                "wake",
                MapUtil.builder()
                    .put("ip", machine.getIpAddress())
                    .put("mac", machine.getMacAddress())
                    .build()));
    }
}
