package org.koix.zephyr.authen.service;

import javax.annotation.PostConstruct;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

@Service
public class ClientDetailsServiceImpl implements ClientDetailsService
{
    private PasswordEncoder passwordEncoder;
	private ClientDetailsService clientDetailsService;
	
    public ClientDetailsServiceImpl()
    {
        this.passwordEncoder=new BCryptPasswordEncoder();
    }
	
	@PostConstruct  
    public void init() throws Exception
    {
		//从数据库读取客户端数据，建立内存中的验证数据
		InMemoryClientDetailsServiceBuilder builder = new InMemoryClientDetailsServiceBuilder();
		builder
        .withClient("sub_system")
            //.secret("$2a$10$ZRmPFVgE6o2aoaK6hv49pOt5BZIKBDLywCaFkuAs6zYmRkpKHgyuO")
            .secret(this.passwordEncoder.encode("123456"))
            .authorizedGrantTypes("authorization_code","refresh_token")
            .scopes("all")
            .redirectUris("http://localhost:8081/system/login")
            //.redirectUris("http://localhost:8082/login")
            .accessTokenValiditySeconds(7200)
            .autoApprove(true)
        .and()
        .withClient("sub_showcase")
            //.secret("$2a$10$8yVwRGY6zB8wv5o0kRgD0ep/HVcvtSZUZsYu/586Egxc1hv3cI9Q6")
            .secret(this.passwordEncoder.encode("123456"))
            .authorizedGrantTypes("authorization_code","refresh_token")
            .scopes("all")
            .redirectUris("http://localhost:8082/showcase/login")
            //.redirectUris("http://localhost:8083/login")
            .accessTokenValiditySeconds(7200)
            .autoApprove(true)
        .and()
        .withClient("api01")
            .secret(this.passwordEncoder.encode("123456"))
            .authorizedGrantTypes("password", "refresh_token")
            .scopes("all")
            .accessTokenValiditySeconds(7200);
		this.clientDetailsService = builder.build();
    }
	
	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException 
	{
		//读取客户端数据
		return this.clientDetailsService.loadClientByClientId(clientId);
	}
}
