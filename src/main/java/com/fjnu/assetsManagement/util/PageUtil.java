package com.fjnu.assetsManagement.util;

import lombok.Data;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("hiding")
@Data
public class PageUtil<T> {
    private List<T> list;//分页完的集合
    private int pageNum = 1;//当前页数
    private int pageSize = 10;//每页数量
    private int size;//总记录数
    private int pages;//总页数
    private int navigatePages = 8;//导航页数
    private int[] navigatePageNums;//所有导航页码

    private boolean isFirst = false;//是否第一条
    private boolean isLast = false;//是否最后一条
    private boolean hasPreviousPage = false;//是否有上一页
    private boolean hasNextPage = false;//是否有下一页


    //构造函数——当前页码，每页数据数量
    public PageUtil() {

    }

    public PageUtil(int size, int pageNum, int pageSize) {
        this.init(size, pageNum, pageSize);
    }

    //初始化参数以及调整
    public void init(int si, int pN, int pS) {
        //参数判断及赋值
        this.size = si;
        if (pS > 0 && pS <= this.size) {
            this.pageSize = pS;
        } else if (pS > this.size) {
            this.pageSize = this.size;
        }
        if (this.size % this.pageSize == 0) {
            this.pages = this.size / this.pageSize;
        } else
            this.pages = (this.size / this.pageSize) + 1;

        if (pN > 0 && pN <= this.pages) {
            this.pageNum = pN;
        } else if (pN > this.pages) {
            this.pageNum = this.pages;
        }

        //设置导航页数
        this.caclNavigatePageNums();
        //判断边界
        this.judgePageBoudary();
    }

    //设置导航页数
    private void caclNavigatePageNums() {
        //当总页数小于导航页数
        if (this.pages <= this.navigatePages) {
            this.navigatePageNums = new int[pages];
            for (int i = 0; i < this.pages; i++)
                this.navigatePageNums[i] = i + 1;
        } else {
            this.navigatePageNums = new int[this.navigatePages];
            int[] cur = new int[this.navigatePages];
            int startNum = this.pageNum - this.navigatePages / 2;
            int endNum = this.pageNum + this.navigatePages / 2;

            if (startNum < 1) {
                startNum = 1;
                for (int i = 0; i < this.navigatePages; i++)
                    cur[i] = startNum++;
            }
            if (endNum > this.pages) {
                for (int i = this.navigatePages - 1; i >= 0; i--)
                    cur[i] = endNum--;
            }
            if (startNum > 1 && endNum < this.pages) {
                for (int i = 0; i < this.navigatePages; i++)
                    cur[i] = i + 1;
            }
            int j = 0;
            for (int i = 0; i < cur.length; i++) {
                if (cur[i] > 0) {
                    this.navigatePageNums[j++] = cur[i];
                }
            }
        }
    }

    //判断边界
    private void judgePageBoudary() {
        if (this.pageNum == 1) {
            this.isFirst = true;
        }
        if (this.pageNum == this.pages) {
            this.isLast = true;
        }
        if (this.pageNum > 1) {
            this.hasPreviousPage = true;
        }
        if (this.pageNum < this.pages) {
            this.hasNextPage = true;
        }
    }

    @Override
    public String toString() {
        return "PageUtil{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", size=" + size +
                ", pages=" + pages +
                ", navigatePages=" + navigatePages +
                ", navigatePageNums=" + Arrays.toString(navigatePageNums) +
                ", isFirst=" + isFirst +
                ", isLast=" + isLast +
                ", hasPreviousPage=" + hasPreviousPage +
                ", hasNextPage=" + hasNextPage +
                '}';
    }

    public static void main(String[] args) {
        Double d1 = 0d;
        Double d2 = 0.00d;
        Double d3 = 0.000d;
        Double d4 = 1d;
        Double dd = 1.23d;
        Double d5 = 1.111d;
        BigDecimal bg1 = new BigDecimal(d1);
        BigDecimal bg2 = new BigDecimal(d2);
        BigDecimal bg3 = new BigDecimal(d3);
        BigDecimal bg4 = new BigDecimal(d4);
        BigDecimal bgd = new BigDecimal(dd);
        BigDecimal bg5 = new BigDecimal(d5);
        Double ad1 = bg1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        Double ad2 = bg2.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        Double ad3 = bg3.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        Double ad4 = bg4.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        Double add = bgd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        Double ad5 = bg5.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println("d1:" + d1 + "   ");
        System.out.println(ad1);
        System.out.println("d2:" + d2 + "   ");
        System.out.println(ad2);
        System.out.println("d3:" + d3 + "   ");
        System.out.println(ad3);
        System.out.println("d4:" + d4 + "   ");
        System.out.println(ad4);
        System.out.println("dd:" + dd + "   ");
        System.out.println(add);
        System.out.println("d5:" + d5 + "   ");
        System.out.println(ad5);
        System.out.println("===========第二种==========");
        DecimalFormat df = new DecimalFormat("0.00");
        String temp = df.format(d1);
        System.out.println(temp);
        Double ddd1 = Double.parseDouble(temp);
        System.out.println(ddd1);
    }
}
