package com.project.my_landing_page.http.converters;

import com.project.my_landing_page.http.request.SobreMimPaginaRequest;
import com.project.my_landing_page.http.response.SobreMimPaginaResponse;
import com.project.my_landing_page.models.Biografia;
import com.project.my_landing_page.models.Desgosto;
import com.project.my_landing_page.models.Hobbie;
import com.project.my_landing_page.models.Objetivo;
import com.project.my_landing_page.models.SobreMimPagina;
import com.project.my_landing_page.models.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SobreMimPaginaConverter {

    private final SobreMimModelConverter converter;

    public SobreMimPagina toEntity(SobreMimPaginaRequest req, Usuario admin) {
        SobreMimPagina pagina = new SobreMimPagina();

        pagina.setAdmin(admin);
        pagina.setSobreMimModel(converter.toEntity(req.sobreMim()));
        pagina.setBiografia(new Biografia(0L, req.biografia()));
        pagina.setHobbie(new Hobbie(0L, req.hobbie()));
        pagina.setObjetivo(new Objetivo(0L, req.objetivo()));
        pagina.setDesgosto(new Desgosto(0L, req.desgosto()));

        return pagina;
    }

    public SobreMimPaginaResponse toResponse(SobreMimPagina pagina) {
        return new SobreMimPaginaResponse(
                pagina.getId(),
                converter.toResponse(pagina.getSobreMimModel()),
                pagina.getBiografia().getBiografia(),
                pagina.getHobbie().getHobbie(),
                pagina.getObjetivo().getObjetivo(),
                pagina.getDesgosto().getDesgosto()
        );
    }
}
