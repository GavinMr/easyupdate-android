package com.gary.android.easyupdate;

public final class EasyUpdateConfig {

    private final boolean updateOnlyWifi;
    private final boolean deltaUpdate;
    private final EasyUpdatePlugin updatePlugin;

    private EasyUpdateConfig(Builder builder) {
        updateOnlyWifi = builder.updateOnlyWifi;
        deltaUpdate = builder.deltaUpdate;
        updatePlugin = builder.updatePlugin;
    }

    public EasyUpdatePlugin getUpdatePlugin() {
        return updatePlugin;
    }

    public boolean isUpdateOnlyWifi() {
        return updateOnlyWifi;
    }

    public boolean isDeltaUpdate() {
        return deltaUpdate;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {

        private boolean updateOnlyWifi = true;
        private boolean deltaUpdate = false;
        private EasyUpdatePlugin updatePlugin;

        /**
         * 设置更新的网络条件
         * @param updateOnlyWifi 布尔值true(默认)只在wifi环境下检测更新，false在所有网络环境中均检测更新。
         */
        public Builder setUpdateOnlyWifi(boolean updateOnlyWifi) {
            this.updateOnlyWifi = updateOnlyWifi;
            return this;
        }

        /**
         * 设置增量更新
         * @param deltaUpdate 布尔值true(默认)使用增量更新，false使用全量更新。
         */
        public Builder setDeltaUpdate(boolean deltaUpdate) {
            this.deltaUpdate = deltaUpdate;
            return this;
        }

        /**
         * 设置检查更新的插件
         * @param updatePlugin
         */
        public Builder setUpdatePlugin(EasyUpdatePlugin updatePlugin) {
            this.updatePlugin = updatePlugin;
            return this;
        }

        public EasyUpdateConfig build() {
            return new EasyUpdateConfig(this);
        }
    }
}
