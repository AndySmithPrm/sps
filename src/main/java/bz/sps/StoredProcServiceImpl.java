package bz.sps;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * сервис, который знает как делать вызовы функций в базе
 */
@Service
public class StoredProcServiceImpl implements StoredProcService {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	ObjectMapper objectMapper;

	@Override
	public String first(ProcParams params) {
		return call("first_function", params).values().stream().findFirst().get().toString();
	}

	@Override
	public SecondProcResult second(ProcParams params) {
		return objectMapper.convertValue(call("second_function", params), SecondProcResult.class);
	}

	private Map<String, Object> call(String name, Object params) {
		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate);
		call.withFunctionName(name);
		return call.execute(new BeanPropertySqlParameterSource(params));
	}
}
