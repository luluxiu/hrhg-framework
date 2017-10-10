/**
 * 终端设备类型。<br>
 */
package com.integrity.framework.api.code;


import com.integrity.framework.utils.StringUtils;

/**
 * 终端设备类型。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public interface DeviceType {
    /**
     * 设备终端类型-IOS
     */
    String DEV_TYPE_IOS = "iOS";
    /**
     * 设备终端类型-ANDROID
     */
    String DEV_TYPE_ANDROID = "Android";
    /**
     * 设备终端类型-PC
     */
    String DEV_TYPE_PC = "PC";

    /**
     * 类型。<br>
     *
     * @author 李海军
     * @since 1.0.0
     */
    enum Type implements CodeName {
        /**
         * IOS
         */
        IOS(1, DEV_TYPE_IOS),
        /**
         * ANDROID
         */
        ANDROID(2, DEV_TYPE_ANDROID),
        /**
         * PC
         */
        PC(3, DEV_TYPE_PC);

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
    }
}