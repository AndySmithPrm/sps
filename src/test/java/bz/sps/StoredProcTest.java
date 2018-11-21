package bz.sps;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StoredProcTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	public void test() throws Exception {
		// сделано под функции postgres
		jdbcTemplate.execute(
				"CREATE OR REPLACE FUNCTION first_function(age int,weight double precision,name text,id uuid,email text) RETURNS text AS $$"
						+ "        BEGIN RETURN upper(name)||age;       END" + "$$ LANGUAGE plpgsql;");
		jdbcTemplate.execute(
				"CREATE OR REPLACE FUNCTION second_function(age int,weight double precision,name text,id uuid,email text,"
						+ "out resultid bigint,out resultinfo text) AS $$"
						+ "   begin resultid=age*2;resultinfo=email||id; end" + "$$ LANGUAGE plpgsql");
		
		// first_function  upper(name)+age POPPOP18
		mvc.perform(get(
				"/sps/first/json?name=poppop&id=15a28415-da6f-4d73-8664-15a28971&age=18&weight=51.19&email=pop@gmail.com"))
				.andExpect(status().isOk())
				.andExpect(content().json("{\"success\":true,\"msg\":\"OK\",\"data\":\"POPPOP18\"}"));
		mvc.perform(get(
				"/sps/first/xml?name=poppop&id=15a28415-da6f-4d73-8664-15a28971&age=18&weight=51.19&email=pop@gmail.com"))
				.andExpect(status().isOk()).andExpect(content().xml(
						"<StoredProcResult><success>true</success><msg>OK</msg><data>POPPOP18</data></StoredProcResult>"));
		// second_function resultid=age*2;resultinfo=email||id
		// resultid:36, resultinfo:pop@gmail.com15a28415-da6f-4d73-8664-000015a28971
		mvc.perform(get(
				"/sps/second/json?name=poppop&id=15a28415-da6f-4d73-8664-15a28971&age=18&weight=51.19&email=pop@gmail.com"))
				.andExpect(status().isOk()).andExpect(content().json(
						"{\"success\":true,\"msg\":\"OK\",\"data\":{\"resultid\":36,\"resultinfo\":\"pop@gmail.com15a28415-da6f-4d73-8664-000015a28971\"}}"));
		mvc.perform(get(
				"/sps/second/xml?name=poppop&id=15a28415-da6f-4d73-8664-15a28971&age=18&weight=51.19&email=pop@gmail.com"))
				.andExpect(status().isOk()).andExpect(content().xml(
						"<StoredProcResult><success>true</success><msg>OK</msg><data><resultid>36</resultid><resultinfo>pop@gmail.com15a28415-da6f-4d73-8664-000015a28971</resultinfo></data></StoredProcResult>"));

		// проверка валидаторов
		
		// слишком длинное имя 	@Size(min = 5, max = 10)
		mvc.perform(get(
				"/sps/first/json?name=poppoppoppop&id=15a28415-da6f-4d73-8664-15a28971&age=18&weight=51.19&email=pop@gmail.com"))
				.andExpect(status().isBadRequest())
				.andExpect(content().json("{\"success\":false,\"msg\":\"ERROR_ARGUMENT_NOT_VALID\",\"data\":[{\"field\":\"name\",\"badValue\":\"poppoppoppop\"}]}"));
		// неформатный uuid
		mvc.perform(get(
				"/sps/first/json?name=poppop&id=Q5a28415-da6f-4d73-8664-15a28971&age=18&weight=51.19&email=pop@gmail.com"))
				.andExpect(status().isBadRequest())
				.andExpect(content().json(
						"{\"success\":false,\"msg\":\"ERROR_ARGUMENT_NOT_VALID\",\"data\":[{\"field\":\"id\",\"badValue\":\"Q5a28415-da6f-4d73-8664-15a28971\"}]}"));
		// дробный age
		mvc.perform(get(
				"/sps/first/json?name=poppop&id=15a28415-da6f-4d73-8664-15a28971&age=18.4&weight=51.19&email=pop@gmail.com"))
				.andExpect(status().isBadRequest())
				.andExpect(content().json(
						"{\"success\":false,\"msg\":\"ERROR_ARGUMENT_NOT_VALID\",\"data\":[{\"field\":\"age\",\"badValue\":\"18.4\"}]}"));
		// маленький age @Min(18)
		mvc.perform(get(
				"/sps/first/json?name=poppop&id=15a28415-da6f-4d73-8664-15a28971&age=1&weight=51.19&email=pop@gmail.com"))
				.andExpect(status().isBadRequest())
				.andExpect(content().json(
						"{\"success\":false,\"msg\":\"ERROR_ARGUMENT_NOT_VALID\",\"data\":[{\"field\":\"age\",\"badValue\":1}]}"));
		// большой weight 	@DecimalMax("100.0"), неправильный email, две ошибки одновременно
		mvc.perform(get(
				"/sps/first/json?name=poppop&id=15a28415-da6f-4d73-8664-15a28971&age=18&weight=151.19&email=popgmail.com"))
				.andExpect(status().isBadRequest())
				.andExpect(content().json(
						"{\"success\":false,\"msg\":\"ERROR_ARGUMENT_NOT_VALID\",\"data\":[{\"field\":\"email\",\"badValue\":\"popgmail.com\"},{\"field\":\"weight\",\"badValue\":151.19}]}"));
	}

}
