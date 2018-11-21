package bz.sps;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

/*
 * внешний http интерфейс, знает как распаковать запрос и в каком виде отдать результат
 */
@RestController
public class StoredProcRestController {

	@Autowired
	StoredProcService service;

	private StoredProcResult callFirst(ProcParams params) {
		return new StoredProcResult(true, "OK", service.first(params));
	}

	private StoredProcResult callSecond(ProcParams params) {
		return new StoredProcResult(true, "OK", service.second(params));
	}
	
	@RequestMapping(value = "/sps/first/json", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public StoredProcResult getFirstJson(@Valid @ModelAttribute("params") ProcParams params) {
		return callFirst(params);
	}

	@RequestMapping(value = "/sps/first/xml", produces = MediaType.APPLICATION_XML_VALUE)
	public StoredProcResult getFirstXml(@Valid @ModelAttribute("params") ProcParams params) {
		return callFirst(params);
	}
	
	@RequestMapping(value = "/sps/second/json", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public StoredProcResult getSecondJson(@Valid @ModelAttribute("params") ProcParams params) {
		return callSecond(params);
	}

	@RequestMapping(value = "/sps/second/xml", produces = MediaType.APPLICATION_XML_VALUE)
	public StoredProcResult getSecondXml(@Valid @ModelAttribute("params") ProcParams params) {
		return callSecond(params);
	}
}
