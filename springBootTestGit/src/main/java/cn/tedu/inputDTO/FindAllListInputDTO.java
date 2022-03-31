package cn.tedu.inputDTO;


import cn.tedu.pojo.BaseObject;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FindAllListInputDTO extends BaseObject {

    @NotNull(message = "用户id不能为空")
    private Integer idUser;

}
