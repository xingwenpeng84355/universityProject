/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;
import jdk.nashorn.internal.runtime.Context;
 
@FacesValidator("myValidator")
public class MyValidator implements javax.faces.validator.Validator{

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        String txt = (String)o;
        if ( isStringAlphDigital(txt) ){
            // correct format;
        } else {  
            throw new UnsupportedOperationException("Username and Password can only contains Digital And Alphabets!!!");     
        }
    }
    
    private boolean isStringAlphDigital(String src){
        int l = src.length();
        for(int I=0; I < l; I++){
            char i = src.charAt(I);
            if ( i=='!' || i=='@'|| i=='#'|| i=='$'|| i=='%' || 
                    i=='&'|| i=='.'|| i=='+'|| i=='('|| i=='?' || 
                      i=='>'|| i=='-'|| i==')'|| i=='*'|| i=='/'|| 
                      i=='`'|| i=='~'
                    )
                return false;
        }
        
        return  true;
    }
    
}
