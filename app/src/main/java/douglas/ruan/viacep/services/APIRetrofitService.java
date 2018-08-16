package douglas.ruan.viacep.services;

import douglas.ruan.viacep.model.CEP;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIRetrofitService {

    @GET("{CEP}/json/")
    Call<CEP> getEnderecoByCEP (@Path("CEP")String CEP);
}//fecha classe