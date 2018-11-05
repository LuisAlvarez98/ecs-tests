import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MTax implements Constant {
    
    public MTax(){
        
    }
    public static List<String> validate(List<X_Tax> xTaxList) {
        
         List<String> errorList = new ArrayList<>();
         List<String> validIds = new ArrayList<>();
        //Guarda los id's validos y guarda los errores que aparecen
        /*
            Quite el xTaxList.size() > 0 ya que si la xTaxList no tiene 
            elementos signficica que es nulo y pues el xTaxList != null hace
            el trabajo.
         */
        //Checa si la xTaxList existe
        if(xTaxList != null) {
            /*Cambie al nombre de la variable en ingles por estandares
            O es en ingles o es en espanol
             */
            int counter = 0;
            //Recorre los taxes para ver si tienen un id valido
            for (X_Tax tax : xTaxList) {
                //Si el id es valido entonces se agrega a la lista de validIds
                if(tax.getId() != null){
                    validIds.add(tax.getId().toString());
                }
                //Si el tax es nulo se agrega a la lista de errores que el 
                //impuesto es obligatorio
                if(tax.getTax() != null) {
                    errorList.add("El impuesto es obligatorio");
                }
                //Checa si no hay locales y agrega en un contador cuantos
                if(!tax.isLocal()){
                    counter++;
                }
            }
            /*
                Quite cont <= 0 porque solamente se ocupa saber si es igual a 0
                no es necesario sabes si es menor
             */
            if(counter == 0){
                errorList.add("Debe de incluir al menos una tasa no local");
            }
            //Checa si existen validIds
            if(validIds.size() > 0){
                    List<X_Tax> xt = TaxsByListId(validIds, false);
                     /*Declare el int i  afuera por estandares */
                    int i;
                    //Checa si hay datos no guardados previamente
                    if(xt.size() != validIds.size()){
                        errorList.add("Existen datos no guardados previamente");
                    }else{
                        //Guarda los datos que no fueron guardados previamente
                        HashMap<String, X_Tax> map_taxs = new HashMap<String, X_Tax>();
                        for(X_Tax tax: xt){
                            map_taxs.put(tax.getId().toString(), tax);
                        }
                        for(i = 0; i < xTaxList.size(); i++){
                            if(xTaxList.get(i).getId() != null){
                                xTaxList.get(i).setCreated(
                                        map_taxs.get(xTaxList.get(i).getId().toString())
                                                .getCreated());
                            }
                        }
                    }
            /*Se debe agregar a la lista de errores que no existen id's validos
                Es util saber que no existen id's validos
             */
            }else{
                errorList.add("No existen id's validos");
            }
        }
        return errorList;
    }
}