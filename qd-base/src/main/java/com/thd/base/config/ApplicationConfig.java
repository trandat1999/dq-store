package com.thd.base.config;

import com.thd.base.entity.User;
import com.thd.base.enums.UserEnum;
import com.thd.base.repository.UserRepository;
import com.thd.base.util.ConstUtils;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.stream.Stream;

import static com.thd.base.util.ConstUtils.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableAsync
@EnableScheduling
@EnableJpaAuditing(auditorAwareRef = AUDITOR_AWARE)
@RequiredArgsConstructor
@Slf4j
@EnableWebSecurity
@EnableMethodSecurity
public class ApplicationConfig {
    @Value("${application.security.whitelist}")
    private String[] whiteList;
    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final LogoutHandler logoutHandler;
    private final UserRepository userRepository;

    @Bean
    public AuditorAware<UUID> auditorAware() {
        return () -> {
            UUID rs = UUID.fromString(TEMP_UUID);
            if (!ObjectUtils.isEmpty(SecurityContextHolder.getContext()) && !ObjectUtils.isEmpty(SecurityContextHolder.getContext().getAuthentication()) &&
                    SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
                User user= null;
                try {
                    user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                    rs = user.getId();
                }catch (Exception e){
                    log.error("AuditorAware: {}",SecurityContextHolder.getContext().getAuthentication().getPrincipal());
                }
            }
            return Optional.of(rs);
        };
    }

    @Bean(name = TASK_EXECUTOR_BEAN_NAME)
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100000);
        executor.setThreadNamePrefix(PREFIX_NAME_TASK_EXECUTOR);
        executor.initialize();
        return executor;
    }

    @Bean(name = SCHEDULING_BEAN_NAME)
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler
                = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(5);
        threadPoolTaskScheduler.setThreadNamePrefix(PREFIX_NAME_SCHEDULING);
        return threadPoolTaskScheduler;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
//        authProvider.setHideUserNotFoundExceptions(false);
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        String[] combinedWhiteList = Stream.concat(Arrays.stream(WHITE_LIST_ROOT), Arrays.stream(whiteList)).toArray(String[]::new);
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(req ->
                        req.requestMatchers(combinedWhiteList)
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .httpBasic(AbstractHttpConfigurer::disable)
                .exceptionHandling(ex->ex.authenticationEntryPoint(authenticationEntryPoint).accessDeniedHandler(authenticationEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .logout(logout -> {
                    logout.logoutUrl(URI_LOGOUT)
                            .clearAuthentication(true)
                            .addLogoutHandler(logoutHandler)
                            .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());
                });
        return http.build();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @EventListener
    public void onApplicationEvent(ApplicationReadyEvent event) {
        createAdminUser();
    }

    private void createAdminUser() {
        User user = userRepository.findByUsername(ADMIN_USERNAME_ROOT).orElse(null);
        if (user == null) {
            user = User.builder()
                    .accountNonLocked(true)
                    .credentialsNonExpired(true)
                    .accountNonExpired(true)
                    .enabled(true)
                    .password(passwordEncoder().encode(ADMIN_PASSWORD_ROOT))
                    .username(ADMIN_USERNAME_ROOT)
                    .type(UserEnum.ROOT_USER)
                    .build();
            userRepository.save(user);
        }
    }
}
