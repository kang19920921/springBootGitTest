package cn.tedu.inputDTO;


import cn.tedu.Utils.ValidateUtil;
import cn.tedu.pojo.BaseObject;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class EncryptBaseInputDTO extends BaseObject {



    @NotBlank(message = "客户编号不能为空")
    private String clientId;

    @NotBlank(message = "请求唯一标识不能为空")
    private String requestId;

    @NotBlank(message = "时间戳不能为空")
    private String timestamp;

    @NotBlank(message = "加密的业务数据不能为空")
    private String requestDataEnc;

    @NotBlank(message = "AES秘钥不能为空")
    private String encodeKey;

    @NotBlank(message = "签名不能为空")
    private String sign;

    public void validateParams() {
        ValidateUtil.assertNotNullWarm(clientId, "客户编号不能为空");
        ValidateUtil.assertNotNullWarm(requestId, "请求唯一标识不能为空");
        ValidateUtil.assertNotNullWarm(timestamp, "时间戳不能为空");
        ValidateUtil.assertNotNullWarm(requestDataEnc, "加密的业务数据不能为空");
        ValidateUtil.assertNotNullWarm(encodeKey, "AES秘钥不能为空");
        ValidateUtil.assertNotNullWarm(sign, "签名不能为空");
    }
}
