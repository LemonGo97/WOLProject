package cn.lemongo97.wol.controller;

import cn.hutool.core.map.MapUtil;
import cn.lemongo97.wol.config.ResponseResult;
import cn.lemongo97.wol.config.Result;
import cn.lemongo97.wol.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LemonGo97
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/info")
    public Result getInfo() {
        return Result.success(MapUtil.builder()
                .put("id", 1)
                .put("name", "admin")
                .put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif").build());
    }

}
