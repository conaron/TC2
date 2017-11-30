package view.validador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("validador.Cpf")
public class Cpf implements Validator {

    private String cpf;
    private List<Integer> digitos;

    public Cpf() {
        digitos = new ArrayList();
        digitos.add(0);
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        cpf = value.toString().replaceAll("\\D", "");
        cpf = String.join("", Collections.nCopies(11 - cpf.length(), "0")) + cpf;
        cpf = cpf.substring(cpf.length() - 11);

        for (String algarismo : cpf.split("")) {
            digitos.add(Integer.valueOf(algarismo));
        }

        if (!valida()) {
            String titulo = "CPF inválido";
            String mensagem = value.toString() + " não é um cpf válido. Por favor, corrija e envie novamente";

            FacesMessage msg = new FacesMessage(titulo, mensagem);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }

    private boolean valida() {
        int algarismo_1 = 0, algarismo_2 = 0;
        int limite = 10;

//      Valida cpfs com número constante ex.: "000.000.000-00"
        for (int i = 0; i < 10; i++) {
            if (cpf.equals(String.join("", Collections.nCopies(11, String.valueOf(i))))) {
                return false;
            }
        }

//      Carrega os valores de repedição para o cálculo
        for (int i = 1; i < limite; i++) {
            algarismo_1 += digitos.get(i) * (limite - i + 1);
            algarismo_2 += digitos.get(i) * (limite - i + 2);
        }

//      Valida o algarismo 1 do digito verificador
        algarismo_1 = (algarismo_1 * 10) % 11;
        algarismo_1 = (algarismo_1 == 10) ? 0 : algarismo_1;

        if (algarismo_1 != digitos.get(10)) {
            return false;
        }

//      Valida o algarismo 2 do digito verificador
        algarismo_2 += algarismo_1 * 2;
        algarismo_2 = (algarismo_2 * 10) % 11;
        algarismo_2 = (algarismo_2 == 10) ? 0 : algarismo_2;

        return algarismo_2 == digitos.get(11);
    }

}
