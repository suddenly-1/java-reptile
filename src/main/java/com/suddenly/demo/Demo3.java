package com.suddenly.demo;

import com.suddenly.entity.User;
import com.suddenly.entity.UserFrom;
import com.suddenly.util.DownloadVideo;
import com.suddenly.util.date.MyDateUtil;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Demo3 {

    public static void main(String[] args) throws ParseException {
//        String startDate = MyDateUtil.DateConvertString(new Date());
//        String url = "https://ugcws.video.gtimg.com/uwMROfz2r57AoaQXGdGnCmddJ6n7RsTqDrcQApy_tdLk6-3k/shg_93386895_50001_0191a03a3eca33b192a4bb3910ed0136.f20611100.1.mp4?sdtfrom=v1010&guid=f8bded9098376dc20ff78c9b8022245c&vkey=1CF82ED51945DD7E564D4D305F132D778876CC1ADBD7FCC032D985A48D99D9C9678E2E9C282E46ED1B47C2E60F115FB2BC4DC7A84C1E661CA393E0644780FA2837713B906C960EBBAF277C5D70F8269C579E031DE6CDBA7A09233751BC06ADA9F514F84B32D12137B7BAC52B08438C76D138557759C74A898601846C3668200B";
//        System.out.println(DownloadVideo.httpDownload(url,"D:\\MyFolder\\111\\DownLoadTest\\demo.mp4"));
//        String endDate = MyDateUtil.dateConvertString(new Date());

//        String timeDifference = MyDateUtil.getTimeDifference(startDate, endDate);
//        String timeDifference = MyDateUtil.getTimeDifference("2020-08-06 17:22:22", "2020-08-07 18:32:32");
//        System.out.println(timeDifference);


        //对象复制
        //1.获取mapperFactory对象
//        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
//        User user = new User("小明", "18", "男");
//        //复制对象(属性名不同) 方法
//        //首先使用classMap将两个类的字节码存好,使用field将双方名字对应,若有多个不同可连续使用field,使用byDefault将其余相同名字的自动拷贝(如果不使用则只会拷贝被field配置的属性),最后使用register使其生效
//        mapperFactory.classMap(User.class,UserFrom.class).field("sex1", "sex").byDefault().register();
//        UserFrom userFrom = mapperFactory.getMapperFacade().map(user, UserFrom.class);
//        System.out.println(userFrom);
//        738817257827864609		东方电气股份有限公司	1	1	86.80	2020-08-11 10:00:04	1		2020-08-10 09:58:33	system	system	2020-08-11 10:00:04
    }
}

