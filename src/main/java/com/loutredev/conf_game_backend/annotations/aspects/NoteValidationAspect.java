package com.loutredev.conf_game_backend.annotations.aspects;

import com.loutredev.conf_game_backend.annotations.validate.ValidateNote;
import com.loutredev.conf_game_backend.exceptions.ressource.NoteNotFoundException;
import com.loutredev.conf_game_backend.persistence.repositories.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class NoteValidationAspect extends ValidationAspectBase {

  @Autowired
  private NoteRepository noteRepository;

  @Before("@annotation(validateAnnotation)")
  public void validate(JoinPoint joinPoint, ValidateNote validateAnnotation) {
    Object[] args = joinPoint.getArgs();
    String fieldName = validateAnnotation.idField();
    Long id = extractId(joinPoint, args, fieldName);

    if (id != null && !noteRepository.existsById(id)) {
      throw new NoteNotFoundException(id);
    }
  }
}
