package com.grab.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;
import org.apache.ibatis.annotations.Mapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MpGenerator {

    public static void main(String[] args) {
        // 获取表名
        List<String> tables = getTableNames();
        // 数据库设置
        FastAutoGenerator.create("jdbc:p6spy:postgresql://localhost:5432/furion-contract-management?schema=public", "postgres", "123456")
                /**
                 * 全局配置
                 */
                .globalConfig(builder -> {
                    builder.author("robinson") // 作者
                            .disableOpenDir() // 禁止打开输出目录
                            .outputDir(System.getProperty("user.dir") + "\\src\\main\\java") // 指定输出目录 /opt/baomidou/ 默认值: windows:D:// linux or mac : /tmp
                            .enableSwagger() // 开启 swagger 模式  默认值:false
                            .dateType(DateType.TIME_PACK) // 时间策略   DateType.ONLY_DATE 默认值: DateType.TIME_PACK
                            .commentDate("yyyy-MM-dd"); // 注释日期   默认值: yyyy-MM-dd
                    // .fileOverride(); // 覆盖已有文件（已迁移到策略配置中，3.5.4版本会删除此方法）

                })
                /**
                 * 包配置
                 */
                .packageConfig(builder -> builder.parent("com.grab") // 父包名    默认值:com.baomidou
                        // .moduleName("com.qbb.security") // 父包模块名  默认值:无
                        .entity("domain") // Entity 包名  默认值:common
                        .service("service") // Service 包名 默认值:service
                        .serviceImpl("service.impl") // Service Impl 包名    默认值:service.impl
                        .controller("controller") // Controller 包名  默认值:controller
                        .mapper("mapper") // Mapper 包名




                        // 默认值:mapper
                        .xml("mapper") // Mapper XML 包名  默认值:mapper.xml
                        .pathInfo(Collections.singletonMap(OutputFile.xml, System.getProperty("user.dir") + "\\src\\main\\resources\\mapper"))) // xml的路径配置信息
                /**
                 * 策略配置
                 */
                .strategyConfig(builder -> builder.addInclude(tables)// 增加表匹配(内存过滤)	include 与 exclude 只能配置一项
                                // .enableCapitalMode() // 开启大写命名	默认值:false
                                // .enableSkipView() // 开启跳过视图	默认值:false
                                // .disableSqlFilter() // 禁用 sql 过滤	默认值:true，语法不能支持使用 sql 过滤表的话，可以考虑关闭此开关
                                // .enableSchema()    // 启用 schema	默认值:false，多 schema 场景的时候打开
                                // .likeTable(LikeTable)    // 模糊表匹配(sql 过滤)	likeTable 与 notLikeTable 只能配置一项
                                // .notLikeTable(LikeTable)    // 模糊表排除(sql 过滤)	likeTable 与 notLikeTable 只能配置一项
                                // .addExclude()    // 增加表排除匹配(内存过滤)	include 与 exclude 只能配置一项
                                .addTablePrefix("t_")
                                // .addTableSuffix(String...)	// 增加过滤表后缀
                                // .addFieldPrefix(String...)	// 增加过滤字段前缀
                                // .addFieldSuffix(String...)	// 增加过滤字段后缀

                                /**
                                 * service 策略配置
                                 */
                                .serviceBuilder()
                                .superServiceClass(IService.class)    // 设置 service 接口父类	BaseService.class
                                // .superServiceClass(String)    // 设置 service 接口父类	com.baomidou.global.BaseService
                                .superServiceImplClass(ServiceImpl.class)    // 设置 service 实现类父类	BaseServiceImpl.class
                                // .superServiceImplClass(String)    // 设置 service 实现类父类	com.baomidou.global.BaseServiceImpl
                                // .convertServiceFileName(ConverterFileName)    // 转换 service 接口文件名称
                                // .convertServiceImplFileName(ConverterFileName)    // 转换 service 实现类文件名称
                                .formatServiceFileName("%sService") // 格式化 service 接口文件名称
                                .formatServiceImplFileName("%sServiceImpl") // 格式化 service 实现类文件名称

                                /**
                                 * 实体策略配置
                                 */
                                .entityBuilder()
                                // .nameConvert(INameConvert)    // 名称转换实现
//                        .superClass(BaseEntity.class)    // 设置父类	BaseEntity.class
                                // .superClass(String)    // 设置父类	com.baomidou.global.BaseEntity
                                .disableSerialVersionUID()    // 禁用生成 serialVersionUID	默认值:true
                                // .enableColumnConstant()    // 开启生成字段常量	默认值:false
                                .enableChainModel()    // 开启链式模型	默认值:false
                                .enableRemoveIsPrefix()    // 开启 Boolean 类型字段移除 is 前缀	默认值:false
                                .enableTableFieldAnnotation()    // 开启生成实体时生成字段注解	默认值:false
                                // .enableActiveRecord()    // 开启 ActiveRecord 模型	默认值:false
//                        .versionColumnName("version")    // 乐观锁字段名(数据库)
                                // .versionPropertyName(String)    // 乐观锁属性名(实体)
//                        .logicDeleteColumnName("is_deleted")    // 逻辑删除字段名(数据库)
                                // .logicDeletePropertyName(String)    // 逻辑删除属性名(实体)
                                .naming(NamingStrategy.underline_to_camel)    // 数据库表映射到实体的命名策略	默认下划线转驼峰命名:NamingStrategy.underline_to_camel
                                // .columnNaming(NamingStrategy.underline_to_camel) // 数据库表字段映射到实体的命名策略	默认为 null，未指定按照 naming 执行
                                // .addSuperEntityColumns("id", "createTime", "updateTime", "statu") // 添加父类公共字段
                                // .addIgnoreColumns(String...)    // 添加忽略字段
                                .addTableFills(new Column("create_ts", FieldFill.INSERT))    // 添加表字段填充
                                .addTableFills(new Property("update_ts", FieldFill.INSERT_UPDATE))    // 添加表字段填充
                                .idType(IdType.AUTO)    // 全局主键类型
                                // .convertFileName(ConverterFileName)//	转换文件名称
                                // .formatFileName(String)    // 格式化文件名称
                                .enableLombok() // 开启 lombok 模型	默认值:false

                                /**
                                 * controller 策略配置
                                 */
                                .controllerBuilder()
//                        .superClass(BaseController.class) // 设置父类	BaseController.class
                                // .superClass(String) // 设置父类	com.baomidou.global.BaseController
                                .enableHyphenStyle() // 开启驼峰转连字符	默认值:false
                                .enableRestStyle() // 开启生成@RestController 控制器	默认值:false
                                // .convertFileName(ConverterFileName)    // 转换文件名称
                                .formatFileName("%sController")//	格式化文件名称

                                /**
                                 * mapper 策略配置
                                 */
                                .mapperBuilder()
                                .superClass(BaseMapper.class)    // 设置父类	BaseMapper.class
                                // .superClass(String)	//设置父类	com.baomidou.global.BaseMapper
                                // .enableMapperAnnotation()	//开启 @Mapper 注解	默认值:false WARN 因为现在mybatis-plus传递mybatis-spring依赖，这里是没问题的，但后面如果考虑脱离mybatis-spring的时候就需要把这里处理掉，建议使用mapperAnnotation方法来标记自己的注解。
                                .mapperAnnotation(Mapper.class)
                                .enableBaseResultMap()    // 启用 BaseResultMap 生成	默认值:false
                                .enableBaseColumnList()    // 启用 BaseColumnList	默认值:false
                                // .cache(Class<? extends Cache>)	//设置缓存实现类	MyMapperCache.class
                                // .convertMapperFileName(ConverterFileName)	//转换 mapper 类文件名称
                                //         .convertXmlFileName(ConverterFileName)	//转换 xml 文件名称
                                .formatMapperFileName("%sMapper")    // 格式化 mapper 文件名称
                                .formatXmlFileName("%sMapper")    // 格式化 xml 实现类文件名称
                )
                /**
                 * 模板配置
                 */
                .templateConfig(builder -> {
                    // WARN : 使用我们自定义模板 需要注意虽然我们文件是以ftl结尾，但是这里不要加上ftl,不然会报模板引擎找不到文件
                    builder
                            // .disable(TemplateType.ENTITY,TemplateType.SERVICE...) // 禁用模板
                            // .disable() // 禁用所有模板
                            .entity("/templates/myEntity.java") // 自定义entity模板
                            .controller("/templates/myControlle.java") // 自定义controller模板
                            .service("/templates/myService.java") // 自定义service模板
                            .serviceImpl("/templates/myServiceImpl.java") // 自定义servicei.mpl模板
                            .mapper("/templates/myMapper.java") // 自定义mapper模板
                            .xml("/templates/myMapper.xml"); // 自定义xml模板
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }


    /**
     * 读取控制台内容,获取用户输入的表名
     */
    public static List<String> getTableNames() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入表名(多个表名以逗号隔开):");
        String tableNames = scanner.nextLine();
        if (StringUtils.isNotBlank(tableNames)) {
            return Arrays.asList(tableNames.split(","));
        }
        throw new MybatisPlusException("请按照格式输入正确的表名！");
    }
}
