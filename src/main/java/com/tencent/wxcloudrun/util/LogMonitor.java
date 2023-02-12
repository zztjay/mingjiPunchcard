package com.tencent.wxcloudrun.util;

import com.alibaba.alisports.common.annotation.Required;
import com.taobao.eagleeye.EagleEye;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 用于业务代码对关键业务调用处进行打点监控， 统一格式， 提高SunFire等日志监控工具配置监控规则效率
 *
 * @author taohe
 * @author dylanzhou
 * @version1 增加业务日志监控格式功能
 * @version2 增加异常日志格式化功能
 * @version3 增加有效异常堆栈提取逻辑
 * @date 2018-08-09 11:30
 */

public class LogMonitor {
    /**
     * 用于区分业务场景的code，默认是系统监控
     */
    private  String monitorCode = "system";
    /**
     * 监控项描述
     */
    private  String monitorDesc = "系统监控";

    /**
     * 方法名
     */
    private static final String FIELD_NAME_METHOD_NAME = "methodName";

    /**
     * 异常名
     */
    private static final String FIELD_NAME_EXCEPTION_NAME = "exceptionName";

    /**
     * 类名
     */
    private static final String FIELD_NAME_CLASS_NAME = "className";
    /**
     * 异常堆栈
     */
    private static final String FIELD_NAME_STACK_TRACE = "stackTrace";

    /**
     * 最大异常堆栈长度
     */
    private static final int MAX_STACK_TRACE_SIZE = 1000;

    /**
     * 业务指定logger, 在比如logback-spring.xml中配置对应appender，日志可打印至指定文件
     */
    private Logger logger;

    /**
     * 日志内容格式分隔符
     */
    private final String separator = "||";
    private final String kvSeparator = ":";
    private String logLevel;

    /**
     * 业务自定义的key-value日志内容
     */
    private Map<String, Object> map = new LinkedHashMap<>();

    private Logger getLogger() {
        if (null == logger) {
            return LoggerFactory.getLogger("");
        }
        return logger;
    }

    /**
     * 业务监控日志格式： eagleEyeId || 监控编号 || 监控描述 || {业务自定义日志内容}
     *
     * @return
     */
    private String formatLogContent() {
        StringBuilder stringBuilder = buildBaseLogInfo();

        // kv信息格式化
        for (Map.Entry<String, Object> entry : this.map.entrySet()) {
            if (null == entry.getValue()) {
                stringBuilder.append(this.separator).append(entry.getKey());
            } else {
                stringBuilder.append(this.separator).append(
                    entry.getKey()).append(this.kvSeparator).append(entry.getValue());
            }
        }

        return stringBuilder.toString();
    }

    /**
     * 构建基础的日志格式
     *
     * @return
     */
    private StringBuilder buildBaseLogInfo() {
        StringBuilder stringBuilder = new StringBuilder();

        // 加入eagleEye的traceId
        String traceId = EagleEye.getTraceId();
        stringBuilder.append(this.separator).append("traceId").append(
            this.kvSeparator).append(traceId);

        // 监控code格式化
        stringBuilder.append(this.separator).append("monitorCode").append(
            this.kvSeparator).append(this.monitorCode);

        // 监控描述格式化
        stringBuilder.append(this.separator).append("monitorDesc").append(
            this.kvSeparator).append(this.monitorDesc);

        return stringBuilder;
    }

    /**
     * 构造函数，强制必填
     *
     * @param monitorCode 业务监控编号
     * @param monitorDesc 业务监控描述
     */
    private LogMonitor(@Required String monitorCode, @Required String monitorDesc) {
        this.monitorCode = monitorCode;
        this.monitorDesc = monitorDesc;
    }

    private LogMonitor() {
    }

    /**
     * 系统异常监控，增加方法名参数
     *
     * @param methodName
     * @return
     */
    public LogMonitor methodName(String methodName) {
        return fluentPut(FIELD_NAME_METHOD_NAME, methodName);
    }

    /**
     * 系统异常监控，增加类名参数
     *
     * @param className
     * @return
     */
    public LogMonitor className(String className) {
        return fluentPut(FIELD_NAME_CLASS_NAME, className);
    }

    /**
     * 静态方法， 获取一个实例.
     *
     * @param monitorCode 业务场景的code
     * @param monitorDesc 业务场景的描述
     * @return
     */
    @Deprecated
    public static LogMonitor getInstance(@Required String monitorCode, @Required String monitorDesc) {
        return new LogMonitor(monitorCode, monitorDesc);
    }

    private LogMonitor(Logger logger) {
        this.logger = logger;
    }

    /**
     * 创建一个日志监控类（废弃）,指定日志文件名
     * {@link #createSysExMonitor}
     *
     * @return 日志监控类
     */
    @Deprecated
    public static LogMonitor create(String loggerName) {
        Logger logger = LoggerFactory.getLogger(loggerName);
        return new LogMonitor(logger);
    }

    /**
     * 创建一个系统异常的日志监控类，日志默认打印至主日志中
     *
     * @return 日志监控类
     */
    public static LogMonitor createSysExMonitor() {
        return create();
    }

    /**
     * 创建一个系统异常的日志监控类，可指定系统异常打印的日志文件
     *
     * @return 日志监控类
     */
    public static LogMonitor createSysExMonitor(String loggerName) {
        return create(loggerName);
    }

    /**
     * 创建一个系统异常的日志监控类，可指定系统异常打印的日志文件
     *
     * @return 日志监控类
     */
    public static LogMonitor createSysExMonitor(Logger logger) {
        return create(logger);
    }

    private LogMonitor(String monitorCode, String monitorDesc, Logger logger) {
        this.monitorCode = monitorCode;
        this.monitorDesc = monitorDesc;
        this.logger = logger;
    }


    /**
     * 创建一个日志监控类，可指定业务监控日志名称，监控code和监控描述(废弃)
     * {@link #createBizMonitor}
     *
     * @param loggerName  日志名称
     * @param monitorCode 监控code
     * @param monitorDesc 监控描述
     * @return 日志监控类
     */
    @Deprecated
    public static LogMonitor create(String loggerName, String monitorCode, String monitorDesc) {
        Logger logger = LoggerFactory.getLogger(loggerName);
        return new LogMonitor(monitorCode, monitorDesc, logger);
    }


    /**
     * 创建一个普通的业务监控类，可指定业务监控日志名称，监控code和监控描述
     *
     * @param loggerName  日志名称
     * @param monitorCode 监控code
     * @param monitorDesc 监控描述
     * @return 日志监控类
     */
    public static LogMonitor createBizMonitor(String loggerName, String monitorCode, String monitorDesc) {
        return create(loggerName, monitorCode, monitorDesc);
    }
    /**
     * 创建一个普通的业务监控类，默认打印到应用主日志，监控code和监控描述
     *
     * @param monitorCode 监控code
     * @param monitorDesc 监控描述
     * @return 日志监控类
     */
    public static LogMonitor createBizMonitor(String monitorCode, String monitorDesc) {
        return new LogMonitor(monitorCode,monitorDesc);
    }

    /**
     * 创建一个日志监控类，可指定业务监控日志名称，监控code和监控描述（废弃）
     * {@link #createBizMonitor}
     *
     * @param logger      日志名称
     * @param monitorCode 监控code
     * @param monitorDesc 监控描述
     * @return 日志监控类
     */
    @Deprecated
    public static LogMonitor create(Logger logger, String monitorCode, String monitorDesc) {
        return new LogMonitor(monitorCode, monitorDesc, logger);
    }

    /**
     * 创建一个普通的业务监控类，可指定业务监控日志名称，监控code和监控描述
     *
     * @param logger      日志名称
     * @param monitorCode 监控code
     * @param monitorDesc 监控描述
     * @return 日志监控类
     */
    public static LogMonitor createBizMonitor(Logger logger, String monitorCode, String monitorDesc) {
        return create(logger, monitorCode, monitorDesc);
    }

    /**
     * 创建一个日志监控类，默认打印至主日志文件中(废弃)
     * {@link #createSysExMonitor}
     *
     * @return 日志监控类
     */
    @Deprecated
    public static LogMonitor create() {
        return new LogMonitor();
    }

    /**
     * 创建一个日志监控类，指定日志文件类(废弃)
     * {@link #createSysExMonitor}
     *
     * @return 日志监控类
     */
    @Deprecated
    public static LogMonitor create(Logger logger) {
        return new LogMonitor(logger);
    }

    /**
     * 设置key-value日志内容
     *
     * @param key
     * @param value
     * @return Object
     */
    public void put(String key, Object value) {
        this.map.put(key, value);
    }

    /**
     * 流式写法 设置key-value日志内容
     *
     * @param key
     * @param value
     * @return LogMonitor
     */
    public LogMonitor fluentPut(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

    /**
     * 流式写法 增加一个keyword
     *
     * @param keyword 关键子
     * @return LogMonitor
     */
    public LogMonitor addKeyWord(String keyword) {
        this.map.put(keyword, null);
        return this;
    }

    public LogMonitor info() {
        logLevel = "info";
        return this;
    }

    public LogMonitor warn() {
        logLevel = "warn";
        return this;
    }

    public LogMonitor error() {
        logLevel = "error";
        return this;
    }

    /**
     * 打印信息
     */
    public void log() {
        printLog(this.formatLogContent(), logLevel == null ? "info" : logLevel);
    }

    public void log(Throwable e) {
        log(e, true);
    }

    /**
     * 需要直接调用，只能打印直接调用者的类名和方法名
     * <p>
     * 异常监控格式：eagleEyeId || 监控编号 || 监控描述 || 异常名称 || 类名 || 方法名 ||  异常堆栈 || 其他信息
     *
     * @param e          异常信息
     * @param printStack 是否打印异常堆栈 默认打印
     */
    public void log(Throwable e, boolean printStack) {
        if (null != e) {
            StringBuilder stringBuilder = buildBaseLogInfo();

            // 异常名称
            String exceptionName = e.getClass().getSimpleName();
            stringBuilder.append(this.separator).append(FIELD_NAME_EXCEPTION_NAME).append(
                this.kvSeparator).append(exceptionName);
            // 类名
            String className = null;
            if (map.containsKey(FIELD_NAME_CLASS_NAME)) {
                className = String.valueOf(map.get(FIELD_NAME_CLASS_NAME));
            } else {
                className = Thread.currentThread().getStackTrace()[3].getClassName();
            }
            stringBuilder.append(this.separator).append(FIELD_NAME_CLASS_NAME).append(
                this.kvSeparator).append(className);

            // 方法名
            String methodName = null;
            if (map.containsKey(FIELD_NAME_METHOD_NAME)) {
                methodName = String.valueOf(map.get(FIELD_NAME_METHOD_NAME));
            } else {
                methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
            }
            stringBuilder.append(this.separator).append(FIELD_NAME_METHOD_NAME).append(
                this.kvSeparator).append(methodName);

            // kv信息增加异常堆栈
            String stackTraceAsString = ExceptionUtils.getStackTrace(e);
            if (StringUtils.isNotEmpty(stackTraceAsString)) {
                String newStackTraceAsString = getValueAbleStack(stackTraceAsString,
                    String.valueOf(map.get(FIELD_NAME_METHOD_NAME)));
                stringBuilder.append(this.separator).append(FIELD_NAME_STACK_TRACE).append(
                    this.kvSeparator).append(newStackTraceAsString);
            }

            // kv信息格式化
            for (Map.Entry<String, Object> entry : this.map.entrySet()) {
                if (null == entry.getValue()) {
                    stringBuilder.append(this.separator).append(entry.getKey());
                } else {
                    stringBuilder.append(this.separator).append(
                        entry.getKey()).append(this.kvSeparator).append(entry.getValue());
                }
            }
            // 打印日志
            if (printStack) {
                printLog(stringBuilder.toString(), e, logLevel == null ? "error" : logLevel);
            } else {
                printLog(stringBuilder.toString(), logLevel == null ? "error" : logLevel);
            }

        }
    }

    private void printLog(String content, String logLevel) {
        switch (logLevel) {
            case "warn":
                getLogger().warn(content);
                break;
            case "error":
                getLogger().error(content);
                break;
            default:
                getLogger().info(content);
                break;
        }
    }

    private void printLog(String content, Throwable e, String logLevel) {

        if (e == null) {
            printLog(content, logLevel);
            return;
        }
        switch (logLevel) {
            case "warn":
                getLogger().warn(content, e);
                break;
            case "error":
                getLogger().error(content, e);
                break;
            default:
                getLogger().info(content, e);
                break;
        }
    }

    /**
     * 获取有价值的堆栈信息
     *
     * @param stackTraceAsString 异常堆栈
     * @param methodName         方法
     * @return
     */
    private String getValueAbleStack(String stackTraceAsString, String methodName) {

        if (StringUtils.isEmpty(methodName)) {
            return stackTraceAsString;
        }
        if (StringUtils.isNotEmpty(stackTraceAsString)) {
            String newStackTraceAsString = stackTraceAsString.replace("\n", " ").
                replace("\t", " ");
            int length = newStackTraceAsString.length();

            // 提取方法名前后500个字符
            if (newStackTraceAsString.contains(methodName)) {
                int index = newStackTraceAsString.indexOf(methodName);
                int startIndex = index - MAX_STACK_TRACE_SIZE / 2;
                if (startIndex < 0) {
                    startIndex = 0;
                }
                int endIndex = index + MAX_STACK_TRACE_SIZE / 2;
                if (endIndex > length - 1) {
                    endIndex = length - 1;
                }
                newStackTraceAsString = newStackTraceAsString.substring(startIndex, endIndex);
            }

            // 最大长度控制
            if (newStackTraceAsString.length() < MAX_STACK_TRACE_SIZE) {
                return newStackTraceAsString + "...";
            } else {
                return newStackTraceAsString.substring(0, MAX_STACK_TRACE_SIZE) + "...";
            }
        }
        return null;
    }

}


