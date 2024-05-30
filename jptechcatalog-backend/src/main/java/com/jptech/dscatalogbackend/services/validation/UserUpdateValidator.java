package com.jptech.dscatalogbackend.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.jptech.dscatalogbackend.dto.UserUpdateDTO;
import com.jptech.dscatalogbackend.entities.User;
import com.jptech.dscatalogbackend.repositories.UserRepository;
import com.jptech.dscatalogbackend.services.exceptions.FieldMessage;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
															//implementa a anotation e onde ela será usada
public class UserUpdateValidator implements ConstraintValidator<UserUpdateValid, UserUpdateDTO> {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private HttpServletRequest httpServletRequest; //guarda as info da requisição recebida
	
	@Override
	public void initialize(UserUpdateValid ann) {
	}

	@Override
	public boolean isValid(UserUpdateDTO dto, ConstraintValidatorContext context) {
		
		@SuppressWarnings("unchecked")
		var uriVars = (Map<String, String>) httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		long userId = Long.parseLong(uriVars.get("id"));
		
		List<FieldMessage> list = new ArrayList<>();
		
		//Aqui vem os testes de validação, acrescentando objetos FieldMessage à lista
		
		User user = userRepository.findByEmail(dto.getEmail());
		
		if (user != null && userId != user.getId()) {
			list.add(new FieldMessage("email","Email já existe"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty(); // se false dispara erro, se true passa na validação
	}
}
