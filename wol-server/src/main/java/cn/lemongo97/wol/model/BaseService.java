package cn.lemongo97.wol.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BaseService {
    @TableField
    protected Date createTime;
    @TableField
    protected Date updateTime;
    @TableField
    protected Long userId;
}
