package com.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.util.DefaultJdbcListFactory;
import org.springframework.security.oauth2.common.util.JdbcListFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


@Service
public class UserDetailsService implements UserDetailsService {

	private final Log logger = LogFactory.getLog(getClass());

	private JdbcTemplate jdbcTemplate;
	
	@SuppressWarnings("unused")
	private JdbcListFactory listFactory;
	
	public void setDataSource(DataSource dataSource) {
		Assert.notNull(dataSource, "DataSource required");
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.listFactory = new DefaultJdbcListFactory(new NamedParameterJdbcTemplate(jdbcTemplate));
	}

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		logger.debug("loadUserByUsername username =" +username);
		UserInfo userInfo = getUserInfo(username);
		GrantedAuthority authority = new SimpleGrantedAuthority(userInfo.getRole());
		UserDetails userDetails = (UserDetails)new User(userInfo.getUsername(), 
				userInfo.getPassword(), Arrays.asList(authority));
		logger.debug("--- getUsername :" +userInfo.getUsername());
		logger.debug("--- getPassword :" +userInfo.getPassword());
		logger.debug("--- getRole 	  :" +userInfo.getRole());
		logger.debug("--- authority   :" + Arrays.asList(authority));
		return userDetails;
	}

	public UserInfo getUserInfo(String username){
		logger.debug("--- getUserInfo invoked ---");
		String sql = "SELECT USERNAME, PASSWORD, ROLES FROM AUTH_USER_DETAILS WHERE USERNAME = ? AND ENABLED = 1";
		UserInfo userInfo = (UserInfo)jdbcTemplate.queryForObject(sql, new Object[]{username},
				new RowMapper<UserInfo>() {
			public UserInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
				UserInfo user = new UserInfo();
				user.setUsername(rs.getString("USERNAME"));
				user.setPassword(rs.getString("PASSWORD"));
				user.setRole(rs.getString("ROLES"));
				return user;
			}
		});
		logger.debug("--- getUserInfo :" +userInfo);
		return userInfo;
	} 
}