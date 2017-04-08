package com.xms.task.code;


import org.springframework.http.HttpStatus;

/**
 * Created by bowen on 2017/2/21 0021.
 * 异常代码封装
 */
public enum ExceptionCode {
    // 授权相关
    UNAUTHORIZED("TASK/UNAUTHORIZED", "用户授权错误", HttpStatus.UNAUTHORIZED),
    // 管理台校验缺失参数
    ADMIN_ARGUMENT_REQUIRED("TASK/ADMIN_ARGUMENT_REQUIRED", "缺少参数: %s", HttpStatus.BAD_REQUEST),

    ARGUMENT_INVALID("TASK/ARGUMENT_INVALID", "请求参数不合法", HttpStatus.BAD_REQUEST),
    RETRY_AGAIN("TASK/RETRY_AGAIN", "请稍后重试", HttpStatus.BAD_REQUEST),
    ARGUMENT_REQUIRED("TASK/ARGUMENT_REQUIRED", "缺少参数", HttpStatus.BAD_REQUEST),
    FILE_NOT_FOUND("TASK/FILE_NOT_FOUND", "文件不存在", HttpStatus.NOT_FOUND),
    TENANT_NOT_EXIST("TASK/TENANT_NOT_EXIST", "租户不存在", HttpStatus.BAD_REQUEST),
    ORG_ID_REQUIRED("TASK/ORG_ID_REQUIRED", "缺少参数,组织ID", HttpStatus.BAD_REQUEST),
    LOST_REQUIRED("TASK/LOST_REQUIRED", "缺少参数,orgId或者uid", HttpStatus.BAD_REQUEST),
    LIMIT_INVALID("TASK/LIMIT_INVALID", "limit必须大于0", HttpStatus.BAD_REQUEST),
    OFFSET_INVALID("TASK/OFFSET_INVALID", "OFFSET必须大于等于0", HttpStatus.BAD_REQUEST),
    TENANT_EXIST_ALREADY("TASK/TENANT_EXIST_ALREADY", "租户已存在", HttpStatus.BAD_REQUEST),
    TEMPLATE_ID_REQUIRED("TASK/TEMPLATE_ID_REQUIRED", "缺少参数,模板ID", HttpStatus.BAD_REQUEST),
    TEMPLATE_NOT_EXIST("TASK/TEMPLATE_NOT_EXIST", "模板不存在", HttpStatus.BAD_REQUEST),
    GLOBAL_TASK_CAN_NOT_DELETE("TASK/GLOBAL_TASK_CAN_NOT_DELETE", "全局任务 %s 作为 %s 的后置任务，不能被删除", HttpStatus.BAD_REQUEST),
    TASK_NOT_EXIST("TASK/TASK_NOT_EXIST", "任务不存在", HttpStatus.BAD_REQUEST),
    NEXT_TASK_INVALID("TASK/NEXT_TASK_INVALID", "后置任务不能包含自己！", HttpStatus.BAD_REQUEST),
    TASK_CODE_EXIST("TASK/TASK_CODE_EXIST", "对应任务代码已经存在", HttpStatus.BAD_REQUEST),
    TENANT_TASK_NOT_EXIST("TASK/TENANT_TASK_NOT_EXIST", "租户任务不存在", HttpStatus.BAD_REQUEST),
    TENANT_TASK_EXIST("TASK/TENANT_TASK_EXIST", "租户任务已经存在", HttpStatus.BAD_REQUEST),
    REWARD_NOT_EXIST("TASK/GLOBAL_REWARD_NOT_EXIST", "奖励不存在", HttpStatus.BAD_REQUEST),
    REWARD_LOG_NOT_EXIST("TASK/REWARD_LOG_NOT_EXIST", "奖励日志表不存在", HttpStatus.BAD_REQUEST),
    TASK_LOG_NOT_EXIST("TASK/TASK_LOG_NOT_EXIST", "任务日志表不存在", HttpStatus.BAD_REQUEST),
    REWARD_CODE_EXIST("TASK/GLOBAL_REWARD_CODE_EXIST", "奖励代码已经存在", HttpStatus.BAD_REQUEST),
    TENANT_REWARD_NOT_EXIST("TASK/TENANT_REWARD_NOT_EXIST", "租户奖励不存在", HttpStatus.BAD_REQUEST),
    TENANT_REWARD_CODE_EXIST("TASK/TENANT_REWARD_CODE_EXIST", "租户奖励已经存在", HttpStatus.BAD_REQUEST),
    INVOKE_UC_ERROR("UC/INVOKE_UC_ERROR", "访问UC失败", HttpStatus.BAD_REQUEST),
    UC_ORG_NOT_EXIST("UC/ORG_NOT_EXIST", "组织在UC不存在", HttpStatus.BAD_REQUEST),
    UC_USER_NOT_EXIST("UC/UC_USER_NOT_EXIST", "用户在UC不存在", HttpStatus.BAD_REQUEST),
    TASK_AUDIT_IN_PROGRESS("TASK_AUDIT/AUDIT_IN_PROGRESS", "还有未审核的项目", HttpStatus.BAD_REQUEST),
    FILTER_INVALID("TASK/FILTER_INVALID", "搜索参数不合法！", HttpStatus.BAD_REQUEST),
    TASK_PUSH_NOT_EXIST("TASK/TASK_PUSH_NOT_EXIST", "推送不存在", HttpStatus.BAD_REQUEST),
    TASK_PUSH_NOT_DOWN("TASK/TASK_PUSH_NOT_DOWN", "推送未下架不允许编辑", HttpStatus.BAD_REQUEST),
    TASK_PUSH_MIXED_TARGET("TASK/TASK_PUSH_MIXED_TARGET", "当前推送 %s 触发对象存在重合情况", HttpStatus.BAD_REQUEST),
    TASK_PUSH_NO_NODE("TASK/TASK_PUSH_NO_NODE", "UC 查询不到该nodeId信息，请输入正确的节点信息", HttpStatus.BAD_REQUEST),
    TASK_PUSH_NULL_WAIT_TARGET("TASK/TASK_PUSH_MIXED_TARGET", "存在已上架的推送时，当前推送 %s 的触发对象不能配置成空或者推送给所有！！", HttpStatus.BAD_REQUEST),
    TASK_PUSH_UP_TARGET("TASK/TASK_PUSH_MIXED_TARGET", "已上架的 %s 推送中的触发对象存在为空或者nodeId值为all的情况时，不能配置其他推送！！", HttpStatus.BAD_REQUEST),
    TASK_PUSH_SAME_TARGET("TASK/TASK_PUSH_MIXED_TARGET", "当前推送 %s 触发对象中的 %s 和已上架推送 %s 触发对象中的 %s 存在重合", HttpStatus.BAD_REQUEST),
    TASK_PUSH_TARGET_LENGTH_OUT_OF_MAX("TASK/TASK_PUSH_TARGET_LENGTH_OUT_OF_MAX", "推送对象配置不能超过10个", HttpStatus.BAD_REQUEST),
    TASK_PUSH_TARGET_CONFIG_OUT_OF_MAX("TASK/TASK_PUSH_TARGET_CONFIG_OUT_OF_MAX", "推送任务配置不能超过50个", HttpStatus.BAD_REQUEST),

    TASK_AUDIT_UPDATE_ERROR("TASK_AUDIT_UPDATE_ERROR", "任务审核失败", HttpStatus.BAD_REQUEST),
    TASK_AUDIT_NOT_EXIST("TASK_AUDIT_NOT_EXIST", "任务审核不存在", HttpStatus.BAD_REQUEST),
    USER_TASK_UPDATE_ERROR("USER_TASK_UPDATE_ERROR", "用户任务更新失败", HttpStatus.BAD_REQUEST),
    USER_TASK_NOT_EXIST("USER_TASK_NOT_EXIST", "用户任务不存在", HttpStatus.BAD_REQUEST),
    USER_TASK_CANCEL_AUDIT_ERROR("USER_TASK_CANCEL_AUDIT_ERROR", "撤销审核任务出错", HttpStatus.BAD_REQUEST),
    UN_SUPPORT_OPERATE("TASK/UN_SUPPORT_OPERATE", "未知类型的查询操作", HttpStatus.BAD_REQUEST),
    TENANT_IMPORT_FILE_EMPTY("TASK/TENANT_IMPORT_FILE_EMPTY", "租户配置文件为空", HttpStatus.BAD_REQUEST),
    TENANT_IMPORT_FAILURE("TASK/TENANT_IMPORT_FAILURE", "租户配置导入失败", HttpStatus.BAD_REQUEST),
    TENANT_EXPORT_FAILURE("TASK/TENANT_EXPORT_FAILURE", "租户配置导出失败", HttpStatus.BAD_REQUEST),
    TENANT_ID_NOT_EXIST("TASK/TENANT_ID_NOT_EXIST", "租户ID不能为空", HttpStatus.BAD_REQUEST),
    TENANT_EXPORT_EMPTY("TASK/TENANT_EXPORT_FAILURE", "导出租户ID集合为空", HttpStatus.BAD_REQUEST),
    TASK_CONFIGS_REQUIRED("TASK/TASK_CODE_NOT_BE_NULL", "任务必填", HttpStatus.BAD_REQUEST),
    TASK_CODE_IS_NULL("TASK/TASK_CODE_IS_NULL", "任务代码不能为空", HttpStatus.BAD_REQUEST),
    TASK_CODE_ALL_INVALID("TASK/TASK_CODE_ALL_INVALID", "所选任务全部无效", HttpStatus.BAD_REQUEST),
    TASK_LIST_NOT_EXIST("TASK/TASK_LIST_NOT_EXIST", "列表不存在", HttpStatus.BAD_REQUEST),
    LIST_CODE_UN_MATCH("TASK/LIST_CODE_UN_MATCH", "存在任务所在列表与此列表不匹配！", HttpStatus.BAD_REQUEST),
    TASK_LIST_REQUIRED("TASK/TASK_LIST_REQUIRED", "列表必填！", HttpStatus.BAD_REQUEST),
    DICT_TYPE_NOT_EXIST("TASK/DICT_TYPE_NOT_EXIST", "不存在对应字典类型", HttpStatus.BAD_REQUEST),
    REWARD_DICT_EXIST("TASK/REWARD_DICT_EXIST", "奖励字典已经存在", HttpStatus.BAD_REQUEST),
    REWARD_ITEM_NOT_EXIST("TASK/REWARD_ITEM_NOT_EXIST", "奖励项不能为空！", HttpStatus.BAD_REQUEST),
    CLONE_TASK_EMPTY("TASK/CLONE_TASK_EMPTY", "克隆任务集合为空", HttpStatus.BAD_REQUEST),
    TARGET_REQUIRED("TASK/TARGET_REQUIRED", "非默认列表触发对象不能为空！", HttpStatus.BAD_REQUEST),
    TASK_PUSH_EXIST("TASK/TASK_PUSH_EXIST", "该列表已存在推送，不能再添加！", HttpStatus.BAD_REQUEST),
    BAD_BUSINESS_ID("TASK/BAD_BUSINESS_ID", "错误的业务系统标识", HttpStatus.BAD_REQUEST),
    BAD_TASK_DAYS("TASK/BAD_TASK_DAYS", "错误的任务时间！", HttpStatus.BAD_REQUEST),
    USER_REWARD_NOT_EXIST("TASK/USER_REWARD_NOT_EXIST", "用户奖励不存在", HttpStatus.BAD_REQUEST),
    EXEC_TASK_IN_PROGRESS("TASK/EXEC_TASK_IN_PROGRESS", "任务正在处理中", HttpStatus.BAD_REQUEST),
    EXEC_REWARD_IN_PROGRESS("TASK/EXEC_REWARD_IN_PROGRESS", "奖励正在领取中", HttpStatus.BAD_REQUEST),
    FORBIDDEN_RESOURCE("TASK/NOT_AUTHORIZED_USER", "用户权限不足", HttpStatus.FORBIDDEN),
    MAX_SCHEDULE_NOT_AVAILABLE("TASK/MAX_SCHEDULE_NOT_AVAILABLE", "查不到任务进度上限", HttpStatus.SERVICE_UNAVAILABLE),
    VIP_NOT_AVAILABLE("TASK/VIP_NOT_AVAILABLE", "查不到VIP等级", HttpStatus.SERVICE_UNAVAILABLE),
    TARGET_NODE_ID_IS_EMPTY("TASK/TARGET_NODE_ID_IS_EMPTY", "目标对象节点ID不能为空！", HttpStatus.BAD_REQUEST),
    LEVEL_INVALID("TASK/LEVEL_INVALID", "目标对象startLevel不能大于endLevel！", HttpStatus.BAD_REQUEST),
    LEVEL_REQUIRED("TASK/ARGUMENT_REQUIRED", "缺少参数,等级不能为空", HttpStatus.BAD_REQUEST),
    UN_ALLOW_CYCLE_TASK("TASK/UN_ALLOW_CYCLE_TASK", "该任务列表不允许添加周期性任务！", HttpStatus.BAD_REQUEST),
    BAD_TASK_SCHEDULE("TASK/BAD_TASK_TYPE", "错误的任务进度", HttpStatus.SERVICE_UNAVAILABLE);


    private String code;    // 异常代码
    private String message; // 异常信息
    private HttpStatus status; // http 状态码

    ExceptionCode(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
