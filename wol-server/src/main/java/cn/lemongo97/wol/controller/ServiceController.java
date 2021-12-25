package cn.lemongo97.wol.controller;

import cn.hutool.core.map.MapUtil;
import cn.lemongo97.wol.config.ResponseResult;
import cn.lemongo97.wol.config.Result;
import cn.lemongo97.wol.model.ServicePO;
import cn.lemongo97.wol.service.ServiceService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseResult
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @GetMapping
    public IPage<ServicePO> listService(Long userId, Page<ServicePO> pageInfo) {
        return serviceService.listService(userId, pageInfo);
    }

    @PostMapping
    public ServicePO addService(@RequestBody ServicePO service) {
        return serviceService.addService(service);
    }

    @DeleteMapping("/{clientId}")
    public void delService(@PathVariable("clientId") String clientId) {
        serviceService.removeById(clientId);
    }

    @PutMapping
    public void modifyService(@RequestBody ServicePO service) {
        serviceService.modifyService(service);
    }

    @GetMapping("/{clientId}")
    public ServicePO getService(@PathVariable String clientId) {
        return serviceService.getById(clientId);
    }
}
