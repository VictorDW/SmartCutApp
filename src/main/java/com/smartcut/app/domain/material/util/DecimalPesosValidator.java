package com.smartcut.app.domain.material.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

/**
 * Clase validador que contiene la logica de validación
 */
@AllArgsConstructor
public class DecimalPesosValidator implements ConstraintValidator<DecimalPesos, BigDecimal> {

  /**
   * Este método contiene la logica para primeramente limpiar el valor ingresado, con el fin de dejarlo sin ningun punto
   * que afecte la integridad del valor real, por otro lado valida que el precio ingresado cumpla con las condiciones establecidas
   * @param value
   * @param constraintValidatorContext
   * @return un boolean que decide si es el dato ingresado es valido
   */
  @Override
  public boolean isValid(BigDecimal value, ConstraintValidatorContext constraintValidatorContext) {

    String convertedValue = value.toString().replace(".", "");
    BigDecimal bigDecimalValue = new BigDecimal(convertedValue);
    BigDecimal minValue = new BigDecimal(100);
    BigDecimal maxValue = new BigDecimal(999999);

    return bigDecimalValue.compareTo(minValue) >= 0 && bigDecimalValue.compareTo(maxValue) <= 0;
  }
}
