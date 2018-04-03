package com.bs.afterservice.pickerview.other;

import java.util.ArrayList;

public class DataModel {

    /**
     * 初始化三个选项卡数据。
     */
    public static void initData(ArrayList<ProvinceBean> options1Items, ArrayList<ArrayList<String>> options2Items, ArrayList<ArrayList<ArrayList<String>>> options3Items) {

        //选项1
        options1Items.add(new ProvinceBean(0, "广东", "广东省，以岭南东道、广南东路得名", "有钱"));
        options1Items.add(new ProvinceBean(1, "湖南", "湖南省地处中国中部、长江中游，因大部分区域处于洞庭湖以南而得名湖南", "芒果TV"));
        options1Items.add(new ProvinceBean(2, "江西", "简称“赣”（gàn），又称江右、别称赣鄱大地", "老革命根据地"));

        //选项2
        ArrayList<String> options2Items_01 = new ArrayList<String>();
        options2Items_01.add("广州");
        options2Items_01.add("佛山");
        options2Items_01.add("东莞");
        options2Items_01.add("阳江");
        options2Items_01.add("珠海");

        ArrayList<String> options2Items_02 = new ArrayList<String>();
        options2Items_02.add("长沙");
        options2Items_02.add("岳阳");

        ArrayList<String> options2Items_03 = new ArrayList<String>();
        options2Items_03.add("南昌");
        options2Items_03.add("九江");
        options2Items.add(options2Items_01);
        options2Items.add(options2Items_02);
        options2Items.add(options2Items_03);

        //选项3
        /*广东省*/
        ArrayList<ArrayList<String>> options3Items_01 = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> options3Items_02 = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> options3Items_03 = new ArrayList<ArrayList<String>>();
        ArrayList<String> options3Items_01_01 = new ArrayList<String>();
        options3Items_01_01.add("白云");
        options3Items_01_01.add("天河");
        options3Items_01_01.add("海珠");
        options3Items_01_01.add("越秀");
        options3Items_01.add(options3Items_01_01);

        ArrayList<String> options3Items_01_02 = new ArrayList<String>();
        options3Items_01_02.add("南海");
        options3Items_01_02.add("高明");
        options3Items_01_02.add("顺德");
        options3Items_01_02.add("禅城");
        options3Items_01.add(options3Items_01_02);

        ArrayList<String> options3Items_01_03 = new ArrayList<String>();
        options3Items_01_03.add("塘厦");
        options3Items_01_03.add("常平");
        options3Items_01_03.add("虎门");
        options3Items_01_03.add("洪梅");
        options3Items_01.add(options3Items_01_03);

        ArrayList<String> options3Items_01_04 = new ArrayList<String>();
        options3Items_01_04.add("江城区");
        options3Items_01_04.add("阳东区");
        options3Items_01_04.add("阳春市");
        options3Items_01_04.add("阳西县");
        options3Items_01.add(options3Items_01_04);

        ArrayList<String> options3Items_01_05 = new ArrayList<String>();
        options3Items_01_05.add("香洲区");
        options3Items_01_05.add("金湾区");
        options3Items_01_05.add("斗门区");
        options3Items_01.add(options3Items_01_05);

        /*湖南省*/
        ArrayList<String> options3Items_02_01 = new ArrayList<String>();
        options3Items_02_01.add("芙蓉区");
        options3Items_02_01.add("天心区");
        options3Items_02_01.add("岳麓区");
        options3Items_02_01.add("开福区");
        options3Items_02_01.add("雨花区");
        options3Items_02_01.add("望城县");
        options3Items_02_01.add("宁乡县");
        options3Items_02_01.add("浏阳市");
        options3Items_02.add(options3Items_02_01);

        ArrayList<String> options3Items_02_02 = new ArrayList<String>();
        options3Items_02_02.add("岳阳楼区");
        options3Items_02_02.add("君山区");
        options3Items_02_02.add("云溪区");
        options3Items_02_02.add("汨罗市");
        options3Items_02_02.add("临湘市");
        options3Items_02_02.add("岳阳县");
        options3Items_02_02.add("平江县");
        options3Items_02_02.add("湘阴县");
        options3Items_02_02.add("华容县");
        options3Items_02.add(options3Items_02_02);

        /*江西省*/
        ArrayList<String> options3Items_03_01 = new ArrayList<String>();
        options3Items_03_01.add("西湖区");
        options3Items_03_01.add("东湖区");
        options3Items_03_01.add("湾里区");
        options3Items_03_01.add("青云谱区");
        options3Items_03_01.add("青山湖区");
        options3Items_03_01.add("新建区");
        options3Items_03_01.add("南昌县");
        options3Items_03_01.add("进贤县");
        options3Items_03_01.add("安义县");
        options3Items_03.add(options3Items_03_01);

        ArrayList<String> options3Items_03_02 = new ArrayList<String>();
        options3Items_03_02.add("浔阳区");
        options3Items_03_02.add("濂溪区");
        options3Items_03_02.add("九江县");
        options3Items_03_02.add("武宁县");
        options3Items_03_02.add("修水县");
        options3Items_03_02.add("永修县");
        options3Items_03_02.add("德安县");
        options3Items_03_02.add("都昌县");
        options3Items_03_02.add("湖口县");
        options3Items_03_02.add("彭泽县");
        options3Items_03_02.add("瑞昌市");
        options3Items_03_02.add("共青城市");
        options3Items_03_02.add("庐山市");
        options3Items_03.add(options3Items_03_02);

        options3Items.add(options3Items_01);
        options3Items.add(options3Items_02);
        options3Items.add(options3Items_03);
    }
}
