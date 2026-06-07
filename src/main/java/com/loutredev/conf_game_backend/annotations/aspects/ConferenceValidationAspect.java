package com.loutredev.conf_game_backend.annotations.aspects;

import com.loutredev.conf_game_backend.annotations.validate.ValidateConference;
import com.loutredev.conf_game_backend.exceptions.ressource.ConferenceNotFoundException;
import com.loutredev.conf_game_backend.persistence.repositories.ConferenceRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class ConferenceValidationAspect extends ValidationAspectBase {

  @Autowired
  private ConferenceRepository conferenceRepository;

  @Before("@annotation(validateAnnotation)")
  public void validate(JoinPoint joinPoint, ValidateConference validateAnnotation) {
    Object[] args = joinPoint.getArgs();
    String fieldName = validateAnnotation.idField();
    Long id = extractId(joinPoint, args, fieldName);

    if (id != null && !conferenceRepository.existsById(id)) {
      throw new ConferenceNotFoundException(id);
    }
  }
}
