package co.gov.udistrital.volley.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(co.gov.udistrital.volley.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(co.gov.udistrital.volley.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(co.gov.udistrital.volley.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(co.gov.udistrital.volley.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(co.gov.udistrital.volley.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(co.gov.udistrital.volley.domain.Letter.class.getName(), jcacheConfiguration);
            cm.createCache(co.gov.udistrital.volley.domain.PostalAddress.class.getName(), jcacheConfiguration);
            cm.createCache(co.gov.udistrital.volley.domain.LetterBook.class.getName(), jcacheConfiguration);
            cm.createCache(co.gov.udistrital.volley.domain.LetterBook.class.getName() + ".letters", jcacheConfiguration);
            cm.createCache(co.gov.udistrital.volley.domain.MembershipCard.class.getName(), jcacheConfiguration);
            cm.createCache(co.gov.udistrital.volley.domain.Membership.class.getName(), jcacheConfiguration);
            cm.createCache(co.gov.udistrital.volley.domain.Invoice.class.getName(), jcacheConfiguration);
            cm.createCache(co.gov.udistrital.volley.domain.Invoice.class.getName() + ".payments", jcacheConfiguration);
            cm.createCache(co.gov.udistrital.volley.domain.Payment.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
