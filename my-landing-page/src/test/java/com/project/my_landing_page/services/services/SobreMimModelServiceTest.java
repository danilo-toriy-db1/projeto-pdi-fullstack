package com.project.my_landing_page.services.services;

import com.project.my_landing_page.models.SobreMimModel;
import com.project.my_landing_page.repositories.repositories.SobreMimModelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SobreMimModelServiceTest {

    @Mock
    private SobreMimModelRepository sobreMimModelRepository;

    @InjectMocks
    private SobreMimModelService sobreMimModelService;

    @Test
    void deveRetornarNuloQuandoSobreMimModelNaoExistir() {
        when(sobreMimModelRepository.findById(1L)).thenReturn(Optional.empty());

        SobreMimModel res = sobreMimModelService.update(1L, new SobreMimModel(0L, "Teste", 10, "Teste", "Teste", "Teste"));

        assertThat(res).isNull();
        verify(sobreMimModelRepository).findById(1L);
        verify(sobreMimModelRepository, never()).save(any());
    }

    @Test
    void deveAtualizarSobreMimModelQuandoExistir() {
        SobreMimModel existente = new SobreMimModel(1L, "Antiga", 10, "Teste", "Teste", "Teste");
        SobreMimModel nova = new SobreMimModel(2L, "Nova", 11, "Teste2", "Teste3", "Teste4");
        when(sobreMimModelRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(sobreMimModelRepository.save(any(SobreMimModel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        SobreMimModel res = sobreMimModelService.update(1L, nova);

        ArgumentCaptor<SobreMimModel> captor = ArgumentCaptor.forClass(SobreMimModel.class);
        verify(sobreMimModelRepository).findById(1L);
        verify(sobreMimModelRepository).save(captor.capture());
        SobreMimModel salvo = captor.getValue();
        assertEquals(1L, salvo.getId());
        assertEquals("Nova", salvo.getNome());
        assertThat(11).isEqualTo(salvo.getIdade());
        assertEquals("Teste2", salvo.getCarreira());
        assertEquals("Teste3", salvo.getProfissao());
        assertThat("Teste4").isEqualTo(salvo.getEmpresa());
        verifyNoMoreInteractions(sobreMimModelRepository);
    }
}
