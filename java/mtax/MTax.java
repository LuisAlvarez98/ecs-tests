import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MTax implements Constant {
    
    public MTax(){}

    public static List<String> validate(List<X_Tax> xTaxList) {
        List<String> errorList = new ArrayList<>();
        
        if(xTaxList != null && xTaxList.size() > 0) {
            List<String> validIds = new ArrayList<>();
            int counter = 0;

            for (X_Tax tax : xTaxList) {
                if(tax.getId() != null) {
                    validIds.add(tax.getId().toString());
                }
                if(tax.getAmount() <= 0){
                    errorList.add("El importe es obligatorio");
                }
                if(tax.getTax() == null){
                    errorList.add("El impuesto es obligatorio");
                 }
                if(tax.getRate() <= 0){
                    errorList.add("La tasa es obligatoria");
                }
                if(tax.getShare() <= 0){
                    errorList.add("La cuota es obligatoria");
                }
                if(!tax.isLocal()){
                    counter++;
                }
            }
            if(counter == 0){
                errorList.add("Debe de incluir al menos una tasa no local");
            }
            if(validIds.size() > 0){
                    List<X_Tax> xt = TaxsByListId(validIds, false);

                    if(xt.size() != validIds.size()){
                        errorList.add("Existen datos no guardados previamente");
                    }else{
                        HashMap<String, X_Tax> map_taxs = new HashMap<String, X_Tax>();
                        for(X_Tax tax: xt){
                            map_taxs.put(tax.getId().toString(), tax);
                        }
                        for(int i = 0; i < xTaxList.size(); i++){
                           if(xTaxList.get(i).getId() == validIds.get(i)){
                                 xTaxList.get(i).setCreated(map_taxs.get(xTaxList.get(i).getId().toString()).getCreated());
                            }
                        }
                    }
            }else{
                errorList.add("No hay valid id's");
            }
        }
        return errorList;
    }
}
