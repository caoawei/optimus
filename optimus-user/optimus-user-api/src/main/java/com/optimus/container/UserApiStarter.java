package com.optimus.container;

import com.optimus.common.ResetApiStarter;
import com.optimus.module.order.dal.entity.Order;
import com.optimus.module.order.dal.mapper.OrderMapper;
import com.optimus.mvc.config.DataSourceConfig;
import com.optimus.mvc.config.WebConfig;
import com.optimus.utils.SpringMvcUtil;
import com.optimus.utils.Utils;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages="com.*")
@MapperScan(basePackages = "com.optimus.module.*.dal.mapper")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.optimus.module.*.api")
@Import({DataSourceConfig.class, WebConfig.class})
public class UserApiStarter {

    static Logger logger = LoggerFactory.getLogger(UserApiStarter.class);
    public static void main(String[] args) throws Exception {
        ResetApiStarter.start(MethodHandles.lookup().lookupClass());

        test();
    }

    public static void test() {

        OrderMapper orderMapper = SpringMvcUtil.getBean(OrderMapper.class);
        long projectId = 1510000001;
        long couponId = 2110000001L;
        long userId = 1910000001L;
        List<Long> userIds = new ArrayList<>();
        for (int i=0;i<=10000;i++) {
            userIds.add(userId++);
        }

        boolean[] useCoupon = new boolean[]{true,false};

        boolean[] useReward = new boolean[]{true,false};

        int[] status = new int[]{-1,0,1,2};
        String[] projectNames = new String[]{"惠民贷20181030第","消费贷20181030第","房盈通20181030第","供应链20181030第"};
        int i=1;
        ThreadLocalRandom random = ThreadLocalRandom.current();
        while (i<= 5000000) {
            Order order = new Order();
            order.setProjectId(projectId++);
            order.setProjectName(projectNames[random.nextInt(projectNames.length)]+(i)+"期");
            order.setUserId(userIds.get(random.nextInt(userIds.size())));
            if(useCoupon[random.nextInt(useCoupon.length)]) {
                order.setCouponId(couponId++);
                order.setCouponAmount((random.nextLong(100)+1)*100);
            }
            if(useReward[random.nextInt(useReward.length)]) {
                order.setRewardAmount((random.nextLong(500)+1)*100);
            }

            order.setOrderAmount((random.nextLong(10000)+1000)*100);

            order.setPayAmount(order.getOrderAmount());
            if(order.getCouponAmount() != null && order.getCouponAmount() > 0) {
                order.setPayAmount(order.getPayAmount()-order.getCouponAmount());
            }
            if(order.getRewardAmount() != null && order.getRewardAmount() > 0) {
                order.setPayAmount(order.getPayAmount()-order.getRewardAmount());
            }

            order.setStatus(status[random.nextInt(status.length)]);
            order.setGmtCreated(new Date());
            order.setGmtModify(new Date());
            orderMapper.insert(order);

            logger.info("[数据插入]第 {} 条,data:{}",(i++), Utils.toJson(order));
        }
    }
}
