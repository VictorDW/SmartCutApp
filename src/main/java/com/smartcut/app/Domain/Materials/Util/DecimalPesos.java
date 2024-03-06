package com.smartcut.app.Domain.Materials.Util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER}) //-> Especifica los lugares donde se puede aplicar la anotación
@Retention(RetentionPolicy.RUNTIME) //-> Significa que la anotación estará disponible para el tiempo de ejecución, lo cual es necesario para la validación personalizada
@Documented //-> Indica que la anotación @DecimalPesos debe incluirse en la documentación generada.
@Constraint(validatedBy = DecimalPesosValidator.class) //-> Especifica la clase de validador que se utilizará para validar los valores anotados
public @interface DecimalPesos {

  //Proporciona un mensaje predeterminado que se mostrará si la validación falla.
  String message() default "{message.invalid.price}";

  //Este atributo permite especificar grupos de validación
  Class<?>[] groups() default {};

  //Este atributo se utiliza para asociar información adicional con la anotación
  Class<? extends Payload>[] payload() default {};

}
