https://www.jianshu.com/p/f37f8c295057

https://blog.csdn.net/wang926454/article/details/82971846

https://www.cnblogs.com/yibutian/p/9508018.html

整合：

https://www.cnblogs.com/ll409546297/p/7815409.html

https://github.com/wang926454/MyDocs/blob/master/Project/ShiroJwt/ShiroJwt04-Redis.md

pom.xml

```xml
<!-- shiro start -->
        <dependency>
            <groupId>org.apache.shiro</groupId>
            <artifactId>shiro-spring</artifactId>
            <version>1.4.0</version>
        </dependency>
        <dependency>
            <groupId>net.sf.ehcache</groupId>
            <artifactId>ehcache</artifactId>
            <version>2.10.5</version>
        </dependency>
        <dependency>
            <groupId>org.crazycake</groupId>
            <artifactId>shiro-redis</artifactId>
            <version>2.4.2.1-RELEASE</version>
        </dependency>
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt</artifactId>
            <version>0.9.1</version>
        </dependency>
```

application.yml

```yml
jwt:
  header: Authorization
  secret: mySecret
  expiration: 604800
  route:
    authentication:
      path: /auth
      refresh: /refresh
```



ShiroConfiguration

```java
@Configuration
public class ShiroConfiguration {
    /**
     * 用户名分隔符
     */
    public static final String			SPLIT_COMA					= "#";
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        System.out.println("ShiroConfiguration.shirFilter()");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //拦截器.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/auth", "anon");
/*        filterChainDefinitionMap.put("/login/doLogin", "anon");
        filterChainDefinitionMap.put("/logout", "anon");
        filterChainDefinitionMap.put("/login/log", "anon");
        filterChainDefinitionMap.put("/resources/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/js/**.js", "anon");
        filterChainDefinitionMap.put("/login/getUserOrgs", "anon");
        filterChainDefinitionMap.put("/order/myInputTagPage", "anon");
        filterChainDefinitionMap.put("gzorder/getCode", "anon");
        filterChainDefinitionMap.put("gzorder/test", "anon");
        filterChainDefinitionMap.put("/vcode/random", "anon");
        filterChainDefinitionMap.put("/vcode/validate", "anon");*/
        // authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问
        //filterChainDefinitionMap.put("/**", "authc");
        filterChainDefinitionMap.put("/**", "anon");


        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login/log");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/");
        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/login/log");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(myShiroRealm());
        // 自定义缓存实现 使用redis
        //securityManager.setCacheManager(cacheManager());
        // 自定义session管理 使用redis
        //securityManager.setSessionManager(sessionManager());
        //注入记住我管理器;
        //securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }


    /**
     * 注入ShiroRealm
     * 不能省略，会导致Service无法注入
     */
    @Bean
    public ShiroDbRealm myShiroRealm() {

        return new ShiroDbRealm();
    }

    /**
     * cookie对象;
     *
     * @return
     */
    public SimpleCookie rememberMeCookie() {
        System.out.println("cookie11111");
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
        simpleCookie.setMaxAge(2592000);
        return simpleCookie;
    }

    /**
     * cookie管理对象;记住我功能
     *
     * @return
     */
    public CookieRememberMeManager rememberMeManager() {
        System.out.println("cookieManager11111");
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode("3AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }


}
```

ShiroDbRealm

```java
public class ShiroDbRealm extends AuthorizingRealm {
	
	private static final String OR_OPERATOR = " or ";

	@Autowired
	private SysUserFeign sysUserFeign;
	@Autowired
	private UserOrgMenuFeign userOrgMenuFeign;

	/**
	 * 用于权限认证
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
        SimpleAuthorizationInfo authrInfo = new SimpleAuthorizationInfo();
        //获取登录输入的用户名
        String username = principals.getPrimaryPrincipal().toString();
		SysUser sysUser = SysUserCacheHelper.getInstance().getSysUserByAccount(username);
        //查询角色菜单和按钮，作为权限加入authrInfo
		List<Map<String,Object>> list = null;
		try {
			list = (List<Map<String, Object>>) userOrgMenuFeign.queryMenu(sysUser.getOrgId(),sysUser.getId(),null).getData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Set<String> permissionSet = new HashSet<String>();
		if(list != null && list.size()>0){
			for (Map<String,Object> map : list) {
				String menuCode = (String) map.get("MENU_CODE");
//				String buttonCode = (String) map.get("buttonCode");
				//添加菜单一级权限
				if(!permissionSet.contains(menuCode)){
					permissionSet.add(menuCode);
				}
				//添加菜单+按钮权限，权限表达方式 -- menuCode:buttonCode
//				if(StringUtil.isNotEmpty(buttonCode) && !permissionSet.contains(menuCode+":"+buttonCode)){
//					permissionSet.add(menuCode+"-"+buttonCode);
//				}
			}
		}
		authrInfo.addStringPermissions(permissionSet);
		return authrInfo;
	}

	/**
	 * 身份验证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken userToken = (UsernamePasswordToken) token;
		//获取表单提交的用户名
		String username = userToken.getUsername();
		SysUser sysUser = SysUserCacheHelper.getInstance().getSysUserByAccount(username);
		if(sysUser != null){
			if(sysUser.getStatus().equals(ConstantsUtils.CONSTANTS_STATUS_SX)){
				throw new LockedAccountException();
			}
			// 将查询到的用户账号和密码存放到 authenticationInfo用于后面的权限判断
			SimpleAuthenticationInfo authInfo = new SimpleAuthenticationInfo(sysUser.getAccount(), sysUser.getPassword(), this.getName());
			return authInfo;
		}else{
			return null;
		}
	}

	@Override
	protected void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		super.clearCachedAuthorizationInfo(principals);
	}

	@Override
	protected void clearCachedAuthenticationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		super.clearCachedAuthenticationInfo(principals);
	}

	@Override
	protected void clearCache(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		super.clearCache(principals);
	}

	public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

	@Override
	public boolean isPermitted(PrincipalCollection principals, String permission) {
		//采用 or 验证相同URL情况下的权限
		if(permission.contains(OR_OPERATOR)){
			String[] permissons = permission.split(OR_OPERATOR);
			for (String orPermission : permissons) {
				//若有一个权限验证成功
				if(super.isPermitted(principals, orPermission)){
					return true;
				}
			}
			return false;
		}else{
			return super.isPermitted(principals, permission);
		}
	}

}
```

JwtTokenUtil

```java
@Component
public class JwtTokenUtil implements Serializable {

    static final String CLAIM_KEY_USERNAME = "sub";
    static final String CLAIM_KEY_CREATED = "iat";
    private static final long serialVersionUID = -3301605591108950415L;
   // @SuppressFBWarnings(value = "SE_BAD_FIELD", justification = "It's okay here")
    private Clock clock = DefaultClock.INSTANCE;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .getBody();
    }

   /* private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(clock.now());
    }*/

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private Boolean ignoreTokenExpiration(String token) {
        // here you specify tokens, for that the expiration is ignored
        return false;
    }

    public String generateToken(SysUser sysUser) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, sysUser.getAccount());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(createdDate)
            .setExpiration(expirationDate)
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact();
    }

/*    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getIssuedAtDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
            && (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }*/

/*    public String refreshToken(String token) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);

        final Claims claims = getAllClaimsFromToken(token);
        claims.setIssuedAt(createdDate);
        claims.setExpiration(expirationDate);

        return Jwts.builder()
            .setClaims(claims)
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact();
    }*/

 /*   public Boolean validateToken(String token, UserDetails userDetails) {
        JwtUser user = (JwtUser) userDetails;
        final String username = getUsernameFromToken(token);
        final Date created = getIssuedAtDateFromToken(token);
        //final Date expiration = getExpirationDateFromToken(token);
        return (
            username.equals(user.getUsername())
                && !isTokenExpired(token)
                && !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate())
        );
    }*/

    private Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + expiration * 1000);
    }
}
```

AuthenticationException

```java
public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

AuthenticationRestController

```java
@RestController
public class AuthenticationRestController {

    @Value("${jwt.header}")
    private String tokenHeader;



    @Autowired
    private SysUserFeign sysUserFeign;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
   // @Autowired
   // @Qualifier("jwtUserDetailsService")
  //  private UserDetailsService userDetailsService;

    @RequestMapping(value = "${jwt.route.authentication.path}", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) throws Exception {

        ServiceResponse serviceResponse = sysUserFeign.getUserInfoByUsername(authenticationRequest.getUsername());
         String token =null;
        if (serviceResponse.getData()!=null){
           // SysUser sysUser= (SysUser) serviceResponse.getData();
            ObjectMapper mapper=new ObjectMapper();
            SysUser sysUser = mapper.convertValue(serviceResponse.getData(), SysUser.class);
            token=  jwtTokenUtil.generateToken(sysUser);

        }

        // Return the token
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }


    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }


}
```

JwtAuthenticationRequest

```java
public class JwtAuthenticationRequest implements Serializable {

    private static final long serialVersionUID = -8445943548965154778L;

    private String username;
    private String password;

    public JwtAuthenticationRequest() {
        super();
    }

    public JwtAuthenticationRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
```

JwtAuthenticationResponse

```java
public class JwtAuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 1250166508152483573L;

    private final String token;

    public JwtAuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }
}
```

