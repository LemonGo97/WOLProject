package cn.lemongo97.wol.controller;

import cn.lemongo97.wol.config.ResponseResult;
import cn.lemongo97.wol.model.MachinePO;
import cn.lemongo97.wol.service.MachineService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@ResponseResult
@RequestMapping("/machine")
public class MachineController {

    @Autowired
    private MachineService machineService;

    @GetMapping
    public IPage<MachinePO> list(Long userId, Page<MachinePO> pageInfo) {
        return machineService.list(userId, pageInfo);
    }

    @PostMapping
    public MachinePO add(@RequestBody MachinePO machine) {
        return machineService.add(machine);
    }

    @DeleteMapping("/{machineId}")
    public void del(@PathVariable("machineId") String machineId) {
        machineService.removeById(machineId);
    }

    @PutMapping
    public void modify(@RequestBody MachinePO machine) {
        machineService.modify(machine);
    }

    @GetMapping("/{machineId}")
    public MachinePO get(@PathVariable String machineId) {
        return machineService.getById(machineId);
    }

    @GetMapping("/{machineId}")
    public void wake(@PathVariable String machineId) throws IOException {
        machineService.wake(machineId);
    }
}
