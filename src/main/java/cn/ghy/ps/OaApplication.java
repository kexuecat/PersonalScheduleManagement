package cn.ghy.ps;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.InputStream;
import java.util.zip.ZipInputStream;

/**
 * @author 穹鏡
 * @描述 启动类
 *
 * @date 2021/9/23 10:53
 */
@SpringBootApplication
@MapperScan("cn.ghy.ps.project.mapper")
//开启定时任务
@EnableScheduling
//开启缓存
@EnableCaching
public class OaApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(OaApplication.class, args);

    }

}
