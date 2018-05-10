package com.client;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.oauth2.common.util.DefaultJdbcListFactory;
import org.springframework.security.oauth2.common.util.JdbcListFactory;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
 
public class ClientDetailsService implements ClientDetailsService {

	private final Log log = LogFactory.getLog(getClass());
	
	private JdbcTemplate jdbcTemplate;

	@SuppressWarnings("unused")
	private JdbcListFactory listFactory;

	private String selectClientDetailsSql = DEFAULT_SELECT_STATEMENT;

	private static final String CLIENT_FIELDS_FOR_UPDATE = "resource_ids, scope, "
			+ "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, "
			+ "refresh_token_validity, additional_information, autoapprove";

	private static final String CLIENT_FIELDS = "client_secret, " + CLIENT_FIELDS_FOR_UPDATE;

	private static final String BASE_FIND_STATEMENT = "select client_id, " + CLIENT_FIELDS
			+ " from auth_client_details";

	private static final String DEFAULT_SELECT_STATEMENT = BASE_FIND_STATEMENT + " where client_id = ?";


	public ClientDetailsService(DataSource dataSource) {
		Assert.notNull(dataSource, "DataSource required");
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.listFactory = new DefaultJdbcListFactory(new NamedParameterJdbcTemplate(jdbcTemplate));
	}

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		ClientDetails details;
		try {
			details = jdbcTemplate.queryForObject(selectClientDetailsSql, new ClientDetailsRowMapper(), clientId);
		}
		catch (EmptyResultDataAccessException e) {
			throw new NoSuchClientException("No client with requested id: " + clientId);
		}
		log.debug("--- ClientDetails :" +details + " ---- clientId :" + clientId);
		return details;
	} 

	class ClientDetailsRowMapper implements RowMapper<ClientDetails> {

		public ClientDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
			BaseClientDetails details = new BaseClientDetails(rs.getString(1), rs.getString(3), rs.getString(4),
					rs.getString(5), rs.getString(7), rs.getString(6));
			details.setClientSecret(rs.getString(2));
			if (rs.getObject(8) != null) {
				details.setAccessTokenValiditySeconds(rs.getInt(8));
			}
			if (rs.getObject(9) != null) {
				details.setRefreshTokenValiditySeconds(rs.getInt(9));
			} 
			String scopes = rs.getString(11);
			if (scopes != null) {
				details.setAutoApproveScopes(StringUtils.commaDelimitedListToSet(scopes));
			}
			return details;
		} 
	}
}