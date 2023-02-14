package io.lele.supermarket.pricer.core;

import ch.qos.logback.core.model.Model;
import jakarta.validation.*;
import jakarta.validation.metadata.ConstraintDescriptor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorManager;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.hibernate.validator.internal.metadata.core.ConstraintHelper;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;

import java.lang.annotation.ElementType;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
@ToString
public abstract class ModelValidate {

    public static final String STD_VALIDATION_ERROR  = "VALIDATION_ERROR";

    private Validator validator;
    private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    public ModelValidate() {
        validator = factory.getValidator();
    }

    /**
     * Evaluates all Bean Validations on the attributes of this
     * instance.
     */
    public  Set<ValidatorResult> validate() {
        Set<ValidatorResult> violations =  validator.validate( this).stream().map((violation) -> new ValidatorResult(violation.getMessage(), violation.getRootBeanClass().toString(), violation.getPropertyPath().toString(), violation.getExecutableParameters())).collect(Collectors.toSet());
        Set<ValidatorResult>  additionValidations = doCustomValidation();
        if(additionValidations != null && !additionValidations.isEmpty()){
            violations.addAll(additionValidations);
        }
        return violations;
    }
    /**
     * Evaluates all Bean Validations on the attributes of this
     * instance.
     */
    public    Set<ValidatorResult> validate(boolean throwExceptionIfAnyViolation) {
        Set<ValidatorResult> violations = validate();
        if (!violations.isEmpty()) {
            throw new ValidationException(STD_VALIDATION_ERROR);
        }
        return violations;
    }

    protected Set<ValidatorResult> doCustomValidation(){
        return new HashSet<>(0);
    }

    public  boolean isValid(){
        return this.validate().isEmpty();
    }

    protected Set<ValidatorResult>  getEmptyValidator(){
       return  new HashSet<>(0);
    }

}
