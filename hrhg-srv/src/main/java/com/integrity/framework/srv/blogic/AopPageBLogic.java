/**
 * 面向切面通用业务逻辑。<br>
 */
package com.integrity.framework.srv.blogic;

import com.integrity.framework.api.bean.BodyBaseReq;
import com.integrity.framework.api.bean.BodyBaseResp;
import com.integrity.framework.api.bean.BodyReq;
import com.integrity.framework.api.bean.BodyResp;
import com.integrity.framework.api.code.ApiType;
import com.integrity.framework.api.code.CodePath;
import com.integrity.framework.api.code.CodeType;
import com.integrity.framework.api.code.FrameworkCode;
import com.integrity.framework.exception.BLogicException;
import com.integrity.framework.utils.BeanUtils;
import com.integrity.framework.utils.LogUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 面向切面通用业务逻辑。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class AopPageBLogic<P extends BodyBaseReq, R extends BodyBaseResp> extends AbstractAopBLogic<P, R> {
    /**
     * 日志输出对象
     */
    private static final Logger logger = LoggerFactory.getLogger(AopPageBLogic.class);
    /**
     * 用户ID
     */
    protected String uidLogin;
    /**
     * 业务编码枚举
     */
    protected CodePath bizzCode;
    /**
     * 应用类型
     */
    protected ApiType.Type appType;

    /**
     * 获取分组字段信息。<br>
     *
     * @return 分组字段信息
     */
    @Override
    protected String groupField() {
        return null;
    }

    /**
     * 更新鉴权信息。<br>
     *
     * @param uid      用户ID
     * @param codePath 编码路径
     */
    @Override
    public void refreshAuthInfo(String uid, CodePath codePath) {
        this.uidLogin = uid;
        this.bizzCode = codePath;
    }

    /**
     * 预处理逻辑。<br>
     *
     * @param param 预处理参数
     * @return 预处理结果
     * @throws BLogicException 业务逻辑异常
     */
    @SuppressWarnings("unchecked")
    @Override
    protected P preLogic(P param) throws BLogicException {
        if (null == this.bizzCode) {
            // 业务编码不存在
            throw new BLogicException(FrameworkCode.Message.E_NOT_EXIST_BIZZ);
        }

        // 执行父功能操作
        BodyBaseReq<?> modifyParam = super.preLogic(param);

        // 获取应用类型
        this.appType = ApiType.Type.fromCode(modifyParam.getHead().getAppId());

        if (null == this.appType) {
            // 无效的应用编码类型
            throw new BLogicException(FrameworkCode.Message.E_NOT_EXIST_APPTYPE);
        }

        // 业务逻辑处理开始
        LogUtils.info(logger, this.bizzCode.getRootCode() + CodeType.SEPARATOR_UNDERLINE
                        + FrameworkCode.Message.I_REQ_BEGIN.getCode(),
                FrameworkCode.Message.I_REQ_BEGIN.getMessage(), this.bizzCode.getBizzName());
        return super.preLogic(param);
    }

    /**
     * 后处理逻辑。<br>
     *
     * @param param  请求参数
     * @param result 处理结果
     * @return 处理结果修正
     * @throws BLogicException 业务逻辑异常
     */
    @Override
    @SuppressWarnings("unchecked")
    protected R postLogic(P param, R result) throws BLogicException {
        // 执行父功能操作
        BodyBaseResp<?> modifyResult = super.postLogic(param, result);
        // 设置成功消息
        modifyResult.getHead().setResult(FrameworkCode.Message.OK.getFullCode());
        // 设置成功消息
        modifyResult.getHead().setMsg(String.format(FrameworkCode.Message.OK.getMessage(), this.bizzCode.getBizzName()));

        // 业务逻辑处理完成
        LogUtils.info(logger, this.bizzCode.getRootCode() + CodeType.SEPARATOR_UNDERLINE
                        + FrameworkCode.Message.I_REQ_END.getCode(),
                FrameworkCode.Message.I_REQ_END.getMessage(), this.bizzCode.getBizzName());

        return (R) modifyResult;
    }

    /**
     * 生成响应就消息体数据。<br>
     *
     * @param blogic   简单业务逻辑
     * @param reqBody  请求消息体数据
     * @param respBody 响应消息体数据
     * @param <BP>     请求消息体类型
     * @param <BR>     响应消息体类型
     * @throws BLogicException 业务逻辑异常
     */
    @SuppressWarnings("unchecked")
    protected <BP extends BodyReq, BR extends BodyResp> void makeRespBody(
            SimpleWithAuthBLogic blogic, BP reqBody, BR respBody) throws BLogicException {
        // 设置登录用户数据
        blogic.setUidLogin(this.uidLogin);
        // 执行简单业务逻辑
        BR respData = (BR) blogic.execute(reqBody);

        // 复制响应结果
        try {
            BeanUtils.copyBeanValue(respData, respBody);
        } catch (Exception e) {
            throw new BLogicException(FrameworkCode.Message.E_NO_PROPERTY);
        }
    }
}
