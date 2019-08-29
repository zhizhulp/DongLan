package com.cn.danceland.myapplication.bean;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

/**
 * Created by shy on 2018/9/6 15:48
 * Email:644563767@qq.com
 */


public class JsonBean implements IPickerViewData {
    private long value;
    private String label;
    private List<Children> children;

    @Override
    public String toString() {
        return "JsonBean{" +
                "value=" + value +
                ", label='" + label + '\'' +
                ", children=" + children +
                '}';
    }

    public void setValue(long value) {
        this.value = value;
    }
    public long getValue() {
        return value;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }

    public void setChildren(List<Children> children) {
        this.children = children;
    }
    public List<Children> getChildren() {
        return children;
    }

    @Override
    public String getPickerViewText() {
        return this.getLabel();
    }

    public class Children {

        private long value;
        private String label;
        private List<Children> children;

        @Override
        public String toString() {
            return "Children{" +
                    "value=" + value +
                    ", label='" + label + '\'' +
                    ", children=" + children +
                    '}';
        }

        public void setValue(long value) {
            this.value = value;
        }
        public long getValue() {
            return value;
        }

        public void setLabel(String label) {
            this.label = label;
        }
        public String getLabel() {
            return label;
        }

        public List<Children> getChildren() {
            return children;
        }

        public void setChildren(List<Children> children) {
            this.children = children;
        }
    }
}
