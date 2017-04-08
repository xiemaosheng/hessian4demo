package com.xms.task.code;

public enum Code
{

    REQUEST_REFUSE("接入未授权"),

    ARGUMENT_REQUIRED("参数{0}不可为空"),

    ARGUMENT_SHORT("参数({0})长度不小于{1}"),

    ARGUMENT_LONG("参数({0})长度不大于{1}"),

    ARGUMENT_INVALID("参数({0})不合法"),

    ARGUMENT_CARDNO("参数({0})不是身份证号"),

    ARGUMENT_EMAIL("参数({0})不是电子邮箱"),

    ARGUMENT_JSON("参数({0})不是有效json"),

    ARGUMENT_MOBILE("参数({0})不是手机号"),

    ARGUMENT_URL("参数({0})不是有效的url"),

    ARGUMENT_NUMBER("参数({0})不是有效的数字"),

    ARGUMENT_FLOAT("参数({0})不是有效的小数"),

    REQUEST_INVALID("请求非法"),

    TASK_TENANT_NOT_EXIST("租户不存在"),

    TASK_TENANT_EXIST_ALREADY("租户已存在"),

    TASK_PUSH_NOT_FOUND("推送不存在"),

    TASK_PUSH_NOT_DOWN("推送未下架不允许编辑"),

    TASK_PUSH_MIXED_TARGET("触发对象存在重合情况"),

    USER_TASK_NOT_EXIST("用户任务不存在"),

    TASK_AUDIT_NOT_DONE("还有未审核的相同任务"),

    TASK_TEMPLATE_NOT_EXIST("模板不存在"),

    TENANT_IMPORT_ID_NOT_EQUAL("当前租户与导入配置中租户ID（{0}）不一致"),

    TENANT_TASK_EXIST("任务已存在{0}"),

    SERVER_ERROR("内部错误")
    ;

    private String parse;

    private String[] argNames;

    private Code(String parse, String... argNames) {
        this.parse = parse;
    }

    public String getReason() {
        return parse;
    }

    public String [] getArgNames() {
        return argNames;
    }
}
