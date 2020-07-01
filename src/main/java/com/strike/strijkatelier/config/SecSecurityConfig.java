package com.strike.strijkatelier.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@ComponentScan(basePackages = {"com.strike.strijkatelier.security"})
//@ImportResource("classpath:webSecurityConfig.xml" })
@EnableWebSecurity
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Autowired
//    private AuthenticationSuccessHandler myAuthenticationSuccessHandler;
//
//    @Autowired
//    private LogoutSuccessHandler myLogoutSuccessHandler;
//
//    @Autowired
//    private AuthenticationFailureHandler authenticationFailureHandler;
//
//    @Autowired
//    private CustomWebAuthenticationDetailsSource authenticationDetailsSource;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private DifferentLocationChecker differentLocationChecker;
//
//    public SecSecurityConfig() {
//        super();
//    }
//
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Override
//    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authProvider());
//    }
//
//    @Override
//    public void configure(final WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/resources/**")
//                .antMatchers("/api-docs/**")
//                .antMatchers("/swagger-ui/**")
//                .antMatchers("/configuration/ui")
//                .antMatchers("/swagger-resources/**")
//                .antMatchers( "/configuration/security")
//                .antMatchers("/swagger-ui.html")
//                .antMatchers("/webjars/**")
//                .antMatchers("/v3/**");
//    }
//
//    @Override
//    protected void configure(final HttpSecurity http) throws Exception {
//        // @formatter:off
//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/h2/**").permitAll() // to enable access to H2 db's console
//                .antMatchers("/login*", "/login*", "/logout*", "/signin/**", "/signup/**", "/customLogin",
//                        "/user/registration*", "/registrationConfirm*", "/expiredAccount*", "/registration*",
//                        "/badUser*", "/user/resendRegistrationToken*", "/forgetPassword*", "/user/resetPassword*", "/user/savePassword*", "/updatePassword*",
//                        "/user/changePassword*", "/emailError*", "/resources/**", "/old/user/registration*", "/successRegister*", "/qrcode*", "/user/enableNewLoc*").permitAll()
//                .antMatchers("/invalidSession*").anonymous()
//                .antMatchers("/user/updatePassword*").hasAuthority("CHANGE_PASSWORD_PRIVILEGE")
//                .anyRequest().hasAuthority("READ_PRIVILEGE")
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .defaultSuccessUrl("/homepage.html")
//                .failureUrl("/login?error=true")
//                .successHandler(myAuthenticationSuccessHandler)
//                .failureHandler(authenticationFailureHandler)
//                .authenticationDetailsSource(authenticationDetailsSource)
//                .permitAll()
//                .and()
//                .sessionManagement()
//                .invalidSessionUrl("/invalidSession.html")
//                .maximumSessions(1).sessionRegistry(sessionRegistry()).and()
//                .sessionFixation().none()
//                .and()
//                .logout()
//                .logoutSuccessHandler(myLogoutSuccessHandler)
//                .invalidateHttpSession(false)
//                .logoutSuccessUrl("/logout.html?logSucc=true")
//                .deleteCookies("JSESSIONID")
//                .permitAll()
//                .and()
//                .rememberMe().rememberMeServices(rememberMeServices()).key("theKey")
//                .and()
//                .headers().frameOptions().disable(); // this is needed to access the H2 db's console
//
//
//        // @formatter:on
//    }
//
//    // beans
//
//    @Bean
//    public DaoAuthenticationProvider authProvider() {
//        final CustomAuthenticationProvider authProvider = new CustomAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService);
//        authProvider.setPasswordEncoder(encoder());
//        authProvider.setPostAuthenticationChecks(differentLocationChecker);
//        return authProvider;
//    }
//
//    @Bean
//    public PasswordEncoder encoder() {
//        return new BCryptPasswordEncoder(11);
//    }
//
//    @Bean
//    public SessionRegistry sessionRegistry() {
//        return new SessionRegistryImpl();
//    }
//
//    @Bean
//    public RememberMeServices rememberMeServices() {
//        CustomRememberMeServices rememberMeServices = new CustomRememberMeServices("theKey", userDetailsService, new InMemoryTokenRepositoryImpl());
//        return rememberMeServices;
//    }
//
//    @Bean
//    public DatabaseReader databaseReader() throws IOException, GeoIp2Exception {
//        final File resource = new File("src/main/resources/maxmind/GeoLite2-Country.mmdb");
//        return new DatabaseReader.Builder(resource).build();
//    }

}