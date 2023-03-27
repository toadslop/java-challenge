package jp.co.axa.apidemo;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import io.github.cdimascio.dotenv.Dotenv;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import net.minidev.json.JSONObject;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class ApiDemoApplicationTests {

	private Dotenv dotenv = Dotenv.load();

	@Autowired
	private EmployeeRepository repository;

	@Autowired
	private MockMvc mvc;

	@Test
	public void loadsSingleEmployee() throws Exception {
		createTestEmployee("Bob", "Finance", 20000);

		mvc.perform(MockMvcRequestBuilders.get("/api/v1/employees")
				.header("Authorization", getAuth())
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(
						MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value("Bob"));
	}

	@Test
	public void loadsMultipleEmployees() throws Exception {
		createTestEmployee("Bob", "Finance", 20000);
		createTestEmployee("Sally", "HR", 30000);

		mvc.perform(MockMvcRequestBuilders.get("/api/v1/employees")
				.header("Authorization", getAuth())
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(
						MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value("Bob"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[1].name").value("Sally"));
	}

	@Test
	public void updatesEmployee() throws Exception {
		createTestEmployee("Bob", "Finance", 20000);
		Employee emp = createTestEmployee("Sally", "HR", 30000);

		Map<String, String> content = new HashMap<>();
		content.put("name", "Barbara");

		mvc.perform(MockMvcRequestBuilders.put("/api/v1/employees/" + emp.getId())
				.header("Authorization", getAuth())
				.content(new JSONObject(content).toJSONString()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(
						MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Barbara"));
	}

	@Test
	public void getsEmployeeById() throws Exception {
		createTestEmployee("Bob", "Finance", 20000);
		Employee emp = createTestEmployee("Sally", "HR", 30000);

		mvc.perform(MockMvcRequestBuilders.get("/api/v1/employees/" + emp.getId())
				.header("Authorization", getAuth())
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(
						MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Sally"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(30000))
				.andExpect(MockMvcResultMatchers.jsonPath("$.department").value("HR"));
	}

	@Test
	public void createdNewEmployee() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/api/v1/employees").param("name", "Sally")
				.param("salary", "20000").param("department", "HR")
				.header("Authorization", getAuth())
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(
						MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Sally"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(20000))
				.andExpect(MockMvcResultMatchers.jsonPath("$.department").value("HR"));
	}

	@Test
	public void deletesEmployee() throws Exception {
		Employee emp = createTestEmployee("Bob", "Finance", 20000);
		createTestEmployee("Sally", "HR", 30000);
		mvc.perform(MockMvcRequestBuilders.delete("/api/v1/employees/" + emp.getId())
				.header("Authorization", getAuth())
				.contentType(MediaType.APPLICATION_JSON));

		mvc.perform(MockMvcRequestBuilders.get("/api/v1/employees/" + emp.getId())
				.header("Authorization", getAuth())
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	public Employee createTestEmployee(String name, String department, Integer salary) {
		Employee emp = new Employee();
		emp.setName(name);
		emp.setDepartment(department);
		emp.setSalary(salary);
		return repository.save(emp);
	}

	public String getAuth() {
		String adminUser = dotenv.get("ADMIN_USER");
		String adminPass = dotenv.get("ADMIN_PASS");
		String formatted = adminUser + ":" + adminPass;
		String encoded = Base64.getEncoder().encodeToString(formatted.getBytes());
		return "Basic " + encoded;
	}

}
