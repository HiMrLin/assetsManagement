package com.fjnu.assetsManagement.module.test.service;

import com.fjnu.assetsManagement.entity.Assets;
import com.fjnu.assetsManagement.entity.PurchaseDetail;
import com.fjnu.assetsManagement.entity.PurchaseMaster;
import com.fjnu.assetsManagement.entity.ResponseData;
import com.fjnu.assetsManagement.service.DataCenterService;
import com.fjnu.assetsManagement.util.ResponseDataUtil;
import com.google.common.collect.Lists;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Component
public class TestRequestBusinessService {
    @Autowired
    DataCenterService dataCenterService;
    @Autowired
    private SessionFactory sessionFactory;

    public boolean GenerateImage(String imgStr) { //对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) //图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }
            //生成jpeg图片
            String imgFilePath = "C:\\Users\\Administrator\\Desktop\\AssetsManagement\\barcode.jpg";//新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //测试从数据库取出条形码转化成图片
    public void testTransferImagesProcess() {
        Long cardId = dataCenterService.getData("cardId");

        Session session = sessionFactory.openSession();
        Assets assets = session.get(Assets.class, cardId);
        System.out.println(assets.getCode());

        this.GenerateImage(assets.getCode());
        //转化为图片


    }

    public void testRequestProcess() throws Exception {
        //从容器中获取数据
        String userName = dataCenterService.getData("userName");

        //测试插入一条数据
        PurchaseMaster purchaseMasterTest = new PurchaseMaster();
        List<PurchaseMaster> list = Lists.newArrayList();

        //设置Date格式
        SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd");
        DecimalFormat df = new DecimalFormat("0.00");
        purchaseMasterTest.setId(12302L);
        purchaseMasterTest.setOrderNo("122222");
        purchaseMasterTest.setTotalPrice(df.format(1000d));
        purchaseMasterTest.setOperator("yzq");
        //purchaseMasterTest.setRemark("测试插入");
        purchaseMasterTest.setState(0L);


        //测试一对多关系插入
        PurchaseDetail purchaseDetail1 = new PurchaseDetail();
        purchaseDetail1.setName("电脑");
        purchaseDetail1.setKind("电脑");
        purchaseDetail1.setQuantity(11L);
        purchaseDetail1.setUnitPrice(df.format(1000d));


        PurchaseDetail purchaseDetail2 = new PurchaseDetail();
        purchaseDetail2.setName("手机");
        purchaseDetail2.setKind("手机");
        purchaseDetail2.setQuantity(11L);
        purchaseDetail2.setUnitPrice(df.format(1000d));

//        Set<PurchaseDetail> purchaseDetailSet = new HashSet<>();
//        purchaseDetailSet.add(purchaseDetail1);
//        purchaseDetailSet.add(purchaseDetail2);
//        purchaseMasterTest.setPurchaseDetailSet(purchaseDetailSet);

        purchaseMasterTest.getPurchaseDetailSet().add(purchaseDetail1);
        purchaseMasterTest.getPurchaseDetailSet().add(purchaseDetail2);
        System.out.println(purchaseDetail1);
        System.out.println(purchaseDetail2);
        System.out.println(purchaseDetail1.hashCode());
        System.out.println(purchaseDetail2.hashCode());
        System.out.println(purchaseDetail2.equals(purchaseDetail1));
        System.out.println(purchaseMasterTest);
        purchaseDetail1.setPurchaseMaster(purchaseMasterTest);
        purchaseDetail2.setPurchaseMaster(purchaseMasterTest);
        list.add(purchaseMasterTest);
        //尝试插入表中
        Session testSession = sessionFactory.openSession();
        testSession.beginTransaction();
//        testSession.save(purchaseDetail1);
//        testSession.save(purchaseDetail2);
        testSession.save(purchaseMasterTest);


        testSession.getTransaction().commit();
        testSession.close();

        //操作完成后返回给前台数据
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "list", list);
    }
}
