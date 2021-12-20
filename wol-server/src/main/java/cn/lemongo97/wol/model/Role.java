package cn.lemongo97.wol.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author lemongo97
 */
@Table(name = "t_role")
@Entity
@Data
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String nameZh;
}
