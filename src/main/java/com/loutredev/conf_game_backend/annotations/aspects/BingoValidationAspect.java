package com.loutredev.conf_game_backend.annotations.aspects;

import com.loutredev.conf_game_backend.annotations.validate.ValidateBingo;
import com.loutredev.conf_game_backend.exceptions.ressource.BingoNotFoundException;
import com.loutredev.conf_game_backend.persistence.repositories.BingoRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class BingoValidationAspect extends ValidationAspectBase {

  @Autowired
  private BingoRepository bingoRepository;

  @Before("@annotation(validateAnnotation)")
  public void validate(JoinPoint joinPoint, ValidateBingo validateAnnotation) {
    Object[] args = joinPoint.getArgs();
    String fieldName = validateAnnotation.idField();
    Long id = extractId(joinPoint, args, fieldName);

    if (id != null && !bingoRepository.existsById(id)) {
      throw new BingoNotFoundException(id);
    }
  }
}
