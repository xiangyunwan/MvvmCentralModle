package com.example.annotation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Descirption:
 * @Author zzz
 * @Date 2022/1/24
 **/
public class TestGeneric<T extends Number> {
    private T value;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    /**
     * ArrayList 必须是extends 第一个 第一个是类，后面是接口 ，类只有一个，接口可以多个
     * @param age
     * @param year
     * @param morth
     * @param <T>
     * @param <K>
     * @param <M>
     * @return
     */
    private <T,K,M extends ArrayList & Comparable & Serializable> K getAge(T age, K year, M morth){
        K realAge = year;
        return realAge;
    }


    private <T extends Number> void getName(T name){

    }

    private void getValue(TestGeneric<? extends Number> a){//通配符 ？ 只能用在方法中


    }

    public static void main(String[] args) {
        TestGeneric<Double>[] list=new TestGeneric[100];

       list[0].getValue(new TestGeneric<Float>());


    }
}
