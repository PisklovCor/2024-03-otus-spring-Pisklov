package ru.otus.hw.cofiguration;

import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.DefaultPermissionFactory;
import org.springframework.security.acls.domain.AclAuthorizationStrategy;
import org.springframework.security.acls.domain.AclAuthorizationStrategyImpl;
import org.springframework.security.acls.domain.ConsoleAuditLogger;
import org.springframework.security.acls.domain.DefaultPermissionGrantingStrategy;
import org.springframework.security.acls.domain.SpringCacheBasedAclCache;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.PermissionGrantingStrategy;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.access.expression.DefaultHttpSecurityExpressionHandler;

import javax.sql.DataSource;

/**
 * Для более высокой версии спринга
 * boolean prePostEnabled() default true;
 * EnableGlobalMethodSecurity--@Deprecated
 */
@EnableMethodSecurity(securedEnabled = true)
@Configuration
public class AclMethodSecurityConfiguration {

    @Bean
    public DefaultPermissionFactory permissionFactory() {
        return new DefaultPermissionFactory();
    }

    /**
     * Роль для создания ACL сущностей
     */
    @Bean
    public AclAuthorizationStrategy aclAuthorizationStrategy() {
        return new AclAuthorizationStrategyImpl(new SimpleGrantedAuthority("ROLE_EDITOR"));
    }

    @Bean
    public ConsoleAuditLogger consoleAuditLogger() {
        return new ConsoleAuditLogger();
    }

    @Bean
    public PermissionGrantingStrategy permissionGrantingStrategy(ConsoleAuditLogger consoleAuditLogger) {
        return new DefaultPermissionGrantingStrategy(consoleAuditLogger);
    }

    @Bean
    public SpringCacheBasedAclCache aclCache(PermissionGrantingStrategy permissionGrantingStrategy,
                                             AclAuthorizationStrategy aclAuthorizationStrategy) {
        final ConcurrentMapCache aclCache = new ConcurrentMapCache("acl_cache");
        return new SpringCacheBasedAclCache(aclCache, permissionGrantingStrategy, aclAuthorizationStrategy);
    }

    @Bean
    public LookupStrategy lookupStrategy(DataSource dataSource,
                                         SpringCacheBasedAclCache aclCache,
                                         AclAuthorizationStrategy aclAuthorizationStrategy,
                                         ConsoleAuditLogger consoleAuditLogger) {
        BasicLookupStrategy lookupStrategy = new BasicLookupStrategy(dataSource,
                aclCache, aclAuthorizationStrategy, consoleAuditLogger);
        lookupStrategy.setAclClassIdSupported(true);

        return lookupStrategy;
    }

    @Bean
    public MutableAclService aclService(DataSource dataSource,
                                        LookupStrategy lookupStrategy,
                                        SpringCacheBasedAclCache aclCache) {
        JdbcMutableAclService mutableAclService = new JdbcMutableAclService(dataSource, lookupStrategy, aclCache);
        mutableAclService.setClassIdentityQuery("select currval('acl_class_id_seq')");
        mutableAclService.setSidIdentityQuery("select currval('acl_sid_id_seq')");
        mutableAclService.setAclClassIdSupported(true);

        return mutableAclService;
    }

    @Bean
    public AclPermissionEvaluator permissionEvaluator(MutableAclService aclService) {
        return new AclPermissionEvaluator(aclService);
    }


    @Bean
    public DefaultMethodSecurityExpressionHandler methodSecurityExpressionHandler(
            AclPermissionEvaluator permissionEvaluator,
            ApplicationContext applicationContext) {
        var defaultHttpSecurityExpressionHandler = new DefaultMethodSecurityExpressionHandler();
        defaultHttpSecurityExpressionHandler.setPermissionEvaluator(permissionEvaluator);
        defaultHttpSecurityExpressionHandler.setApplicationContext(applicationContext);
        return defaultHttpSecurityExpressionHandler;
    }

    @Bean
    public DefaultHttpSecurityExpressionHandler httpSecurityExpressionHandler(
            AclPermissionEvaluator permissionEvaluator,
            ApplicationContext applicationContext) {
        var defaultHttpSecurityExpressionHandler = new DefaultHttpSecurityExpressionHandler();
        defaultHttpSecurityExpressionHandler.setPermissionEvaluator(permissionEvaluator);
        defaultHttpSecurityExpressionHandler.setApplicationContext(applicationContext);
        return defaultHttpSecurityExpressionHandler;
    }
}
