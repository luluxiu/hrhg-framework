/**
 * 同步处理状态。<br>
 */
package com.integrity.framework.api.code;

import com.integrity.framework.utils.DataUtils;
import com.integrity.framework.utils.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 同步处理状态。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public interface SyncStatus {
    /**
     * 同步处理状态-未处理
     */
    String STATUS_INIT = "未处理";
    /**
     * 同步处理状态-处理中
     */
    String STATUS_PROCESSING = "处理中";
    /**
     * 同步处理状态-处理成功
     */
    String STATUS_SUCESS = "处理成功";
    /**
     * 同步处理状态-处理失败
     */
    String STATUS_FAILURE = "处理失败";

    /**
     * 类型。<br>
     *
     * @author 李海军
     * @since 1.0.0
     */
    enum Type implements CodeName {
        /**
         * 未处理
         */
        INIT(1, STATUS_INIT),
        /**
         * 处理中
         */
        PROCESSING(2, STATUS_PROCESSING),
        /**
         * 处理成功
         */
        SUCESS(3, STATUS_SUCESS),
        /**
         * 处理失败
         */
        FAILURE(9, STATUS_FAILURE);

        /**
         * 类型
         */
        private final int code;
        /**
         * 类型名称
         */
        private final String codeName;

        /**
         * 私有构造函数。<br>
         */
        Type() {
            this(1, STATUS_INIT);
        }

        /**
         * 私有构造函数。<br>
         *
         * @param code     类型值
         * @param codeName 类型名称
         */
        Type(int code, String codeName) {
            this.code = code;
            this.codeName = codeName;
        }


        /**
         * 获取编码。<br>
         *
         * @return 编码
         */
        @Override
        public int getCode() {
            return this.code;
        }

        /**
         * 获取编码名称。<br>
         *
         * @return 全编码
         */
        @Override
        public String getCodeName() {
            return this.codeName;
        }

        /**
         * 是否为当前类型。<br>
         *
         * @param code 枚举类型编码
         * @return true：是当前类型；false：不是当前类型
         */
        @Override
        public boolean isThisType(Integer code) {
            if (null == code) {
                return false;
            }

            return this.code == code;
        }

        /**
         * 根据类型值获取枚举类型。<br>
         *
         * @param code 类类型值
         * @return 枚举类型
         */
        public static Type fromCode(final Integer code) {
            if (null == code) {
                return null;
            }

            for (Type item : Type.values()) {
                if (item.code == code) {
                    return item;
                }
            }

            return null;
        }

        /**
         * 根据编码值，获取编码文案。<br>
         *
         * @param code 编码值
         * @return 编码文案
         */
        public static String codeText(final Boolean code) {
            if (null == code) {
                return StringUtils.EMPTY_STRING;
            }

            return codeText(code ? 1 : 0);
        }

        /**
         * 根据编码值，获取编码文案。<br>
         *
         * @param code 编码值
         * @return 编码文案
         */
        public static String codeText(final Integer code) {
            return codeText(fromCode(code));
        }

        /**
         * 根据编码类型，获取编码文案。<br>
         *
         * @param code 编码类型
         * @return 编码文案
         */
        public static String codeText(final Type code) {
            if (null == code) {
                // 编码类型为空
                return StringUtils.EMPTY_STRING;
            }

            return code.getCodeName();
        }

        /**
         * 生成所有类型集合。<br>
         *
         * @return 所有类型集合
         */
        public static Map<String, Integer> allTypeMap() {
            return allTypeMap(null);
        }

        /**
         * 生成所有类型集合。<br>
         *
         * @param exclude 排除类型
         * @return 所有类型集合
         */
        public static Map<String, Integer> allTypeMap(Type exclude) {
            // 生成所有类型集合
            Map<String, Integer> allTypes = new LinkedHashMap<>();

            for (Type type : Type.values()) {
                if (!DataUtils.isNullOrEmpty(exclude) && exclude == type) {
                    // 排除数据
                    continue;
                }

                // 枚举所有类型
                allTypes.put(type.getCodeName(), type.getCode());
            }

            return allTypes;
        }
    }
}