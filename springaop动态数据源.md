BaseDataSourceConfig

```java
@Configuration
@MapperScan(basePackages = "com.esgov.gz12345.dao.mysql", sqlSessionTemplateRef = "baseSqlSessionTemplate" )
public class BaseDataSourceConfig {
    @Bean(name = "baseDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.base")
    @Primary
    public DataSource dataSource1() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "baseTransactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager1(@Qualifier("baseDataSource") DataSource dataSource) {
    	return new DataSourceTransactionManager(dataSource); 
    }

    @Bean(name = "baseSqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory1(@Qualifier("dynamicDS1") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        System.out.println("-----------in load mysql mapper----------");
//        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:com/esgov/gz12345/mapper/mysql/*.xml"));
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/**/*.xml"));
        System.out.println(bean.getObject().getConfiguration().getMappedStatementNames());
        System.out.println("-----------mysql mapper load end----------");
        return bean.getObject();
    }

    @Bean(name = "baseSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate1(@Qualifier("baseSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
    
    /**
     * 
     * @Title: pageHelper 
     * @Description: MyBatis分页插件PageHelper
     * @param @return    
     * @return PageHelper    返回类型 
     * @throws 
     *
     */
    @Bean(name="mysqlPageHelper")
    public PageHelper pageHelper(){
       // logger.info("MyBatis分页插件PageHelper");
        //分页插件
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("offsetAsPageNum", "true");
        properties.setProperty("rowBoundsWithCount", "true");
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);
        return pageHelper;
    }

}
```

DataSourceContextHolder

```java
public class DataSourceContextHolder {
	public static final Logger log = Logger.getLogger(DataSourceContextHolder.class);

    /**
     * 默认数据源
     */
    public static final String DEFAULT_DS = "baseDataSource";

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    // 设置数据源名
    public static void setDB(String dbType) {
//        log.info("切换到{}数据源:"+dbType);
//        System.out.println("切换到{}数据源:"+dbType);
        contextHolder.set(dbType);
    }

    // 获取数据源名
    public static String getDB() {
        return (contextHolder.get());
    }

    // 清除数据源名
    public static void clearDB() {
        contextHolder.remove();
    }

}
```

interface DS

```java
@Retention(RetentionPolicy.RUNTIME)
@Target({
        ElementType.METHOD
})
public @interface DS {
	String value() default "baseDataSource";
}
```

DynamicDataSource

```java
public class DynamicDataSource extends AbstractRoutingDataSource{
	   private static final Logger log = Logger.getLogger(DynamicDataSource.class);

	    @Override
	    protected Object determineCurrentLookupKey() {
	        //log.debug("数据源为{}:"+DataSourceContextHolder.getDB());

	        return DataSourceContextHolder.getDB();
	    }
}
```

DynamicDataSourceAspect

```java
@Aspect
@Component
public class DynamicDataSourceAspect {
	@Before("@annotation(DS)")
    public void beforeSwitchDS(JoinPoint point){

        //获得当前访问的class
        Class<?> className = point.getTarget().getClass();

        //获得访问的方法名
        String methodName = point.getSignature().getName();
        //得到方法的参数的类型
        Class[] argClass = ((MethodSignature)point.getSignature()).getParameterTypes();
        String dataSource = DataSourceContextHolder.DEFAULT_DS;
        try {
            // 得到访问的方法对象
            Method method = className.getMethod(methodName, argClass);
//            System.out.println("******in method "+method.getName()+"******");
            // 判断是否存在@DS注解
            if (method.isAnnotationPresent(DS.class)) {
                DS annotation = method.getAnnotation(DS.class);
                // 取出注解中的数据源名
                dataSource = annotation.value();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 切换数据源
        DataSourceContextHolder.setDB(dataSource);

    }


    @After("@annotation(DS)")
    public void afterSwitchDS(JoinPoint point){

        DataSourceContextHolder.clearDB();

    }

}

```

MyBatisConfig

```java
@Configuration
//加上这个注解，使得支持事务
@EnableTransactionManagement
public class MyBatisConfig implements TransactionManagementConfigurer {
    @Autowired
    private DataSource dataSource;

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum", "true");
        p.setProperty("rowBoundsWithCount", "true");
        p.setProperty("reasonable", "true");
        p.setProperty("dialect", "mysql");
        pageHelper.setProperties(p);
        return pageHelper;
    }
}
```

OracleDataSourceConfig

```java
@Configuration
@MapperScan(basePackages = "com.esgov.gz12345.dao.oracle", sqlSessionTemplateRef = "oracleSqlSessionTemplate")
public class OracleDataSourceConfig {
    @Bean(name = "oracleDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.oracleData")
    public DataSource dataSource2() {
        return DataSourceBuilder.create().build();
    }
    
    @Bean(name = "kmOracleDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.kmOracleData")
    public DataSource dataSource3() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 动态数据源: 通过AOP在不同数据源之间动态切换
     * @return
     */
    @Bean(name = "dynamicDS1")
//    @Primary
    public DataSource dataSource(@Qualifier("baseDataSource") DataSource dataSource1,
    		@Qualifier("oracleDataSource") DataSource dataSource2,
    		@Qualifier("kmOracleDataSource") DataSource dataSource3) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        // 默认数据源
        dynamicDataSource.setDefaultTargetDataSource(dataSource1);

        // 配置多数据源
        Map<Object, Object> dsMap = new HashMap<Object, Object>();
        dsMap.put("baseDataSource", dataSource1);
        dsMap.put("oracleDataSource", dataSource2);
        dsMap.put("kmOracleDataSource", dataSource3);

        dynamicDataSource.setTargetDataSources(dsMap);

        return dynamicDataSource;
    }
    
    @Bean(name = "oracleTransactionManager")
    public DataSourceTransactionManager transactionManager2(@Qualifier("oracleDataSource") DataSource dataSource) {
    	return new DataSourceTransactionManager(dataSource); 
    }

    @Bean(name = "oracleSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory2(@Qualifier("dynamicDS1") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        System.out.println("-----------in load oracle mapper----------");
//        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/oracle/MyTestDao.xml"));
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/**/*.xml"));
        System.out.println(bean.getObject().getConfiguration().getMappedStatementNames());
        System.out.println("-----------load oracle mapper end----------");
        
        return bean.getObject();
    }

    @Bean(name = "oracleSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate2(@Qualifier("oracleSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
    
    /**
     * 
     * @Title: pageHelper 
     * @Description: MyBatis分页插件PageHelper
     * @param @return    
     * @return PageHelper    返回类型 
     * @throws 
     *
     */
    @Bean(name="oraclePageHelper")
    public PageHelper pageHelper(){
       // logger.info("MyBatis分页插件PageHelper");
        //分页插件
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("offsetAsPageNum", "true");
        properties.setProperty("rowBoundsWithCount", "true");
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);
        return pageHelper;
    }
}
```

application.yml

```yml
spring:
    datasource:
       type: com.alibaba.druid.pool.DruidDataSource
#       base:
#          type: com.alibaba.druid.pool.DruidDataSource
#          url: jdbc:mysql://14.215.116.215:3306/gz12345_db?useUnicode=true&characterEncoding=utf8
#          username: gz12345
#          password: gz12345
#          driver-class-name: com.mysql.jdbc.Driver
#          name: MysqlDataSource
       base:
          type: com.alibaba.druid.pool.DruidDataSource
          url: jdbc:mysql://192.168.115.190:8011/gz_12345_new?useUnicode=true&characterEncoding=utf8
          username: gz12345
          password: gz12345?><123
          driver-class-name: com.mysql.jdbc.Driver
          name: MysqlDataSource
          initialSize: 5
          minIdle: 5
          maxActive: 20
          maxWait: 60000
          timeBetweenEvictionRunsMillis: 60000
          validationQuery: SELECT 1 FROM DUAL
          testWhileIdle: true
          testOnBorrow: false
          testOnReturn: false
          poolPreparedStatements: true
          maxPoolPreparedStatementPerConnectionSize: 10
             
       oracleData:
          type: com.alibaba.druid.pool.DruidDataSource
          url: jdbc:oracle:thin:@14.215.113.76:1521:orcl
          username: ALLMEDIA_RCXQ
          password: ALLMEDIA_RCXQ
          driver-class-name: oracle.jdbc.driver.OracleDriver
          initialSize: 5
          minIdle: 5
          maxActive: 20
          maxWait: 60000
          timeBetweenEvictionRunsMillis: 60000
          validationQuery: SELECT 1 FROM DUAL
          testWhileIdle: true
          testOnBorrow: false
          testOnReturn: false
          poolPreparedStatements: true
          maxPoolPreparedStatementPerConnectionSize: 10
             
       kmOracleData:
          type: com.alibaba.druid.pool.DruidDataSource
          url: jdbc:oracle:thin:@14.215.113.76:1521:orcl
          username: GZ12345_RCXQ
          password: GZ12345_RCXQ
          driver-class-name: oracle.jdbc.driver.OracleDriver
          initialSize: 5
          minIdle: 5
          maxActive: 20
          maxWait: 60000
          timeBetweenEvictionRunsMillis: 60000
          validationQuery: SELECT 1 FROM DUAL
          testWhileIdle: true
          testOnBorrow: false
          testOnReturn: false
          poolPreparedStatements: true
          maxPoolPreparedStatementPerConnectionSize: 10
```

使用

SysRoleSysnServiceImpl

```java
@Service
@Transactional
public class SysRoleSysnServiceImpl implements SysRoleSysnService {

	
	@Autowired
	private SysRoleMysqlDao sysRoleMysqlDao;
	
	@Autowired
	private SysRoleOracleDao sysRoleOracleDao;



	@Override
	@DS(value="baseDataSource")
	public List<SysRoleVo> querySysRoleVo() {
		// 当前页数，和每页列数
		return sysRoleMysqlDao.querySysRoleVo();
	}

	@Override
	@DS(value="kmOracleDataSource")
	public SysRole getRoleByCode(String code) {
		return sysRoleOracleDao.getRoleByCode(code);
	}

	@Override
	@DS(value="kmOracleDataSource")
	public Integer getKeyid() {
		return sysRoleOracleDao.getKeyid();
	}

	@Override
	@DS(value="kmOracleDataSource")
	public void saveList(List<SysRole> saveList) {
		sysRoleOracleDao.saveList(saveList);
	}

	@Override
	@DS(value="kmOracleDataSource")
	public void updateList(List<SysRole> updateList) {
		sysRoleOracleDao.updateList(updateList);
	}


	//	同步用户角色
	@Override
	@DS(value="baseDataSource")
	public List<SysRoleUserOrgVo> querySysRoleUserOrgVo(Integer start, Integer pageRow) {
		return sysRoleMysqlDao.querySysRoleUserOrgVo(start, pageRow);

	}

	@Override
	@DS(value="baseDataSource")
	public Integer queryRoleRelaNum() {
		return sysRoleMysqlDao.queryRoleRelaNum();
	}

	@Override
	@DS(value="kmOracleDataSource")
	public OracleKeyIdVo queryOracleKeyId(SysRoleUserOrgVo vo) {
		return sysRoleOracleDao.queryOracleKeyId(vo);
	}

	@Override
	@DS(value="kmOracleDataSource")
	public Integer getUserRoleKeyid() {
		return sysRoleOracleDao.getUserRoleKeyid();
	}

	@Override
	@DS(value="kmOracleDataSource")
	public SysUserRole getSysUserRole(Integer roleId, Integer userId) {
		return sysRoleOracleDao.getSysUserRole(roleId,userId);
	}



	@Override
	@DS(value="kmOracleDataSource")
	public void saveSysUserRoleList(List<SysUserRole> list) {
		sysRoleOracleDao.saveSysUserRoleList(list);
	}

	@Override
	@DS(value="kmOracleDataSource")
	public void updateSysUserRoleList(List<SysUserRole> list) {
		sysRoleOracleDao.updateSysUserRoleList(list);
	}

	@Override
	@DS(value="kmOracleDataSource")
	public void saveSysOrgRoleList(List<SysOrgRole> list) {
		sysRoleOracleDao.saveSysOrgRoleList(list);
	}

	@Override
	@DS(value="kmOracleDataSource")
	public void updateSysOrgRoleList(List<SysOrgRole> list) {
		sysRoleOracleDao.updateSysOrgRoleList(list);
	}

	@Override
	@DS(value="kmOracleDataSource")
	public SysOrgRole getSysOrgRole(Integer roleId, Integer orgId) {
		return sysRoleOracleDao.getSysOrgRole(roleId,orgId);
	}

}
```

SysRoleSysnMainServiceImpl

--这里不能用@Service注解

```java
@Component
public class SysRoleSysnMainServiceImpl implements SysRoleSysnMainService {
	//默认每页200
	private final Integer PAGEROW = 200;

	@Autowired
	private SysRoleSysnService sysRoleSysnService;

	@Override
	public void doUpdate() {
		List<SysRoleVo> sysRoleVos = sysRoleSysnService.querySysRoleVo();
		List<SysRole> saveList = new ArrayList<SysRole>();
		List<SysRole> updateList = new ArrayList<SysRole>();

		for (int i = 0; i <sysRoleVos.size() ; i++) {
			SysRoleVo sysRoleVo = sysRoleVos.get(i);
			SysRole role = sysRoleSysnService.getRoleByCode(sysRoleVo.getCode());
			if(role!=null){
				role.setName(sysRoleVo.getName());
				role.setStatus("1");
				role.setCompanyId(-1);
				role.setSystemType(1);
				role.setUpdateTime(new Date());
				role.setStaff("SysRoleDataSysJob");
				role.setSysMysqlId(sysRoleVo.getId());
				updateList.add(role);
			}else {
				role=new SysRole();
				Integer keyid = sysRoleSysnService.getKeyid();
				role.setId(keyid);
				role.setCode(sysRoleVo.getCode());
				role.setName(sysRoleVo.getName());
				role.setCreateTime(new Date());
				role.setStatus("1");
				role.setCompanyId(-1);
				role.setSystemType(1);
				role.setStaff("SysRoleDataSysJob");
				role.setSysMysqlId(sysRoleVo.getId());
				saveList.add(role);
			}
		}
		if(saveList.size()>0){
			sysRoleSysnService.saveList(saveList);
		}
		if (updateList.size()>0) sysRoleSysnService.updateList(updateList);
	}

	@Override
	public void updateRoleRela() {
		//获取机构总数
		Integer count =sysRoleSysnService.queryRoleRelaNum();
		//B、根据分页再取后续的数据，从第1页开始取
		int totalPage = count/PAGEROW+1;

		//循环去拿mysql的数据
		for (int i = 0; i <totalPage ; i++) {
			List<SysUserRole> saveList = new ArrayList<>();
			List<SysUserRole> updateList = new ArrayList<>();

			List<SysOrgRole> savesysOrgRoleList = new ArrayList<>();
			List<SysOrgRole> updatesysOrgRoleList = new ArrayList<>();



			List<SysRoleUserOrgVo> sysRoleUserOrgVos =  sysRoleSysnService.querySysRoleUserOrgVo(i * PAGEROW, 2);

			for (int j = 0; j <sysRoleUserOrgVos.size() ; j++) {
				SysRoleUserOrgVo vo = sysRoleUserOrgVos.get(j);
				OracleKeyIdVo oracleKeyIdVo =sysRoleSysnService.queryOracleKeyId(vo);


				//用户和角色,存在更新，不存在新增
				SysUserRole sysUserRole = sysRoleSysnService.getSysUserRole(oracleKeyIdVo.getRoleId(), oracleKeyIdVo.getUserId());
				if(sysUserRole==null){
					sysUserRole = new SysUserRole();
					Integer userRoleKeyid = sysRoleSysnService.getUserRoleKeyid();
					sysUserRole.setId(userRoleKeyid);
					sysUserRole.setRoleId(oracleKeyIdVo.getRoleId());
					sysUserRole.setUserId(oracleKeyIdVo.getUserId());
					sysUserRole.setStaff("SysRoleDataSysJob_updateRoleRela");
					sysUserRole.setSysMysqlId(vo.getId());
					saveList.add(sysUserRole);
				}else {
					sysUserRole.setStaff("SysRoleDataSysJob_updateRoleRela");
					sysUserRole.setSysMysqlId(vo.getId());
					updateList.add(sysUserRole);
				}


				if(vo.getOrgId()!=null){
					//机构和角色，不存在新增
					SysOrgRole sysOrgRole = sysRoleSysnService.getSysOrgRole(oracleKeyIdVo.getRoleId(), oracleKeyIdVo.getOrgId());

					if(sysOrgRole==null){
						sysOrgRole = new SysOrgRole();
						sysOrgRole.setRoleId(oracleKeyIdVo.getRoleId());
						sysOrgRole.setOrgId(oracleKeyIdVo.getOrgId());
						sysOrgRole.setStaff("SysRoleDataSysJob_updateRoleRela");
						sysOrgRole.setSysMysqlId(vo.getId());
						savesysOrgRoleList.add(sysOrgRole);
					}else {
						sysOrgRole.setStaff("SysRoleDataSysJob_updateRoleRela");
						sysOrgRole.setSysMysqlId(vo.getOrgId());
						updatesysOrgRoleList.add(sysOrgRole);
					}

				}




			}

			//角色用户
			if(saveList.size()>0)sysRoleSysnService.saveSysUserRoleList(saveList);
			if(updateList.size()>0)sysRoleSysnService.updateSysUserRoleList(updateList);

			if(savesysOrgRoleList.size()>0)sysRoleSysnService.saveSysOrgRoleList(savesysOrgRoleList);
			if(updatesysOrgRoleList.size()>0)sysRoleSysnService.updateSysOrgRoleList(updatesysOrgRoleList);
		}




	}

}
```

SysRoleMysqlDao

```java
public interface SysRoleMysqlDao {
	List<SysRoleVo> querySysRoleVo();


    List<SysRoleUserOrgVo> querySysRoleUserOrgVo(@Param("start") Integer start, @Param("pageRow") Integer pageRow);
    Integer queryRoleRelaNum();
}
```

SysRoleOracleDao

```java
public interface SysRoleOracleDao {
    Integer getKeyid();
    SysRole getRoleByCode(String code);

    void saveList(List<SysRole> list);
    void updateList(List<SysRole> list);



    //同步角色关系
    OracleKeyIdVo queryOracleKeyId(@Param("vo") SysRoleUserOrgVo vo);
    Integer getUserRoleKeyid();
    SysUserRole getSysUserRole(@Param("roleId") Integer roleId, @Param("userId") Integer userId);
    SysOrgRole getSysOrgRole(@Param("roleId") Integer roleId, @Param("orgId") Integer orgId);
    void saveSysUserRoleList(List<SysUserRole> list);
    void updateSysUserRoleList(List<SysUserRole> list);

    void saveSysOrgRoleList(List<SysOrgRole> list);
    void updateSysOrgRoleList(List<SysOrgRole> list);

}
```

entity

略过

mapper

SysRoleMysqlDao.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!-- mysql机构类 -->
<mapper namespace="com.esgov.gz12345.dao.mysql.SysRoleMysqlDao">

	<!-- 通用查询映射结果 -->
	<resultMap id="BaseResultMap" type="com.esgov.gz12345.entity.role.SysRoleVo">
		<result column="id" property="id" />
		<result column="name" property="name" />
		<result column="code" property="code" />
	</resultMap>


	<resultMap id="SysRoleUserOrgVo" type="com.esgov.gz12345.entity.role.SysRoleUserOrgVo">
		<result column="id" property="id" />
		<result column="account" property="account" />
		<result column="roleCode" property="roleCode" />
		<result column="roleId" property="roleId" />
		<result column="orgCode" property="orgCode" />
		<result column="orgId" property="orgId" />
	</resultMap>

<!--SysRoleVo机构类 -->
<select id="querySysRoleVo"  resultMap="BaseResultMap">
	select s.id,s.name,s.code from Sys_Role s WHERE s.status = 'SA'
</select>

<select id="queryRoleRelaNum" resultType="integer">
    SELECT COUNT(1) from (
    SELECT DISTINCT
    u.account,
    r.code roleCode,
    r.id roleId,
    g.code orgCode,
    g.id orgId
    FROM
        sys_user_role o
    LEFT JOIN sys_user u ON o.user_id = u.id
    LEFT JOIN sys_role r ON o.role_id = r.id
    LEFT JOIN sys_org g ON o.org_id = g.id where o.status = 'SA' and r.code is not null ) a
</select>


<select id="querySysRoleUserOrgVo"  resultMap="SysRoleUserOrgVo">
    SELECT DISTINCT
    o.id,
    u.account,
    r.code roleCode,
    r.id roleId,
    g.code orgCode,
    g.id orgId

    FROM
        sys_user_role o
    LEFT JOIN sys_user u ON o.user_id = u.id
    LEFT JOIN sys_role r ON o.role_id = r.id
    LEFT JOIN sys_org g ON o.org_id = g.id where o.status = 'SA' and r.code is not null  ORDER BY u.account LIMIT #{start},#{pageRow}
</select>




</mapper>
```

SysRoleOracleDao.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!-- mysql机构类 -->
<mapper namespace="com.esgov.gz12345.dao.oracle.SysRoleOracleDao">

	<!-- 通用查询映射结果 -->
	<resultMap id="BaseResultMap" type="com.esgov.gz12345.entity.role.SysRoleVo">
		<result column="name" property="name" />
		<result column="code" property="code" />
	</resultMap>

	<!-- 通用查询映射结果 -->
	<resultMap id="SysRoleMap" type="com.esgov.gz12345.entity.role.SysRole">
		<id column="id" property="id" />
		<result column="name" property="name" />
		<result column="code" property="code" />
		<result column="create_time" property="createTime" />
		<result column="status" property="status" />
		<result column="update_time" property="updateTime" />
		<result column="company_id" property="companyId" />
		<result column="system_type" property="systemType" />
		<result column="staff" property="staff" />
		<result column="sys_mysqlId" property="sysMysqlId" />
	</resultMap>


	<resultMap id="OracleKeyIdVo" type="com.esgov.gz12345.entity.role.OracleKeyIdVo">
		<result column="userId" property="userId" />
		<result column="roleId" property="roleId" />
		<result column="orgId" property="orgId" />
	</resultMap>

	<resultMap id="SysRoleUserOrgVo" type="com.esgov.gz12345.entity.role.SysRoleUserOrgVo">
		<result column="account" property="account" />
		<result column="roleCode" property="roleCode" />
		<result column="roleId" property="roleId" />
		<result column="orgCode" property="orgCode" />
		<result column="orgId" property="orgId" />
	</resultMap>

	<resultMap id="SysUserRole" type="com.esgov.gz12345.entity.role.SysUserRole">
		<id column="id" property="id" />
		<result column="role_id" property="roleId" />
		<result column="user_id" property="userId" />
		<result column="staff" property="staff" />
		<result column="sys_mysqlId" property="sysMysqlId" />
	</resultMap>

	<resultMap id="SysOrgRole" type="com.esgov.gz12345.entity.role.SysOrgRole">
		<result column="role_id" property="roleId" />
		<result column="org_id" property="orgId" />
		<result column="staff" property="staff" />
        <result column="sys_mysqlId" property="sysMysqlId" />
	</resultMap>

	<!--获取主键-->
	<select id="getKeyid" resultType="java.lang.Integer">
	  SELECT SEQ_SYS_ROLE.NEXTVAL FROM dual
	</select>

	<select id="getRoleByCode"  resultMap="SysRoleMap">
	 select s.id,s.name,s.code,s.create_time,s.status,s.update_time,s.company_id,s.system_type from sys_role s where s.code = #{code}
	</select>

	<!--oracle 批量插入新增-->
	<insert  id="saveList" parameterType="java.util.List"  useGeneratedKeys="false">
		insert into sys_role o
		(o.id,
		o.name,
		o.code,
		o.create_time,
		o.status,
		o.company_id,
		o.system_type,
		o.staff ,
        o.sys_mysqlId)
		<foreach collection ="list" item="role" index= "index"  separator="union all">
			(
			SELECT
			#{role.id},
			#{role.name},
			#{role.code},
			#{role.createTime},
			#{role.status},
			#{role.companyId},
			#{role.systemType},
			#{role.staff},
			#{role.sysMysqlId}
			FROM DUAL
			)
		</foreach >
	</insert >



	<update id="updateList" parameterType="java.util.List">
		<foreach collection="list" item="item" index="index" open="begin" close=";end;" separator=";">
			UPDATE sys_role
			<set>
				<if test="item.name != null">
					name = #{item.name},
				</if>
				<if test="item.code != null">
					code = #{item.code},
				</if>
				<if test="item.status != null">
					status = #{item.status},
				</if>
				<if test="item.updateTime != null">
					update_time = #{item.updateTime},
				</if>
				<if test="item.companyId != null">
					company_id = #{item.companyId},
				</if>
				<if test="item.systemType != null">
					system_type = #{item.systemType},
				</if>
				<if test="item.staff != null">
					staff = #{item.staff},
				</if>
				<if test="item.sysMysqlId != null">
                    sys_mysqlId = #{item.sysMysqlId},
				</if>
			</set>
			where id = #{item.id}
		</foreach>
	</update>


	<!--同步角色关系-->
	<select id="queryOracleKeyId"  resultMap="OracleKeyIdVo">
		select (select u.id from sys_user u where u.account = #{vo.account}) userId,
			   (select r.id from sys_role r where r.code = #{vo.roleCode}) roleId,
			   (select o.id from sys_org o where o.code = #{vo.orgCode}) orgId
		  from dual
	</select>
	<select id="getUserRoleKeyid" resultType="java.lang.Integer">
	  SELECT SEQ_SYS_USER_ROLE.NEXTVAL FROM dual
	</select>
	<select id="getSysUserRole" resultMap="SysUserRole">
	  SELECT ur.id,ur.role_id,ur.user_id,ur.staff from sys_user_role ur where ur.user_id = #{userId} and ur.role_id = #{roleId} and rownum = 1
	</select>

	<insert  id="saveSysUserRoleList" parameterType="java.util.List"  useGeneratedKeys="false">
		insert into sys_user_role o
		(o.id,
		o.role_Id,
		o.user_Id,
		o.staff,
		o.sys_mysqlId)
		<foreach collection ="list" item="sysUserRole" index= "index"  separator="union all">
			(
			SELECT
			#{sysUserRole.id},
			#{sysUserRole.roleId},
			#{sysUserRole.userId},
			#{sysUserRole.staff},
			#{sysUserRole.sysMysqlId}
			FROM DUAL
			)
		</foreach >
	</insert >

	<update id="updateSysUserRoleList" parameterType="java.util.List">
		<foreach collection="list" item="item" index="index" open="begin" close=";end;" separator=";">
			UPDATE sys_user_role
			<set>
				<if test="item.staff != null">
					staff = #{item.staff},
				</if>
				<if test="item.sysMysqlId != null">
					sys_mysqlId = #{item.sysMysqlId},
				</if>

			</set>
			where id = #{item.id}
		</foreach>
	</update>

    <!--更新机构和角色的关系-->
    <select id="getSysOrgRole" resultMap="SysOrgRole">
      SELECT so.role_id,so.org_id,so.staff,so.sys_mysqlid from sys_org_role so  where so.org_id = #{orgId} and so.role_id = #{roleId} and rownum = 1
	</select>

    <insert  id="saveSysOrgRoleList" parameterType="java.util.List"  useGeneratedKeys="false">
        insert into sys_org_role o
        (
        o.role_Id,
        o.org_id,
        o.staff,
        o.sys_mysqlId)
        <foreach collection ="list" item="sysOrgRole" index= "index"  separator="union all">
            (
            SELECT
            #{sysOrgRole.roleId},
            #{sysOrgRole.orgId},
            #{sysOrgRole.staff},
            #{sysOrgRole.sysMysqlId}
            FROM DUAL
            )
        </foreach >
    </insert >

    <update id="updateSysOrgRoleList" parameterType="java.util.List">
        <foreach collection="list" item="item" index="index" open="begin" close=";end;" separator=";">
            UPDATE sys_org_role
            <set>
                <if test="item.staff != null">
                    staff = #{item.staff},
                </if>
                <if test="item.sysMysqlId != null">
                    sys_mysqlId = #{item.sysMysqlId},
                </if>

            </set>
            where role_Id = #{item.roleId} and org_id = #{item.orgId}
        </foreach>
    </update>



</mapper>
```









