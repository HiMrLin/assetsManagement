package com.fjnu.assetsManagement.module.purchase.service;

import com.alibaba.druid.util.Base64;
import com.fjnu.assetsManagement.dao.SysDepartmentDao;
import com.fjnu.assetsManagement.entity.*;
import com.fjnu.assetsManagement.service.DataCenterService;
import com.fjnu.assetsManagement.util.BarcodeUtil;
import com.fjnu.assetsManagement.util.PageUtil;
import com.fjnu.assetsManagement.util.ResponseDataUtil;
import com.fjnu.assetsManagement.vo.SummaryAssets;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
@Transactional
public class PurchaseRequestBusinessService {

    @Autowired
    private DataCenterService dataCenterService;

    @Autowired
    HibernateTemplate hibernateTemplate;

    private SessionFactory sessionFactory;

    @Autowired
    private SysDepartmentDao sysDepartmentDao;


    //生成资产编码
    //详表ID+13位当前时间戳+随机两位0-9数字+当前同类别资产顺序
    public String genernateAssetsId(Long purchaseDetailId, int cur) {
        String pDI = purchaseDetailId.toString();
        int r1 = (int) (Math.random() * (10));//产生2个0-9的随机数
        int r2 = (int) (Math.random() * (10));
        long now = System.currentTimeMillis();//一个13位的时间戳
        //生成采购单号
        String assetsId = pDI + String.valueOf(now) + String.valueOf(r1) + String.valueOf(r2) + String.valueOf(cur);
        return assetsId;
    }

    //读取资产相关数据并进行存储
    public void addAssets(String depository, String ascription) throws IOException {

        sessionFactory = hibernateTemplate.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        Set<PurchaseDetail> purchaseDetailSet = dataCenterService.getData("purchaseDetailSet");

        int count = 0;
        for (PurchaseDetail purchaseDetail : purchaseDetailSet) {
            //得到详表ID和一些数据
            Long purchaseDetailId = purchaseDetail.getId();
            SummaryAssets summaryAssets = purchaseDetail.getSummaryAssets();
            //判断数量是否可以为多
            Dictionary dictionary = this.getDictionary(purchaseDetail.getKindId());
            if (dictionary.getQuantityState() == 2) {
                Assets assets = new Assets();
                //从summaryAssets获取部分信息
                //地区
                assets.setArea(summaryAssets.getArea());
                //货物归属
                assets.setAscription(ascription);
                //制造商
                assets.setMaker(summaryAssets.getMaker());
                //供应商
                assets.setSupplier(summaryAssets.getSupplier());
                //凭证号
                assets.setAccountId(summaryAssets.getAccountId());
                //保管人
                assets.setDepository(depository);
                //使用年限
                assets.setLifeFactor(summaryAssets.getLifeFactor());
                //保修年限
                assets.setFixYear(summaryAssets.getFixYear());

                //数量
                assets.setQuantity(purchaseDetail.getQuantity());
                //单价
                assets.setUnitPrice(purchaseDetail.getUnitPrice());
                //详表ID
                assets.setOrderDetailId(purchaseDetailId);
                //资产编码
                assets.setAssetsId(this.genernateAssetsId(purchaseDetailId, 1));
                //类别
                assets.setKind(purchaseDetail.getKind());
                //货物名称
                assets.setAssetsName(purchaseDetail.getName());


                //生成条形码
                String msg = assets.getAssetsId();
                byte[] codeByte = BarcodeUtil.generate(msg);
                //base64编码存入string从而存入数据库
                String codeImage = Base64.byteArrayToBase64(codeByte);
                assets.setCode(codeImage);
                session.save(assets);
            } else {
                //对于每条记录的每个个体创建资产对象和保存
                for (int i = 0; i < purchaseDetail.getQuantity(); i++, count++) {
                    Assets assets = new Assets();
                    //从summaryAssets获取部分信息
                    //地区
                    assets.setArea(summaryAssets.getArea());
                    //货物归属
                    assets.setAscription(ascription);
                    //制造商
                    assets.setMaker(summaryAssets.getMaker());
                    //供应商
                    assets.setSupplier(summaryAssets.getSupplier());
                    //凭证号
                    assets.setAccountId(summaryAssets.getAccountId());
                    //保管人
                    assets.setDepository(depository);
                    //使用年限
                    assets.setLifeFactor(summaryAssets.getLifeFactor());
                    //保修年限
                    assets.setFixYear(summaryAssets.getFixYear());

                    //数量
                    assets.setQuantity(1l);
                    //单价
                    assets.setUnitPrice(purchaseDetail.getUnitPrice());
                    //详表ID
                    assets.setOrderDetailId(purchaseDetailId);
                    //资产编码
                    assets.setAssetsId(this.genernateAssetsId(purchaseDetailId, i + 1));
                    //类别
                    assets.setKind(purchaseDetail.getKind());
                    //货物名称
                    assets.setAssetsName(purchaseDetail.getName());

                    //生成条形码
                    //1.生成图片文件
//                String msg = assets.getAssetsId();
//                String path = "C:\\Users\\Administrator\\Desktop\\AssetsManagement\\" + msg + ".png";
//                System.out.println(path);
//                File code = BarcodeUtil.generateFile(msg, path);
//                //获得文件输入流
//                FileInputStream input = new FileInputStream(code);
//                //创建一个Blob对象
//                Blob codeImage = Hibernate.getLobCreator(session).createBlob(input, input.available());
//                assets.setCode(codeImage);

                    //2.生成字节数组
                    String msg = assets.getAssetsId();
                    byte[] codeByte = BarcodeUtil.generate(msg);
                    //base64编码存入string从而存入数据库
                    String codeImage = Base64.byteArrayToBase64(codeByte);
                    assets.setCode(codeImage);
                    session.save(assets);

                    //定时清除缓存
                    if (count / 10 == 0) {
                        session.flush();
                        session.clear();
                    }
                }
            }


        }


    }

    //从数据字典根据ID得到数据字典
    public Dictionary getDictionary(Long id) {
        sessionFactory = hibernateTemplate.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Dictionary dictionary = session.get(Dictionary.class, id);
        return dictionary;
    }


    //添加采购主表记录以及采购详表记录
    public void addPurchaseItemServiceProcess() {

        sessionFactory = hibernateTemplate.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        //读取采购表相关数据
        //获取操作员以及采购单号
        Long operatorId = dataCenterService.getData("operatorId");
        //根据id得到部门id
        Session getDepartmentIdSession = sessionFactory.getCurrentSession();
        SysUser sysUser = getDepartmentIdSession.get(SysUser.class, operatorId);
        SysDepartment sysDepartment = sysUser.getSysDepartment();
        //根据部门ID确定保管人以及归属
        //保管人
        String depository;
        if(sysDepartment.getId() == 1)
            depository = "张雷";
        else if(sysDepartment.getId() == 2)
            depository = "吴必辉";
        else
            depository = "陈琪";

        //归属
        String ascription;
        SysUser operator = session.get(SysUser.class, operatorId);
        ascription = operator.getSysDepartment().getName();

        String orderNo = dataCenterService.getData("orderNo");
        String remark = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("remark");
        //获取采购的详细物品
        Set<PurchaseDetail> purchaseDetailSet = dataCenterService.getData("purchaseDetailSet");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        PurchaseMaster purchaseMaster = new PurchaseMaster();
        purchaseMaster.setOrderNo(orderNo);
        purchaseMaster.setOrderTime(sdf.format(new Date()));
        purchaseMaster.setOperatorId(operatorId);
        purchaseMaster.setState(0L);
        purchaseMaster.setRemark(remark);
        purchaseMaster.setDepartmentId(sysDepartment.getId());

        //建立关联
        Double totalPrice = 0.00d;
        for (PurchaseDetail purchaseDetail : purchaseDetailSet) {
            purchaseDetail.setKind(this.getDictionary(purchaseDetail.getKindId()).getKind());
            purchaseDetail.setPurchaseMaster(purchaseMaster);
            purchaseMaster.getPurchaseDetailSet().add(purchaseDetail);
            //计算总价
            totalPrice += Double.parseDouble(purchaseDetail.getUnitPrice()) * purchaseDetail.getQuantity();
            //测试输出
        }

        //dataCenterService.setData("totalPrice", totalPrice);
        DecimalFormat df = new DecimalFormat("0.00");
        purchaseMaster.setTotalPrice(df.format(totalPrice));

        //存入数据库

        session.flush();
        session.clear();
        session.save(purchaseMaster);

        //添加资产表
        try {
            this.addAssets(depository,ascription);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //分页方法
    public void getPageList(PageUtil purchaseMasterList, int pageNum, int pageSize, String className, String otherCondition) {
        sessionFactory = hibernateTemplate.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        //得到记录总数
        String getSizeHql = "select count(*) from " + className + " c " + otherCondition;
        Query getSizeQuery = session.createQuery(getSizeHql);
        Long sizeLong = (Long) ((org.hibernate.query.Query) getSizeQuery).uniqueResult();
        int size = sizeLong.intValue();

        purchaseMasterList.init(size, pageNum, pageSize);
        //得到记录
        int curPageSize = purchaseMasterList.getPageSize();
        int curPageNum = purchaseMasterList.getPageNum();
        int firstResult = (curPageNum - 1) * curPageSize;
//        System.out.println("第一条：" + firstResult);
//        System.out.println("取几条：" + curPageSize);
        String getListHql = "from " + className + " c " + otherCondition;
        Query getListQuery = session.createQuery(getListHql);
        getListQuery.setFirstResult(firstResult);
        getListQuery.setMaxResults(curPageSize);
        purchaseMasterList.setList(((org.hibernate.query.Query) getListQuery).list());


    }

    //得到采购单列表
    public void getPurchaseMasterListProcess() {

        //设置分页
        Integer pageSize = dataCenterService.getData("pageSize");
        Integer pageNum = dataCenterService.getData("pageNum");

        //hql语句其他条件
        String otherCondition = "WHERE c.existState<>0 or c.existState = null";
        PageUtil<PurchaseMaster> purchaseMasterList = new PageUtil<>();
        this.getPageList(purchaseMasterList, pageNum, pageSize, PurchaseMaster.class.getSimpleName(), otherCondition);

        //操作完成后返回给前台数据
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "purchaseMasterList", purchaseMasterList);
    }

    //根据采购单号得到采购单数据
    public void getPurchaseMasterListByOrderNoProcess() {
        sessionFactory = hibernateTemplate.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        String hql = "from PurchaseMaster p WHERE p.orderNo=:o and (p.existState<>0 or p.existState = null)";
        Query query = session.createQuery(hql);
        String orderNo = dataCenterService.getData("orderNo");
        ((org.hibernate.query.Query) query).setString("o", orderNo);
        PurchaseMaster purchaseMaster = (PurchaseMaster) ((org.hibernate.query.Query) query).uniqueResult();

        //操作完成后返回给前台数据
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "purchaseMaster", purchaseMaster);
    }

    //根据采购单号数组删除采购单
    public void deletePurchaseMasterListByOrderNoProcess() {
        sessionFactory = hibernateTemplate.getSessionFactory();
        //取得数据
        List<String> orderNoList = dataCenterService.getData("orderNoList");

        //进行逻辑删除——即把采购单记录的exist_state属性改为0
        Session session = sessionFactory.getCurrentSession();
        for (String orderNo : orderNoList) {
            String hql = "update PurchaseMaster p set p.existState=0 where p.orderNo=:o";
            Query query = session.createQuery(hql);
            ((org.hibernate.query.Query) query).setString("o", orderNo);
            query.executeUpdate();

        }
    }

}
