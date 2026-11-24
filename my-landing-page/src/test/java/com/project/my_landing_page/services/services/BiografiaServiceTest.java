package com.project.my_landing_page.services.services;

import com.project.my_landing_page.models.Biografia;
import com.project.my_landing_page.repositories.repositories.BiografiaRepository;
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
public class BiografiaServiceTest {

    @Mock
    private BiografiaRepository biografiaRepository;

    @InjectMocks
    private BiografiaService biografiaService;

    @Test
    void deveRetornarNuloQuandoBiografiaNaoExistir() {
        when(biografiaRepository.findById(1L)).thenReturn(Optional.empty());

        Biografia res = biografiaService.update(1L, new Biografia(0L, "Teste"));

        assertThat(res).isNull();
        verify(biografiaRepository).findById(1L);
        verify(biografiaRepository, never()).save(any());
    }

    @Test
    void deveAtualizarBiografiaQuandoExistir() {
        Biografia existente = new Biografia(1L, "Antiga");
        Biografia nova = new Biografia(2L, "Nova");
        when(biografiaRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(biografiaRepository.save(any(Biografia.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Biografia res = biografiaService.update(1L, nova);

        ArgumentCaptor<Biografia> captor = ArgumentCaptor.forClass(Biografia.class);
        verify(biografiaRepository).findById(1L);
        verify(biografiaRepository).save(captor.capture());
        Biografia salvo = captor.getValue();
        assertEquals(1L, salvo.getId());
        assertEquals("Nova", salvo.getBiografia());
        verifyNoMoreInteractions(biografiaRepository);
    }
}
