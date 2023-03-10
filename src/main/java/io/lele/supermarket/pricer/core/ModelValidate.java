package io.lele.supermarket.pricer.core;

import lombok.ToString;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashSet;
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
        Set<ValidatorResult> violations =  validator.validate( this).stream().map((violation) -> new ValidatorResult(violation.getMessage(), violation.getRootBeanClass(), violation.getPropertyPath().toString())).collect(Collectors.toSet());
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
