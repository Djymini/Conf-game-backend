package com.loutredev.conf_game_backend.annotations.aspects;

import com.loutredev.conf_game_backend.annotations.validate.ValidateSession;
import com.loutredev.conf_game_backend.exceptions.ressource.SessionNotFoundException;
import com.loutredev.conf_game_backend.persistence.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class SessionValidationAspect extends ValidationAspectBase {

  @Autowired
  private SessionRepository sessionRepository;

  @Before("@annotation(validateAnnotation)")
  public void validate(JoinPoint joinPoint, ValidateSession validateAnnotation) {
    Object[] args = joinPoint.getArgs();
    String fieldName = validateAnnotation.idField();
    Long id = extractId(joinPoint, args, fieldName);

    if (id != null && !sessionRepository.existsById(id)) {
      throw new SessionNotFoundException(id);
    }
  }
}
